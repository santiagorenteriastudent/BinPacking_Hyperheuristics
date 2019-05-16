package BinPacking.Solver;

/**
 * Defines the methods to create and handle a generic hyper-heuristic.
 * <p>
 * @author José Carlos Ortiz Bayliss (jcobayliss@tec.mx)
 * @version 1.0
 */
public abstract class HyperHeuristic {

    protected final Feature[] features;
    protected final Heuristic[] heuristics;

    /**
     * Creates a new instance of <code>HyperHeuristic</code>.
     * <p>
     * @param features The features to be used to characterize the problem
     * instances.
     * @param heuristics The heuristics available for the hyper-heuristic.
     */
    protected HyperHeuristic(Feature[] features, Heuristic[] heuristics) {
        this.features = new Feature[features.length];
        System.arraycopy(features, 0, this.features, 0, features.length);
        this.heuristics = new Heuristic[heuristics.length];
        System.arraycopy(heuristics, 0, this.heuristics, 0, heuristics.length);
    }

    /**
     * Returns the heuristic to be used given the current problem state.
     * <p>
     * @param solver The bin packing problem solver that contains the
     * information about the current problem state.
     * @return The heuristic to be used given the current problem state.
     */
    public abstract Heuristic getHeuristic(BinPackingSolver solver);
    public abstract double[][] train(String folder);

    /**
     * Returns the string representation of this hyper-heuristic.
     * <p>
     * @return The string representation of this hyper-heuristic.
     */
    public abstract String toString();

}
