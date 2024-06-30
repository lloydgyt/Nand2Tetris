package VMTranslator;

import java.io.*;

import static VMTranslator.CommandType.*;

public class Main {
    /**
     * translate .vm file to .asm
     * by create a new .asm file in the same directory
     * @param args - fileName.vm
     */
    public static void main(String[] args) {
        String inputFile = args[0];
        String outputFile = STR."\{inputFile.substring(0, inputFile.lastIndexOf('.'))}.asm";
        BufferedReader reader;
        BufferedWriter writer;

        // read the file given by inputFile
        // use bufferReader to read file line by line
        // write/rewrite to a file - fileName.asm
        try {
            reader = new BufferedReader(new FileReader(inputFile));
            writer = new BufferedWriter(new FileWriter(outputFile, false));
        } catch (IOException e) {
            System.out.println("An error occurred.");
            return;
        }

        // instantiate Parser and CodeWriter
        Parser parser = new Parser(reader);
        CodeWriter codeWriter = new CodeWriter(writer);

        while (parser.hasMoreCommands()) {
            // advance to next command
            parser.advance();
            parser.parseCommand();

            // attach original VM command
            try {
                writer.write(STR."// \{parser.currentCommand()} (VM code)\r\n");
            } catch (IOException e) {
                System.out.println("IOError");
            }

            if (parser.commandType() == C_POP || parser.commandType() == C_PUSH) {
                codeWriter.writePushPop(parser.commandType(), parser.arg1(), parser.arg2());
            } else if (parser.commandType() == C_ARITHMETIC) {
                codeWriter.writeArithmetic(parser.arg1());
            }

        } // no more commands

        // close the buffer
        codeWriter.Close();
    }
}
