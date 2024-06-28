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

    //todo: write API description and then implement

    /**
     * writes to the output file the assembly that implements
     * the given arithmetic command
     * @param command - Todo: what is the meaning of command
     *                      is it command type?
     */
    public void writeArithmetic(String command) {

    }

    /**
     * writes to the output file the assembly that implements
     * the given C_PUSH or C_POP command
     * @param command - todo: ???
     * @param segment - either local, argument or etc.
     * @param index - the offset from base address of segment
     */
    public void writePushPop(CommandType command, String segment, int index) {

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
