package VMTranslator;

import java.io.*;

public class Main {
    /**
     * translate .vm file to .asm
     * by create a new .asm file in the same directory
     * @param args - fileName.vm
     */
    public static void main(String[] args) {
        String inputFile = args[0];
        String outputFile = inputFile.substring(0, inputFile.lastIndexOf('.')) + ".asm";
        BufferedReader reader = null;
        BufferedWriter writer = null;

        // read the file given by inputFile
        // use bufferReader to read file line by line
        try {
            reader = new BufferedReader(new FileReader(inputFile));
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }

        // Create a new file - fileName.asm
        try {
            writer = new BufferedWriter(new FileWriter(outputFile, false));
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }

        // instantiate Parser and CodeWriter
        Parser parser = new Parser(reader);
        CodeWriter codeWriter = new CodeWriter(writer);

        while (parser.hasMoreCommands()) {

            parser.advance();
            //todo: parse the command and write asm

        } // no more commands

        // close the buffer
        codeWriter.Close();
    }
}
