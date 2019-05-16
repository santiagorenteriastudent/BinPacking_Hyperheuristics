package BinPacking.Solver;

import BinPacking.Problem.BinPackingProblem;
import BinPacking.Problem.Item;
import BinPacking.Problem.Bin;
import BinPacking.Utils.Statistical;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Provides the methods to solve the one dimensional class constrained bin
 * packing problem.
 * <p>
 * @author José Carlos Ortiz Bayliss (jcobayliss@tec.mx)
 * @version 1.0
 */
public class BinPackingSolver {

    private final int maxCapacity, maxColors;
    private final List<Bin> openBins, closedBins;
    private final List<Item> items;

    /**
     * Creates a new instance of <code>BinPackingSolver</code>.
     * <p>
     * @param problem The bin packing problem instance to solve.
     */
    public BinPackingSolver(BinPackingProblem problem) {
        maxCapacity = problem.getMaxCapacity();
        maxColors = problem.getMaxColors();
        openBins = new LinkedList();
        closedBins = new LinkedList();
        items = new ArrayList(problem.getNbItems());
        items.addAll(Arrays.asList(problem.getItems()));
        openBins.add(new Bin(maxCapacity, maxColors));
    }

    /**
     * Solves a bin packing problem instance by using one specific heuristic.
     * <p>
     * @param heuristic The heuristic to solve the bin packing problem instance.
     * @return A solution to the bin packing problem instance.
     */
    public List<Bin> solve(Heuristic heuristic) {
        Bin bin;
        Item item;
        List<Bin> tmp;
        while (items.size() > 1) {
            item = items.remove(0);
            if (item.getSize() <= maxCapacity) {
                bin = selectBin(heuristic, item);
                /*
                 * The item is packed.
                 */
                bin.pack(item);
                /*
                 * Full bins cannot longer be used.
                 */
                if (bin.getCapacity() == 0) {
                    closedBins.add(bin);
                    openBins.remove(bin);
                }
            }
        }
        tmp = new ArrayList(closedBins);
        tmp.addAll(openBins);
        return tmp;
    }

    /**
     * Solves a bin packing problem instance by using a heuristic selector.
     * <p>
     * @param hyperHeuristic The hyper-heuristic to be used to solve the bin
     * packing problem instance.
     * @return A solution to the bin packing problem instance.
     */
    public List<Bin> solve(HyperHeuristic hyperHeuristic) {
        Bin bin;
        Item item;
        List<Bin> tmp;
        Heuristic heuristic;
        while (items.size() > 1) {
            item = items.remove(0);
            if (item.getSize() <= maxCapacity) {
                heuristic = hyperHeuristic.getHeuristic(this);
                bin = selectBin(heuristic, item);
                /*
                 * The item is packed.
                 */
                bin.pack(item);
                /*
                 * Full bins cannot longer be used.
                 */
                if (bin.getCapacity() == 0) {
                    closedBins.add(bin);
                    openBins.remove(bin);
                }
            }
        }
        tmp = new ArrayList(closedBins);
        tmp.addAll(openBins);
        return tmp;
    }

    /**
     * Returns the items left to be packed in the bin packing problem instance
     * being solved.
     * <p>
     * @return The items left to be packed in the bin packing problem instance
     * being solved.
     */
    public List<Item> getItems() {
        return items;
    }
    
    /**
     * Solves current state using heuristic
     * @param heuristic
     * @return
     */
    public List<Bin> solveState(Heuristic heuristic) {
        Bin bin;
        Item item;
        List<Bin> tmp;
        item = items.remove(0);
        if (item.getSize() <= maxCapacity) {
            bin = selectBin(heuristic, item);
            /*
            * The item is packed.
            */
            bin.pack(item);
            /*
            * Full bins cannot longer be used.
            */
            if (bin.getCapacity() == 0) {
                closedBins.add(bin);
                openBins.remove(bin);
            }
        }
        tmp = new ArrayList(closedBins);
        tmp.addAll(openBins);
        return tmp;
    }

    /**
     * Returns the items left to be packed in the bin packing problem instance
     * being solved.
     * <p>
     * @return The items left to be packed in the bin packing problem instance
     * being solved.
     */

    /**
     * Returns the current capacities of the bins given the current solution to
     * the bin packing problem instance being solved.
     * <p>
     * @return The current capacities of the bins given the current solution to
     * the bin packing problem instance being solved.
     */
    public int[] getCapacities() {
        int i;
        int[] capacities;
        capacities = new int[openBins.size()];
        i = 0;
        for (Bin bin : openBins) {
            capacities[i++] = bin.getCapacity();
        }
        return capacities;
    }

    /**
     * Returns a suitable bin to pack the item provided as argument.
     * <p>
     * @param heuristic The heuristic to be used to select the bin where the
     * item will be packed.
     * @param item The item to pack.
     * @return A suitable bin to pack the item provided as argument.
     */
    public Bin selectBin(Heuristic heuristic, Item item) {
        int waste, waste2, tmp;
        Bin selected, selectedTmp;
        selected = null;
        switch (heuristic) {
            case FIRST_FIT:
                for (Bin bin : openBins) {
                    if (bin.canPack(item)) {
                        selected = bin;
                        break;
                    }
                }
                break;
            case FIRST_FIT_SC:
                for (Bin bin : openBins) {
                    if (bin.canPack(item) && bin.containsColor(item.getColor())) {
                        selected = bin;
                        break;
                    }
                }
                break;
            case FIRST_FIT_DC:
                for (Bin bin : openBins) {
                    if (bin.canPack(item) && !bin.containsColor(item.getColor())) {
                        selected = bin;
                        break;
                    }
                }
                break;
            case BEST_FIT:
                waste = Integer.MAX_VALUE;
                for (Bin bin : openBins) {
                    if (bin.canPack(item)) {
                        tmp = bin.getCapacity() - item.getSize();
                        if (tmp < waste) {
                            selected = bin;
                            waste = tmp;
                        }
                    }
                }
                break;
            case BEST_FIT_SC:
                waste = Integer.MAX_VALUE;
                for (Bin bin : openBins) {
                    if (bin.canPack(item)) {
                        tmp = bin.getCapacity() - item.getSize();
                        if (tmp < waste && bin.containsColor(item.getColor())) {
                            selected = bin;
                            waste = tmp;
                        }
                    }
                }
                break;
            case BEST_FIT_DC:
                waste = Integer.MAX_VALUE;
                for (Bin bin : openBins) {
                    if (bin.canPack(item)) {
                        tmp = bin.getCapacity() - item.getSize();
                        if (tmp < waste && !bin.containsColor(item.getColor())) {
                            selected = bin;
                            waste = tmp;
                        }
                    }
                }
                break;
            case WORST_FIT:
                waste = -Integer.MAX_VALUE;
                for (Bin bin : openBins) {
                    if (bin.canPack(item)) {
                        tmp = bin.getCapacity() - item.getSize();
                        if (tmp > waste) {
                            selected = bin;
                            waste = tmp;
                        }
                    }
                }
                break;
            case WORST_FIT_SC:
                waste = -Integer.MAX_VALUE;
                for (Bin bin : openBins) {
                    if (bin.canPack(item)) {
                        tmp = bin.getCapacity() - item.getSize();
                        if (tmp > waste && bin.containsColor(item.getColor())) {
                            selected = bin;
                            waste = tmp;
                        }
                    }
                }
                break;
            case WORST_FIT_DC:
                waste = -Integer.MAX_VALUE;
                for (Bin bin : openBins) {
                    if (bin.canPack(item)) {
                        tmp = bin.getCapacity() - item.getSize();
                        if (tmp > waste && !bin.containsColor(item.getColor())) {
                            selected = bin;
                            waste = tmp;
                        }
                    }
                }
                break;
            case ALMOST_WORST_FIT:
                waste = -Integer.MAX_VALUE;
                waste2 = -Integer.MAX_VALUE;
                selected = null;
                selectedTmp = null;
                for (Bin bin : openBins) {
                    if (bin.canPack(item)) {
                        tmp = bin.getCapacity() - item.getSize();
                        if (tmp > waste) {
                            selected = selectedTmp;
                            selectedTmp = bin;
                            waste2 = waste;
                            waste = tmp;
                        } else if (tmp > waste2) {
                            selected = bin;
                            waste2 = tmp;
                        }
                    }
                }
                if (selected == null) {
                    selected = selectedTmp;
                }
            case ALMOST_WORST_FIT_SC:
                waste = -Integer.MAX_VALUE;
                waste2 = -Integer.MAX_VALUE;
                selected = null;
                selectedTmp = null;
                for (Bin bin : openBins) {
                    if (bin.canPack(item)) {
                        tmp = bin.getCapacity() - item.getSize();
                        if (tmp > waste && bin.containsColor(item.getColor())) {
                            selected = selectedTmp;
                            selectedTmp = bin;
                            waste2 = waste;
                            waste = tmp;
                        } else if (tmp > waste2 && bin.containsColor(item.getColor())) {
                            selected = bin;
                            waste2 = tmp;
                        }
                    }
                }
                if (selected == null) {
                    selected = selectedTmp;
                }  
            case ALMOST_WORST_FIT_DC:
                waste = -Integer.MAX_VALUE;
                waste2 = -Integer.MAX_VALUE;
                selected = null;
                selectedTmp = null;
                for (Bin bin : openBins) {
                    if (bin.canPack(item)) {
                        tmp = bin.getCapacity() - item.getSize();
                        if (tmp > waste && !bin.containsColor(item.getColor())) {
                            selected = selectedTmp;
                            selectedTmp = bin;
                            waste2 = waste;
                            waste = tmp;
                        } else if (tmp > waste2 && !bin.containsColor(item.getColor())) {
                            selected = bin;
                            waste2 = tmp;
                        }
                    }
                }
                if (selected == null) {
                    selected = selectedTmp;
                }
        }
        /*
         * If no bin is selected we have to open a new bin to pack the item.
         */
        if (selected == null) {
            selected = new Bin(maxCapacity, maxColors);
            openBins.add(selected);
        }
        return selected;
    }

    /**
     * Returns the number of bins used given the current solution to the bin
     * packing problem instance being solved.
     * <p>
     * @return The number of bins used given the current solution to the bin
     * packing problem instance being solved.
     */
    public int getNbBins() {
        return closedBins.size() + openBins.size();
    }

    /**
     * Returns the number of open bins used by this solver.
     * <p>
     * @return The number of open bins used by this solver.
     */
    public int getNbOpenBins() {
        return openBins.size();
    }

    /**
     * Returns the number of closed bins used by this solver.
     * <p>
     * @return The number of closed bins used by this solver.
     */
    public int getNbClosedBins() {
        return closedBins.size();
    }

    public double getLargeItemRate() {
        double rate;
        rate = 0;
        for (Item item : items) {
            if (item.getSize() > 0.50 * maxCapacity) {
                rate++;
            }
        }
        return rate / items.size();
    }

    /**
     * Returns the value of a specific feature of the bin packing problem being
     * solved.
     * <p>
     * @param feature The feature to evaluate.
     * @return The value of a specific feature of the bin packing problem being
     * solved.
     */
    public double getFeature(Feature feature) {
        int i;
        double tmp;
        double[] values;
        List<Integer> colors;
        switch (feature) {
            case AVGL:
                i = 0;
                values = new double[items.size()];
                for (Item item : items) {
                    values[i++] = item.getSize();
                }
                return Statistical.mean(values) / Statistical.max(values);
            case STDL:
                i = 0;
                values = new double[items.size()];
                for (Item item : items) {
                    values[i++] = item.getSize();
                }
                return Statistical.stdev(values) / Statistical.max(values);
            case SMALL:
                tmp = 0;
                for (Item item : items) {
                    if (item.getSize() < 0.50 * maxCapacity) {
                        tmp++;
                    }
                }
                return tmp / items.size();
            case VSMALL:
                tmp = 0;
                for (Item item : items) {
                    if (item.getSize() < 0.25 * maxCapacity) {
                        tmp++;
                    }
                }
                return tmp / items.size();
            case LARGE:
                tmp = 0;
                for (Item item : items) {
                    if (item.getSize() > 0.50 * maxCapacity) {
                        tmp++;
                    }
                }
                return tmp / items.size();
            case VLARGE:
                tmp = 0;
                for (Item item : items) {
                    if (item.getSize() > 0.75 * maxCapacity) {
                        tmp++;
                    }
                }
                return tmp / items.size();
            case COLORC:
                colors = new ArrayList(10);
                for (Item item : items) {
                    if (!colors.contains(item.getColor())) {
                        colors.add(item.getColor());
                    }
                }
                tmp = (double) maxColors / colors.size();
                if (tmp > 1) {
                    return 1;
                }
                return tmp;
            case OBINS:
                return ((double) openBins.size()) / (closedBins.size() + openBins.size());
            case AVGW:
                tmp = 0;
                for (Bin bin : closedBins) {
                    tmp += bin.getCapacity();
                }
                for (Bin bin : openBins) {
                    tmp += bin.getCapacity();
                }
                return tmp / (closedBins.size() + openBins.size());
            case COLORF:
                tmp = 0;
                for (Bin bin : closedBins) {
                    tmp += bin.getNbColors();
                }
                for (Bin bin : openBins) {
                    tmp += bin.getNbColors();
                }
                return tmp / (closedBins.size() + openBins.size());
            default:
                System.out.println("An error has ocurred.\nThe system will halt.");
                System.exit(1);
        }
        return Double.NaN;
    }

    /**
     * Returns the string representation of the current solution to the bin
     * packing problem instance being solved.
     * <p>
     * @return The string representation of the current solution to the bin
     * packing problem instance being solved.
     */
    public String toString() {
        List<Bin> tmp = new LinkedList();
        tmp.addAll(closedBins);
        tmp.addAll(openBins);
        return tmp.toString();
    }

}
