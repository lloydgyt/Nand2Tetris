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
        //todo: should there be a flag to indicate EOF?
        try {
            return reader.ready();
        } catch (IOException e) {
            //todo: how to handle such a IOException
            System.out.println("IOError");
            return false;
        }
    }

    /**
     * advance to next command
     */
    public void advance() {
        try {
            command = reader.readLine();
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
     * @return the first argument of current command
     * for C_PUSH and C_POP, it's the segment of command
     * in case of C_ARITHMETIC, the command itself is returned.
     * Should not be called if current command is C_RETURN
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
     * parse the command and set arg1, arg2 and cType
     */
    private void parseCommand() {
        String[] words = command.split(" ");

        if (words[0].equals("push")) {
             cType = C_PUSH;
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
}
