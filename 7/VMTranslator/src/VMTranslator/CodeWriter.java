package VMTranslator;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static VMTranslator.ArithmeticType.*;

public class CodeWriter {
    private final BufferedWriter writer;
    private final Map<String, ArithmeticType> arithmeticTypeMap = new HashMap<>();
    private final Map<String, String> compareLabelMap = new HashMap<>();

    // used for making labels for each command distinct
    private int labelNum = 0;

    /**
     * @param writer - buffer reader for filename.vm
     */
    public CodeWriter(BufferedWriter writer) {
        this.writer = writer;
        arithmeticTypeMap.put("add", ADD);
        arithmeticTypeMap.put("sub", SUB);
        arithmeticTypeMap.put("neg", NEG);
        arithmeticTypeMap.put("eq", EQ);
        arithmeticTypeMap.put("gt", GT);
        arithmeticTypeMap.put("lt", LT);
        arithmeticTypeMap.put("and", AND);
        arithmeticTypeMap.put("or", OR);
        arithmeticTypeMap.put("not", NOT);

        compareLabelMap.put("<", "LT");
        compareLabelMap.put(">", "GT");
        compareLabelMap.put("==", "EQ");
    }

    /**
     * writes to the output file the assembly that implements
     * the given arithmetic command
     * @param command - the name of Arithmetic command e.g. ADD
     */
    public void writeArithmetic(String command) {
        try {
            // use Map and Switch statement choose which commands to write
            switch (arithmeticTypeMap.get(command)) {
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
        writeNeg();
        writeAdd();
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

    /**
     * using if-statement to implement EQ
     */
    //todo: these command may need refactoring
    // then change @1 or @-1 there is no need to get immediate
    // numbers like 1 or -1 in this way
    private void writeEq() throws IOException {
        // compute x - y, and push it to stack
        writeSub();
        writeCmp("==");
    }

    private void writeGt() throws IOException {
        // compute x - y, and push it to stack
        writeSub();
        writeCmp(">");
    }

    private void writeLt() throws IOException {
        // compute x - y, and push it to stack
        writeSub();
        writeCmp("<");
    }

    // pass in compare type ">", "<" or "=="
    private void writeCmp(String compareType) throws IOException {
        String label = compareLabelMap.get(compareType);

        // SP--
        writeDecreaseSP();
        // D = *SP
        writeGetTop();

        // compares D to 0, if holds then jump
        writer.write(STR."@\{label}_\{labelNum}\r\n");
        writer.write(STR."D;J\{label}\r\n");

        // else, set *SP = 0
        writer.write("D=0\r\n");
        // *SP = D
        writeSetTop();

        // jump to end
        writer.write(STR."@END_\{label}_\{labelNum}\r\n");
        writer.write("0;JMP\r\n");

        // set *SP = -1
        writer.write(STR."(\{label}_\{labelNum})\r\n");
        writer.write("D=-1\r\n");
        // *SP = D
        writeSetTop();

        // end of if-statement
        writer.write(STR."(END\{label}_\{labelNum})\r\n");

        // SP++
        writeIncreaseSP();

        labelNum++;
    }

    private void writeAnd() throws IOException {
        // SP--
        writeDecreaseSP();
        // D = *SP
        writeGetTop();
        // SP--
        writeDecreaseSP();

        // D = D & M
        writer.write("// AND operands\r\n");
        writer.write("@SP\r\n");
        writer.write("A=M\r\n");
        writer.write("D=D&M\r\n");

        // *SP = D
        writeSetTop();
        // SP++
        writeIncreaseSP();
    }

    private void writeOr() throws IOException {
        // SP--
        writeDecreaseSP();
        // D = *SP
        writeGetTop();
        // SP--
        writeDecreaseSP();

        // D = D | M
        writer.write("// OR operands\r\n");
        writer.write("@SP\r\n");
        writer.write("A=M\r\n");
        writer.write("D=D|M\r\n");

        // *SP = D
        writeSetTop();
        // SP++
        writeIncreaseSP();
    }
    private void writeNot() throws IOException {
        // SP--
        writeDecreaseSP();

        // M = !M
        writer.write("// M = !M\r\n");
        writer.write("@SP\r\n");
        writer.write("A=M\r\n");
        writer.write("M=!M\r\n");

        // SP++
        writeIncreaseSP();
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
