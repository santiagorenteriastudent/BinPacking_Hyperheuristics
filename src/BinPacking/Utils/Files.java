package BinPacking.Utils;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Provides the methods to save and load text files.
 * <p>
 * @author José Carlos Ortiz Bayliss (jcobayliss@tec.mx)
 * @version 1.0
 */
public abstract class Files {

    /**
     * Reads a text file and returns a string with its contents.
     * <p>
     * @param fileName The name of the text file to be read.
     * @return A string with the contents of the text file.
     */
    public static String load(String fileName) {
        File file = new File(fileName);
        char[] data;
        int size = (int) file.length(), chars_read = 0;
        FileReader in;
        try {
            in = new FileReader(file);
            data = new char[size];
            while (in.ready()) {
                chars_read += in.read(data, chars_read, size - chars_read);
            }
            in.close();
            return (new String(data, 0, chars_read));
        } catch (IOException e) {
            System.out.println("An error occurred while attempting to read the file \'" + fileName + "\'.");
            System.out.println("Exception: " + e.toString());
            System.out.println("The system will halt.");
            System.exit(1);
        }
        return null;
    }

    /**
     * Saves a string to a text file.
     * <p>
     * @param string The string to be saved.
     * @param fileName The file where the string will be saved.
     */
    public static void save(String string, String fileName) {
        File f;
        FileWriter fw;
        try {
            f = new File(fileName);
            fw = new FileWriter(f, false);
            fw.write(string);
            fw.close();
        } catch (IOException e) {
            System.out.println("An error occurred while attempting to save the file \"" + fileName + "\".");
            System.out.println("Exception: " + e.toString());
            System.out.println("The system will halt.");
            System.exit(1);
        }
    }

}
