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
            //todo: substitute the name in accordance to given inputFile
            File file = new File("example.txt");
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }

            writer = new BufferedWriter(new FileWriter(file));

        } catch (IOException e) {
            System.out.println("An error occurred.");
        }

        // instantiate Parser and CodeWriter
        Parser parser = new Parser(reader);
        CodeWriter codeWriter = new CodeWriter(writer);

        while (parser.hasMoreCommands()) {

            //todo: parse the command and write asm

            //todo: Demo - how to read line
            // String line;
            // while ((line = reader.readLine()) != null) {
            //     System.out.println(line);
            // }

            //todo: Demo - write to the file using BufferedWriter
            // writer.write("Hello, world!");
            // writer.newLine(); // Adds a new line
            // writer.write("This is a new file.");
            // writer.close();
            // System.out.println("Successfully wrote to the file.");
            parser.advance();
        } // no more commands

        // close the buffer
        codeWriter.Close();
    }
}
