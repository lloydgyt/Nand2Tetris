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
    //todo: remember to first attach original VM command
    public void writeArithmetic(String command) {

        //todo: Demo - write to the file using BufferedWriter
        // writer.write("Hello, world!");
        // writer.newLine(); // Adds a new line
        // writer.write("This is a new file.");
    }

    /**
     * writes to the output file the assembly that implements
     * the given C_PUSH or C_POP command
     * @param command - either C_POP or C_PUSH
     * @param segment - either local, argument or etc.
     * @param index - the offset from base address of segment
     */
    public void writePushPop(CommandType command, String segment, int index) {
        //todo: Parser must provide
        // 1. command
        // 2. segment (String)
        // 3. index
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
