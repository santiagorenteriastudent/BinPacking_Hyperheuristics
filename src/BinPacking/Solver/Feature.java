package BinPacking.Solver;

/**
 * Defines the available features to characterize the one dimensional class
 * constrained bin packing problem.
 * <p>
 * @author José Carlos Ortiz Bayliss (jcobayliss@tec.mx)
 * @version 1.0
 */
public enum Feature {
    
    /**
     * Normalized average length of the items left to be packed.
     */
    AVGL,
    /**
     * Normalized standard deviation of the length of the items left to be packed.
     */    
    STDL,
    /**
     * Proportion of small items (items which size is smaller than 50% of the maximum capacity of the bins).
     */    
    SMALL,
    /**
     * Proportion of very small items (items which size is smaller than 25% of the maximum capacity of the bins).
     */    
    VSMALL,
    /**
     * Proportion of large items (items which size is larger than 50% of the maximum capacity of the bins).
     */    
    LARGE,
    /**
     * Proportion of very large items (items which size is larger than 75% of the maximum capacity of the bins).
     */    
    VLARGE,
    /**
     * An estimation of how likely it is to have a conflict due the item colors.
     */
    COLORC,
    /**
     * Proportion of open bins.
     */
    OBINS,
    /**
     * Average waste.
     */
    AVGW,
    /**
     * Color fullness
     */
    COLORF
}
