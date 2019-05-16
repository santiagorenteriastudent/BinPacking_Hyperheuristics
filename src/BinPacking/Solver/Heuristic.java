package BinPacking.Solver;

/**
 * Defines the available heuristics to solve the one dimensional class
 * constrained bin packing problem.
 * <p>
 * @author José Carlos Ortiz Bayliss (jcobayliss@tec.mx)
 * @version 1.0
 */
public enum Heuristic {
    /**
     * Chooses the first bin with enough capacity for the current item to pack.
     */
    FIRST_FIT,
    /**
     * Chooses the first bin with enough capacity and that already contains an
     * item with the same color of the current item to pack.
     */
    FIRST_FIT_SC,
    /**
     * Chooses the first bin with enough capacity and that does not contain an
     * item with the same color of the current item to pack.
     */
    FIRST_FIT_DC,
    /**
     * Chooses the bin with the smallest capacity for the current item to pack
     * (tries to minimize the waste of the bin after the item is packed).
     */
    BEST_FIT,
    /**
     * Chooses the bin with the smallest capacity and that already contains an
     * item with the same color of the current item to pack (tries to minimize
     * the waste of the bin after the item is packed).
     */
    BEST_FIT_SC,
    /**
     * Chooses the bin with the smallest capacity and that does not contain an
     * item with the same color of the current item to pack (tries to minimize
     * the waste of the bin after the item is packed).
     */
    BEST_FIT_DC,
    /**
     * Chooses the bin with the largest capacity for the current item to pack
     * (tries to maximize the waste of the bin after the item is packed).
     */
    WORST_FIT,
    /**
     * Chooses the bin with the largest capacity and that already contains an
     * item with the same color of the current item to pack (tries to maximize
     * the waste of the bin after the item is packed).
     */
    WORST_FIT_SC,
    /**
     * Chooses the bin with the largest capacity and that does not contain an
     * item with the same color of the current item to pack (tries to maximize
     * the waste of the bin after the item is packed).
     */
    WORST_FIT_DC,
    /**
     * Chooses the second bin with the largest capacity for the current item to
     * pack (tries to 'almost' maximize the waste of the bin after the item is
     * packed).
     */
    ALMOST_WORST_FIT,
    /**
     * Chooses the second bin with the largest capacity and that already
     * contains an item with the same color of the current item to pack (tries
     * to 'almost' maximize the waste of the bin after the item is packed).
     */
    ALMOST_WORST_FIT_SC,
    /**
     * Chooses the second bin with the largest capacity and that does not
     * contain an item with the same color of the current item to pack (tries
     * to 'almost' maximize the waste of the bin after the item is packed).
     */
    ALMOST_WORST_FIT_DC
}
