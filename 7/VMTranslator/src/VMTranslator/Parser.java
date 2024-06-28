package VMTranslator;

import java.io.BufferedReader;

public class Parser {
    private final BufferedReader reader;

    /**
     * @param reader - buffer reader for filename.vm
     */
    public Parser(BufferedReader reader) {
        this.reader = reader;
    }

    /**
     * if there is more commands to parse
     */
    public boolean hasMoreCommands() {
        return false;
    }

    /**
     * advance to next command
     */
    public void advance() {

    }

    /**
     * @return the type of current command
     */
    public CommandType commandType() {
        return null;
    }

    /**
     * @return the first argument of current command,
     * in case of C_ARITHMETIC, the command itself is returned.
     * Should not be called if current command is C_RETURN
     */
    public String arg1() {
        return null;
    }

    /**
     * @return the second argument of current command.
     * Should be called only if current command is
     * C_PUSH, C_POP, C_FUNCTION or C_CALL.
     */
    public int arg2() {
        return 0;
    }
}
