

import BinPacking.Solver.HyperHeuristic;
import BinPacking.Solver.Heuristic;
import BinPacking.Solver.Feature;
import BinPacking.Solver.BinPackingSolver;
import java.util.Arrays;
import java.util.Random;

/**
 * Provides the methods to create and handle a simple hyper-heuristic for
 * solving the one dimensional class constrained bin packing problem.
 * <p>
 * @author José Carlos Ortiz Bayliss (jcobayliss@tec.mx)
 * @version 1.0
 */
public class SampleHyperHeuristic extends HyperHeuristic {
    
    private final double[][] conditions;

    /**
     * Creates a new instance of <code>SampleHyperHeuristic</code>.
     * <p>
     * @param features The features to be used to characterize the problem
     * instances.
     * @param heuristics The heuristics available for the hyper-heuristic.
     * @param seed The seed to initialize the random number generator in this
     * hyper-heuristic.
     */
    public SampleHyperHeuristic(Feature[] features, Heuristic[] heuristics, long seed) {
        super(features, heuristics);
        Random random;        
        conditions = new double[heuristics.length][];
        random = new Random(seed);
        for (int i = 0; i < heuristics.length; i++) {
            conditions[i] = new double[features.length];
            for (int j = 0; j < features.length; j++) {
                conditions[i][j] = random.nextDouble();
            }
        }
    }

    @Override
    public Heuristic getHeuristic(BinPackingSolver solver) {
        double distance, minDistance;
        double[] state;
        Heuristic heuristic;
        /*
         * Calculates the current problem state.
         */
        state = new double[features.length];
        for (int i = 0; i < state.length; i++) {
            state[i] = solver.getFeature(features[i]);
        }
        /*
         * Calculates the distance from the problem state to each of the condition in the hyper-heuristic.
         */
        minDistance = Double.MAX_VALUE;
        heuristic = null;
        for (int i = 0; i < conditions.length; i++) {
            distance = getDistance(state, conditions[i]);            
            if (distance < minDistance) {
                minDistance = distance;
                heuristic = heuristics[i];
            }
        }
        return heuristic;
    }

    @Override
    public String toString() {
        StringBuilder string;
        string = new StringBuilder();
        for (int i = 0; i < conditions.length; i++) {
            string.append(Arrays.toString(conditions[i])).append(" => ").append(heuristics[i]).append("\n");
        }
        return string.toString().trim();
    }
    
    /**
     * Returns the Euclidian distance between the condition of a rule in the hyper-heuristic and the current problem state.
     * <p>
     * @param state The current problem state.
     * @param condition The condition of a rule in the hyper-heuristic.
     * @return The Euclidian distance between the condition of a rule in the
     * hyper-heuristic and the current problem state.
     */
    private double getDistance(double[] state, double[] condition) {
        double sum;
        sum = 0;
        for (int i = 0; i < features.length; i++) {
            sum += Math.pow(state[i] - condition[i], 2);
        }
        return Math.sqrt(sum);
    }

    @Override
    public double[][] train(String folder) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void saveCondMatrix(String fileName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
