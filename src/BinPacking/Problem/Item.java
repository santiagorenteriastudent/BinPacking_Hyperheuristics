package BinPacking.Problem;

/**
 * Provides the methods to create and handle items for the one dimensional class
 * constrained bin packing problem.
 * <p>
 * @author José Carlos Ortiz Bayliss (jcobayliss@tec.mx)
 * @version 1.0
 */
public class Item {

    private final int size, color;    
       
    /**
     * Creates a new instance of <code>Item</code>.
     * <p>     
     * @param size The size of this item.
     * @param color The color of this item.
     */
    public Item(int size, int color) {            
        this.size = size;
        this.color = color;
    }     
    
    /**
     * Returns the size of this item.
     * <p>
     * @return The size of this item.
     */
    public int getSize() {
        return size;
    }   
    
    /**
     * Returns the color of this item.
     * <p>
     * @return The color of this item.
     */
    public int getColor() {
        return color;
    }
    
    /**
     * Returns the string representation of this item.
     * <p>
     * @return The string representation of this item.
     */
    public String toString() {
        StringBuilder string;
        string = new StringBuilder();
        string.append("(").append(size).append(", ").append(color).append(")");
        return string.toString();
    }    
    
}
