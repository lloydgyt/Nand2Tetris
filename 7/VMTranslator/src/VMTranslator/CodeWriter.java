package VMTranslator;

import java.io.BufferedWriter;
import java.io.IOException;

public class CodeWriter {
    private final BufferedWriter writer;

    /**
     * @param writer - buffer reader for filename.vm
     */
    public CodeWriter(BufferedWriter writer) {
        this.writer = writer;
    }

    /**
     * writes to the output file the assembly that implements
     * the given arithmetic command
     * @param command - the name of Arithmetic command e.g. ADD
     */
    public void writeArithmetic(String command) {
        try {
            //todo: must write assembly
            // every arithmetic command except 'eq' and 'neg'
            // pops twice and push once
            if (command.equals("add")) {
                writeAdd();
            }
        } catch (IOException e) {
            System.out.println("IOError");
        }
    }

    private void writeAdd() throws IOException {
        writeDecreaseSP();

        writeGetTop();

        writeDecreaseSP();

        writer.write("// D = 7 + 8\r\n");
        writer.write("@SP\r\n");
        writer.write("A=M\r\n");
        writer.write("D=D+M\r\n");

        writeSetTop();

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
