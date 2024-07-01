package VMTranslator;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static VMTranslator.ArithmeticType.*;

public class CodeWriter {
    private final BufferedWriter writer;
    private final Map<String, ArithmeticType> map = new HashMap<>();

    // used for making labels for each command distinct
    // there are 6 boolean command, hence the length
    // all the number starts from 0
    private final int[] labelNum = new int[6];

    /**
     * @param writer - buffer reader for filename.vm
     */
    public CodeWriter(BufferedWriter writer) {
        this.writer = writer;
        map.put("add", ADD);
        map.put("sub", SUB);
        map.put("neg", NEG);
        map.put("eq", EQ);
        map.put("gt", GT);
        map.put("lt", LT);
        map.put("and", AND);
        map.put("or", OR);
        map.put("not", NOT);
    }

    /**
     * writes to the output file the assembly that implements
     * the given arithmetic command
     * @param command - the name of Arithmetic command e.g. ADD
     */
    public void writeArithmetic(String command) {
        try {
            // use Map and Switch statement choose which commands to write
            switch (map.get(command)) {
                case ADD:
                    writeAdd();
                    break;
                case SUB:
                    writeSub();
                    break;
                case NEG:
                    writeNeg();
                    break;
                case EQ:
                    writeEq();
                    break;
                case GT:
                    writeGt();
                    break;
                case LT:
                    writeLt();
                    break;
                case AND:
                    writeAnd();
                    break;
                case OR:
                    writeOr();
                    break;
                case NOT:
                    writeNot();
                    break;
                default:
                    //todo: should I handle this exception?
                    throw new IllegalArgumentException("unknown command type");
            }
        } catch (IOException e) {
            System.out.println("IOError");
        }
    }

    private void writeAdd() throws IOException {
        // SP--
        writeDecreaseSP();
        // D = *SP
        writeGetTop();
        // SP--
        writeDecreaseSP();

        // D = M + D
        //todo: how to show actual number instead of hard-coding it
        // e.g. writer.write("// D = 7 + 8\r\n");
        // this feature should apply to all arithmetic command
        // once implemented
        writer.write("// adds operands\r\n");
        writer.write("@SP\r\n");
        writer.write("A=M\r\n");
        writer.write("D=M+D\r\n");

        // *SP = D
        writeSetTop();
        // SP++
        writeIncreaseSP();
    }

    private void writeSub() throws IOException {
        // SP--
        writeDecreaseSP();
        // D = *SP
        writeGetTop();
        // SP--
        writeDecreaseSP();
        // D = M - D
        writer.write("// subtracts operands\r\n");
        writer.write("@SP\r\n");
        writer.write("A=M\r\n");
        writer.write("D=M-D\r\n");
        // *SP = D
        writeSetTop();
        // SP++
        writeIncreaseSP();
    }

    private void writeNeg() throws IOException {
        // SP--
        writeDecreaseSP();

        // M = -M
        writer.write("// M = -M\r\n");
        writer.write("@SP\r\n");
        writer.write("A=M\r\n");
        writer.write("M=-M\r\n");

        // SP++
        writeIncreaseSP();
    }

    //todo: suppose that 1 for true and 0 for false
    /**
     * using if-statement to implement EQ
     * use labelNum[0] as label number
     */
    private void writeEq() throws IOException {
        // SP--
        writeDecreaseSP();
        // D = *SP
        writeGetTop();

        // if D == 0, then jump
        writer.write(STR."@EQ\{labelNum[0]}\r\n");
        writer.write("D;JEQ\r\n");

        // else, set *SP = 0
        writer.write("@1\r\n");
        writer.write("D=A\r\n");
        // *SP = D
        writeSetTop();

        // jump to end
        writer.write(STR."@ENDEQ\{labelNum[0]}\r\n");
        writer.write("0;JMP\r\n");

        // set *SP = 1
        writer.write(STR."(EQ\{labelNum[0]})\r\n");
        writer.write("@1\r\n");
        writer.write("D=A\r\n");
        // *SP = D
        writeSetTop();

        // end of if-statement
        writer.write(STR."(ENDEQ\{labelNum[0]})\r\n");

        // SP++
        writeIncreaseSP();

        labelNum[0]++;
    }

    /**
     * use labelNum[1] as label number
     */
    //todo: can I use previous-built command to implement other command?
    private void writeGt() throws IOException {
    }

    private void writeLt() throws IOException {

    }
    private void writeAnd() throws IOException {

    }
    private void writeOr() throws IOException {

    }
    private void writeNot() throws IOException {

    }

    private void writeDecreaseSP() throws IOException {
        writer.write("// SP--\r\n");
        writer.write("@SP\r\n");
        writer.write("M=M-1\r\n");
    }

    private void writeIncreaseSP() throws IOException {
        writer.write("// SP++\r\n");
        writer.write("@SP\r\n");
        writer.write("M=M+1\r\n");
    }

    /**
     * Assembly that set D to *SP
     */
    private void writeGetTop() throws IOException {
        //todo: how to show actual number at top of the stack instead of "*SP"
        writer.write("// D = *SP\r\n");
        writer.write("@SP\r\n");
        writer.write("A=M\r\n");
        writer.write("D=M\r\n");
    }

    /**
     * Assembly that set *SP to D
     */
    private void writeSetTop() throws IOException {
        writer.write("// *SP = D\r\n");
        writer.write("@SP\r\n");
        writer.write("A=M\r\n");
        writer.write("M=D\r\n");
    }

    /**
     * writes to the output file the assembly that implements
     * the given C_PUSH or C_POP command
     * @param command - either C_POP or C_PUSH
     * @param segment - either local, argument or etc.
     * @param index - the offset from base address of segment
     */
    public void writePushPop(CommandType command, String segment, int index) {
        //todo: because there is no pop constant, here is just a
        // temporary implementation to test everything is working so far
        try {
            // push constant i
            writer.write(STR."// D = \{index}\r\n");
            writer.write(STR."@\{index}\r\n");
            writer.write("D=A\r\n");

            writeSetTop();

            writeIncreaseSP();
        } catch (IOException e) {
            System.out.println("IOError");
        }
    }

    /**
     * Close the write buffer
     * todo: why should CodeWriter handles the job to close
     *      the output file?
     */
    public void Close() {
        try {
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }
}
