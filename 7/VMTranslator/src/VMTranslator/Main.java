package VMTranslator;

import java.io.*;

public class Main {
    /**
     * translate .vm file to .asm
     * by create a new .asm file in the same directory
     * @param args - fileName.vm
     */
    public static void main(String[] args) {
        // Java IO
        // read the file given by inputFile
        // use bufferReader to read file line by line
        String inputFile = args[0];
        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }

        // Parse .vm
        // here is Parser()
        try {
            // Create a new file
            //todo: substitute the name in accordance to given inputFile
            File file = new File("example.txt");
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }

            // Write to the file using BufferedWriter
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write("Hello, world!");
            writer.newLine(); // Adds a new line
            writer.write("This is a new file.");
            writer.close();
            System.out.println("Successfully wrote to the file.");

        } catch (IOException e) {
            System.out.println("An error occurred.");
        }

        // generate .asm
        // here is CodeWriter()
    }
}
