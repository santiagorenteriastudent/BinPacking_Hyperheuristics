package BinPacking.Problem;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Provides the methods to create and handle bins for the one dimensional class
 * constrained bin packing problem.
 * <p>
 * @author José Carlos Ortiz Bayliss (jcobayliss@tec.mx)
 * @version 1.0
 */
public class Bin {

    private int capacity;
    private final int maxColors;
    private final List<Item> items;
    private final List<Integer> colors;

    /**
     * Creates a new instance of <code>Bin</code>.
     * <p>
     * @param maxCapacity The maximum capacity of this bin.
     * @param maxColors The maximum number of different colors allowed to be packed in this bin.
     */
    public Bin(int maxCapacity, int maxColors) {
        this.capacity = maxCapacity;
        this.maxColors = maxColors;        
        items = new LinkedList();
        colors = new ArrayList(maxColors);
    }

    /**
     * Creates a new instance of <code>Bin</code> from an existing instance (copy constructor).
     * <p>
     * @param bin The instance of <code>Bin</code> to copy to this instance.
     */
    public Bin(Bin bin) {
        capacity = bin.capacity;
        maxColors = bin.maxColors;        
        items = new LinkedList(bin.items);
        colors = new ArrayList(bin.colors);
    }

    /**
     * Returns the current capacity of this bin.
     * <p>
     * @return The current capacity of this bin.
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Returns the different colors of the items packed in this bin.
     * <p>
     * @return The different colors of the items packed in this bin.
     */
    public int[] getColors() {
        int[] tmp;
        tmp = new int[colors.size()];
        for (int i = 0; i < colors.size(); i++) {
            tmp[i] = colors.get(i);
        }
        return tmp;
    }    
    
    /**
     * Returns the current number of different colors of the items packed in this bin.
     * <p>
     * @return The current number of different colors of the items packed in this bin.
     */
    public int getNbColors() {
        return colors.size();
    }

    public boolean containsColor(int color) {
        return colors.contains(color);
    }
    
    /**
     * Revises if the item provided can be packed in this bin.
     * <p>
     * @param item The item to be packed.
     * @return <code>true</code> if the item can be packed in this bin,
     * <code>false</code> otherwise.
     */
    public boolean canPack(Item item) {
        int nbColors;
        nbColors = colors.size();
        if (!colors.contains(item.getColor())) {
            nbColors++;
        }
        return item.getSize() <= getCapacity() && nbColors <= maxColors;
    }

    /**
     * Packs an item into this bin.
     * <p>
     * @param item The item to pack.
     * @return <code>true</code> if the item was successfully packed,
     * <code>false</code> otherwise.
     */
    public boolean pack(Item item) {
        if (canPack(item)) {
            items.add(item);
            capacity -= item.getSize();
            if (!colors.contains(item.getColor())) {
                colors.add(item.getColor());
            }
            return true;
        }
        return false;
    }

    /**
     * Returns the string representation of this bin.
     * <p>
     * @return The string representation of this bin.
     */
    public String toString() {
        StringBuilder string;
        string = new StringBuilder();
        string.append("[");
        if (!items.isEmpty()) {
            for (Item item : items) {
                string.append(item).append(", ");
            }
            string.delete(string.length() - 2, string.length());
        }
        string.append("]");
        return string.toString().trim();
    }
}
