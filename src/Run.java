import BinPacking.Problem.BinPackingProblem;
import BinPacking.Utils.BinPackingProblemSet;
import BinPacking.Solver.BinPackingSolver;
import BinPacking.Solver.Feature;
import BinPacking.Solver.Heuristic;
import BinPacking.Solver.HyperHeuristic;
import BinPacking.Utils.Files;
import java.text.DecimalFormat;

/**
 * Runs the bin packing framework.
 * <p>
 * @author José Carlos Ortiz Bayliss (jcobayliss@tec.mx)
 * @version 1.0
 */
public class Run {

    public static void main(String[] args) {
        HyperHeuristic hyperHeuristic;
        /*
         * Calculates the initial states of a set of problem instances by using some specific features.
         */
        characterizeSet("Instances/Training", "features.csv",
                new Feature[]{
                    Feature.AVGL,
                    Feature.STDL,
                    Feature.SMALL,
                    Feature.VSMALL,
                    Feature.LARGE,
                    Feature.VLARGE,
                    Feature.COLORC,
                    Feature.OBINS,
                    Feature.AVGW,
                    Feature.COLORF
                }
        );
        /*
         * Solves a set of problem instances by using some specific heuristics.
         */
        solveSet("Instances/Training", "heuristics.csv",
                new Heuristic[]{
                    Heuristic.FIRST_FIT,
                    Heuristic.FIRST_FIT_SC,
                    Heuristic.FIRST_FIT_DC,
                    Heuristic.BEST_FIT,
                    Heuristic.BEST_FIT_SC,
                    Heuristic.BEST_FIT_DC,
                    Heuristic.WORST_FIT,
                    Heuristic.WORST_FIT_SC,
                    Heuristic.WORST_FIT_DC,
                    Heuristic.ALMOST_WORST_FIT,
                    Heuristic.ALMOST_WORST_FIT_SC,
                    Heuristic.ALMOST_WORST_FIT_DC
                }
        );
        /*
         * Creates a sample hyper-heuristic.
         */
        hyperHeuristic = new SimulatedAnnealing(
                new Feature[]{
                    Feature.AVGL,
                    Feature.STDL,
                    Feature.SMALL,
                    Feature.VSMALL,
                    Feature.LARGE,
                    Feature.VLARGE,
                    Feature.COLORC,
                    Feature.OBINS,
                    Feature.AVGW,
                    Feature.COLORF
                },
                new Heuristic[]{
                    Heuristic.FIRST_FIT,
                    Heuristic.FIRST_FIT_SC,
                    Heuristic.FIRST_FIT_DC,
                    Heuristic.BEST_FIT,
                    Heuristic.BEST_FIT_SC,
                    Heuristic.BEST_FIT_DC,
                    Heuristic.WORST_FIT,
                    Heuristic.WORST_FIT_SC,
                    Heuristic.WORST_FIT_DC,
                    Heuristic.ALMOST_WORST_FIT,
                    Heuristic.ALMOST_WORST_FIT_SC,
                    Heuristic.ALMOST_WORST_FIT_DC
                },
                1, // Change this value to generate a different hyper-heuristic.
                10 //Epochs: Times Simulated Annealing sees each instance during training
        );
        solveSet("Instances/Training", "randomHyperHeuristic-Training.csv", hyperHeuristic);
        solveSet("Instances/Testing", "randomHyperHeuristic-Testing.csv", hyperHeuristic);
        //Solve with Simulated Annealing
        System.out.println(hyperHeuristic);
        hyperHeuristic.train("Instances/Training");
        /*
         * Solves a set of problem instances (the ones used for training) by using the previously defined hyper-heuristic.
         */
        solveSet("Instances/Training", "hyperHeuristic-Training.csv", hyperHeuristic);
        /*
         * Solves a set of problem instances (the ones used for testing) by using the previously defined hyper-heuristic.
         */
        solveSet("Instances/Testing", "hyperHeuristic-Testing.csv", hyperHeuristic);
        
        System.out.println(hyperHeuristic);
    }

    /**
     * Characterizes a set of bin packing problem instances by using a set of
     * features and saves the results in a file whose file is provided as argument.
     * <p>
     * @param folder The folder where the instances are stored.
     * @param fileName The name of the file where the results will be saved.
     * @param features The features to be used to characterize the problem
     * instances.
     */
    private static void characterizeSet(String folder, String fileName, Feature[] features) {
        StringBuilder string;
        DecimalFormat format;
        BinPackingProblemSet set;
        BinPackingSolver solver;
        string = new StringBuilder();
        set = new BinPackingProblemSet(folder);
        format = new DecimalFormat("0.0000");
        /*
         * Prints the header of the file.
         */
        string.append("File, ");
        for (Feature feature : features) {
            string.append(feature).append(", ");
        }
        string.delete(string.length() - 2, string.length());
        string.append("\n");
        /*
         * Prints the features for each instance in the set.
         */
        for (BinPackingProblem problem : set.getInstances()) {
            string.append(problem.getFileName()).append(", ");
            for (Feature feature : features) {
                solver = new BinPackingSolver(problem);
                string.append(format.format(solver.getFeature(feature))).append(", ");
            }
            string.delete(string.length() - 2, string.length());
            string.append("\n");
        }
        Files.save(string.toString().trim(), fileName);
    }

    /**
     * Solves a set of bin packing problem instances by using a set of
     * heuristics and saves the results in a file whose file is provided as argument.
     * <p>
     * @param folder The folder where the instances are stored.
     * @param fileName The name of the file where the results will be saved.
     * @param heuristics The heuristics to be used to solve the problem
     * instances.
     */
    private static void solveSet(String folder, String fileName, Heuristic[] heuristics) {
        StringBuilder string;
        DecimalFormat format;
        BinPackingProblemSet set;
        BinPackingSolver solver;
        string = new StringBuilder();
        set = new BinPackingProblemSet(folder);
        format = new DecimalFormat("0.0000");
        /*
         * Prints the header of the file.
         */
        string.append("File, ");
        for (Heuristic heuristic : heuristics) {
            string.append(heuristic).append(", ");
        }
        string.delete(string.length() - 2, string.length());
        string.append("\n");
        /*
         * Prints the features and results for each instance in the set.
         */
        for (BinPackingProblem problem : set.getInstances()) {
            string.append(problem.getFileName()).append(", ");
            for (Heuristic heuristic : heuristics) {
                solver = new BinPackingSolver(problem);
                solver.solve(heuristic);
                string.append(format.format(solver.getFeature(Feature.AVGW))).append(", ");
            }
            string.delete(string.length() - 2, string.length());
            string.append("\n");
        }
        Files.save(string.toString().trim(), fileName);
    }

    /**
     * Solves a set of bin packing problem instances by using a set of
     * heuristics and saves the results in a file whose file is provided as argument.
     * <p>
     * @param folder The folder where the instances are stored.
     * @param fileName The name of the file where the results will be saved.
     * @param hyperHeuristic The hyper-heuristic to be used to solve the problem
     * instances.
     */
    private static void solveSet(String folder, String fileName, HyperHeuristic hyperHeuristic) {
        StringBuilder string;
        DecimalFormat format;
        BinPackingProblemSet set;
        BinPackingSolver solver;
        string = new StringBuilder();
        set = new BinPackingProblemSet(folder);
        format = new DecimalFormat("0.0000");
        /*
         * Prints the header of the file.
         */
        string.append("File, Hyper-heuristic\n");
        /*
         * Prints the features and results for each instance in the set.
         */
        for (BinPackingProblem problem : set.getInstances()) {
            string.append(problem.getFileName()).append(", ");
            solver = new BinPackingSolver(problem);
            solver.solve(hyperHeuristic);
            string.append(format.format(solver.getFeature(Feature.AVGW))).append("\n");
        }
        Files.save(string.toString().trim(), fileName);
    }

}
