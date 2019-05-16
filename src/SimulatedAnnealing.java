

import BinPacking.Problem.BinPackingProblem;
import BinPacking.Problem.Item;
import BinPacking.Solver.HyperHeuristic;
import BinPacking.Solver.Heuristic;
import BinPacking.Solver.Feature;
import BinPacking.Solver.BinPackingSolver;
import BinPacking.Solver.ClosestBest;
import BinPacking.Utils.BinPackingProblemSet;
import java.util.Arrays;
import java.lang.Math;
import java.util.List;
import java.util.Random;

/**
 * Provides the methods to create and handle a simple hyper-heuristic for
 * solving the one dimensional class constrained bin packing problem.
 * <p>
 * @author José Carlos Ortiz Bayliss (jcobayliss@tec.mx)
 * @version 1.0
 */
public class SimulatedAnnealing extends HyperHeuristic {
    
    private final double[][] conditions;
    private long seed;
    private int epochs;
    /**
     * Creates a new instance of <code>SampleHyperHeuristic</code>.
     * <p>
     * @param features The features to be used to characterize the problem
     * instances.
     * @param heuristics The heuristics available for the hyper-heuristic.
     * @param seed The seed to initialize the random number generator in this
     * hyper-heuristic.
     * @param epochs Times the algorithm sees each instance
     */
    public SimulatedAnnealing(Feature[] features, Heuristic[] heuristics, long seed, int epochs) {
        //Initialization of heuristic conditions in feature space
        super(features, heuristics);
        Random random;    
        this.seed = seed;
        this.epochs = epochs;
        
        //Initialize conditions
        conditions = new double[heuristics.length][];
        random = new Random(seed);
        for (int i = 0; i < heuristics.length; i++) {
            conditions[i] = new double[features.length];
            for (int j = 0; j < features.length; j++) {
                conditions[i][j] = random.nextDouble();
            }
        }
    }
    
    /**
     *
     * @param problem The problem to compute closest and heuristics
     * @return array with closest heuristic in index 0 and best in index 1
     */
    public ClosestBest getClosestBestHeuristic(BinPackingProblem problem){
        double minDistance,bestCost,closestCost;
        double distance,cost;
        Heuristic[] heuristic;
        ClosestBest result; 
        double[] state,solvedState;
        BinPackingSolver solver;
        
        solvedState = new double [features.length];
        state = new double[features.length];
        
        minDistance = Double.MAX_VALUE;
        bestCost = Double.MAX_VALUE;
        closestCost = 0;
        heuristic = new Heuristic[2];
        
        solver = new BinPackingSolver(problem);
        state = getState(solver);
        
        //Solve current state with every Heuristic and store solvedState
        for (Heuristic heur : heuristics) {
            solver = new BinPackingSolver(problem);
            solver.solveState(heur);
            solvedState = getState(solver);
            cost = solvedState[7] + solvedState[8];
            distance = getDistance(state,conditions[heur.ordinal()]);
            if (distance < minDistance) {
                minDistance = distance;
                closestCost = cost;
                heuristic[0] = heur; //Closest heuristic to instance state
            }
            if (cost < bestCost){
                bestCost = cost;
                heuristic[1] = heur; //Min cost (best) heuristic
            }
        }
        return new ClosestBest(heuristic[0],heuristic[1],closestCost,bestCost);
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
    
    private double[] getState(BinPackingSolver solver) {
        double[] state;
        state = new double[features.length];
        for(int j = 0; j< features.length; j++){
             state[j] = solver.getFeature(features[j]);
        }
        return state;
    } 
    
    /**
     * Trains hyperheuristic on problems at folder path
     * @param folder
     * @return
     */
    @Override
    public double[][] train(String folder){
        BinPackingProblemSet set;
        ClosestBest closestBestHeuristic;
        BinPackingSolver solver;
        set = new BinPackingProblemSet(folder);
        int numItems, totalSteps;
        Random rand = new Random(seed);
        double[] state;
        double prob,delta,temp, initialTemp, thres;
        List<Item> items;
        initialTemp = temp = 10;
        totalSteps = getTotalSteps(folder);
        
        for(int k =0; k<epochs; k++){
            System.out.println(temp);
            for (BinPackingProblem problem : set.getInstances()){
                solver = new BinPackingSolver(problem);
                items = solver.getItems();
                while (items.size() > 1) {
                    items = solver.getItems();
                    //To see if problem states are changing (bins being packed)
                    //System.out.println(problem.getFileName() + items.size());
                    //Get closest and best heuristics with their cost
                    closestBestHeuristic = getClosestBestHeuristic(problem);
                    state = getState(solver);
                    //Compute acceptance probability of bringing closer closest Heur instead of Best
                    delta = closestBestHeuristic.closestCost - closestBestHeuristic.bestCost;
                    prob = Math.exp(delta/temp);
                    System.out.println(closestBestHeuristic.closestCost);
                    System.out.println(closestBestHeuristic.bestCost);
                    thres = rand.nextDouble();
                    if(thres < prob){
                        //Move closest heuristic closer to current state, otherwise move best
                        moveHeuristic(closestBestHeuristic.closestHeur, state);
                        //moveHeuristic(closestBestHeuristic.bestHeur,5); //Move best randomly
                    } else {
                        moveHeuristic(closestBestHeuristic.bestHeur, state);
                        //moveHeuristic(closestBestHeuristic.closestHeur,5); //Move closest randomly
                    }
                    //Advance state using closest heuristic and remove item
                    solver.solveState(closestBestHeuristic.closestHeur);
                    temp = temp - initialTemp/totalSteps;
                }
            }
        }
        return null;
    }

    private void moveHeuristic(Heuristic heuristic, double[] state) {
        double[] diffVec = new double[features.length];
        Random rand = new Random(seed);
        for(int i=0; i<features.length; i++){
            diffVec[i] = state[i] - conditions[heuristic.ordinal()][i];
            conditions[heuristic.ordinal()][i] =+ rand.nextDouble()*diffVec[i];
        }
    }
    
    private void moveHeuristic(Heuristic heuristic, double step) {
        double[] diffVec = new double[features.length];
        Random rand = new Random(seed);
        for(int i=0; i<features.length; i++){
            conditions[heuristic.ordinal()][i] =+ -1 + 2*step*rand.nextDouble();
        }
    }
    
    private int getTotalSteps(String folder){
        int totalSteps = 0;
        BinPackingProblemSet set;
        set = new BinPackingProblemSet(folder);
        for (BinPackingProblem problem : set.getInstances()){
            totalSteps+= problem.getNbItems();
        }
        return totalSteps*epochs;
    }

}
