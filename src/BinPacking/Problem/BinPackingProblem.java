package BinPacking.Problem;

import BinPacking.Utils.Files;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Provides the methods to create and solve the one dimensional class
 * constrained bin packing problem.
 * <p>
 * @author José Carlos Ortiz Bayliss (jcobayliss@tec.mx)
 * @version 1.0
 */
public class BinPackingProblem {

    private final int maxCapacity, maxColors;
    private final String fileName;
    private final Item[] items;

    /**
     * Creates a new instance of <code>BinPackingProblem</code>.
     * <p>
     * @param fileName The name of the file that contains this instance.
     */
    public BinPackingProblem(String fileName) {
        int nbItems, size, classId;
        String text;
        StringTokenizer lines, tmp;
        text = Files.load(fileName);
        lines = new StringTokenizer(text, "\n");
        if (lines.countTokens() <= 2) {
            System.out.println("The instance \'" + fileName + "\'cannot be loaded.");
            System.out.println("The system will halt.");
            System.exit(1);
            maxCapacity = -1;
            maxColors = -1;
            items = null;
        } else {
            /*
             * Reads the number of items.
             */
            nbItems = (int) Double.parseDouble(lines.nextToken().trim());
            items = new Item[nbItems];
            /*
             * Reads the bin size.
             */
            maxCapacity = (int) Double.parseDouble(lines.nextToken().trim());
            /*
             * Reads the bin cardinality.
             */
            maxColors = (int) Double.parseDouble(lines.nextToken().trim());

            /*
             * Reads the items within the instance.
             */
            for (int i = 0; i < nbItems; i++) {
                tmp = new StringTokenizer(lines.nextToken());
                size = Integer.parseInt(tmp.nextToken().trim());
                classId = Integer.parseInt(tmp.nextToken().trim());
                items[i] = new Item(size, classId);
            }
        }
        this.fileName = fileName.substring(fileName.lastIndexOf('/') + 1);
    }

    /**
     * Returns the maximum capacity of the bins in this bin packing problem.
     * <p>
     * @return The maximum capacity of the bins in this bin packing problem.
     */
    public int getMaxCapacity() {
        return maxCapacity;
    }

    /**
     * Returns the maximum number of different colors of the items packed in this bin packing problem.
     * <p>
     * @return The maximum number of different colors of the items packed in this bin packing problem.
     */
    public int getMaxColors() {
        return maxColors;
    }

    /**
     * Returns the number of items in this bin packing problem.
     * <p>
     * @return The number of items in this bin packing problem.
     */
    public int getNbItems() {
        return items.length;
    }

    /**
     * Returns the name of the file that contains this instance.
     * <p>
     * @return The name of the file that contains this instance.
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Returns the items in this bin packing problem.
     * <p>
     * @return The items in this bin packing problem.
     */
    public Item[] getItems() {
        Item[] tmp;
        tmp = new Item[items.length];
        System.arraycopy(items, 0, tmp, 0, tmp.length);
        return tmp;
    }

    /**
     * Returns the string representation of this bin packing problem.
     * <p>
     * @return The string representation of this bin packing problem.
     */
    public String toString() {
        return Arrays.toString(items);
    }

}
