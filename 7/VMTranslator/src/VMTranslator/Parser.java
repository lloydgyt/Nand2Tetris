package VMTranslator;

import java.io.BufferedReader;
import java.io.IOException;

import static VMTranslator.CommandType.*;

public class Parser {
    private final BufferedReader reader;

    private CommandType cType;
    private int arg2;
    private String arg1;

    // store the current command
    private String command;

    /**
     * @param reader - buffer reader for filename.vm
     */
    public Parser(BufferedReader reader) {
        cType = null;
        this.reader = reader;
    }

    /**
     * if there is more commands to parse
     */
    public boolean hasMoreCommands() {
        try {
            return reader.ready();
        } catch (IOException e) {
            //todo: how to handle such a IOException
            System.out.println("IOError");
            return false;
        }
    }

    /**
     * Advance to next command, skipping blank line and comment.
     */
    public void advance() {
        try {
            do {
                command = reader.readLine();
            } while (command.startsWith("//") || command.isBlank());
        } catch (IOException e) {
            System.out.println("IOError");
        }
    }

    /**
     * @return the type of current command
     */
    public CommandType commandType() {
        return cType;
    }

    /**
     * @return the first argument of current command.
     * For C_PUSH and C_POP, it's the segment of command.
     * In case of C_ARITHMETIC, the command itself is returned.
     * Should not be called if current command is C_RETURN.
     */
    public String arg1() {
        return arg1;
    }

    /**
     * @return the second argument of current command.
     * Should be called only if current command is
     * C_PUSH, C_POP, C_FUNCTION or C_CALL.
     * e.g. returning the index in C_PUSH or C_POP command
     */
    public int arg2() {
        return arg2;
    }

    /**
     * Parse the command and set arg1, arg2 and cType.
     */
    public void parseCommand() {
        String[] words = command.split(" ");

        if (words[0].equals("push")) {
             cType = C_PUSH;
             //todo: setting arg1 and arg2 may be extracted
             // to make the code clear
             arg1 = words[1];
             arg2 = Integer.parseInt(words[2]);
        } else if (words[0].equals("pop")) {
            cType = C_POP;
            arg1 = words[1];
            arg2 = Integer.parseInt(words[2]);
        } else {
            cType = C_ARITHMETIC;
            arg1 = words[0];
        }

        /*else if (words[0].equals("")) {
          //todo: design a method that test if command belongs to arithmetic command
        }*/
    }

    public String currentCommand() {
        return command;
    }
}
