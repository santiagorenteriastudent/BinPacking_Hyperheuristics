package BinPacking.Utils;

/**
 * Provides a small set of statistical functions.
 * <p>
 * @author José Carlos Ortiz Bayliss (jcobayliss@tec.mx)
 * @version 1.0
 */
public abstract class Statistical {

    /**
     * Returns the mean of the values provided as argument.
     * <p>
     * @param values The values to analyze.
     * @return The mean of the values provided as argument.
     */
    public static double mean(double[] values) {
        double mean = 0;
        if (values.length == 0) {
            return 0;
        }
        for (int i = 0; i < values.length; i++) {
            mean += values[i];
        }
        return mean / values.length;
    }
       
    /**
     * Returns the standard deviation of the values provided as argument.
     * <p>
     * @param values The values to analyze.
     * @return The standard deviation of the values provided as argument.
     */
    public static double stdev(double[] values) {
        double mean, stdev;
        mean = mean(values);
        stdev = 0;
        for (int i = 0; i < values.length; i++) {
            stdev += Math.pow((values[i] - mean), 2);
        }
        if (values.length > 1) {
            return Math.sqrt(stdev / (values.length - 1));
        } else {
            return 0;
        }
    }

    /**
     * Returns the median of a values provided as argument.
     * <p>
     * @param values The values to analyze.
     * @return The median of the values provided as argument.
     */
    public static double median(double[] values) {
        double median;        
        double orderedValues[];
        /*
         * Safety check.
         */
        if (values.length == 0) {
            return Double.NaN;
        }
        orderedValues = sort(values);
        if (orderedValues.length % 2 == 1) {
            median = orderedValues[(int) (orderedValues.length / 2)];
        } else {
            median = (orderedValues[(int) (orderedValues.length / 2) - 1] + orderedValues[(int) (orderedValues.length / 2)]) / 2;
        }
        return median;
    }

    /**
     * Sorts the values provided as argument.
     * <p>
     * @param values The values to sort.
     * @return The values sorted in ascending order.
     */
    public static double[] sort(double[] values) {
        int i, n;
        double tempValue;
        boolean swapped;
        double orderedValues[];
        orderedValues = new double[values.length];
        for (i = 0; i < values.length; i++) {
            orderedValues[i] = values[i];
        }
        n = values.length;
        do {
            swapped = false;
            for (i = 0; i < n - 1; i++) {
                if (orderedValues[i] > orderedValues[i + 1]) {
                    tempValue = orderedValues[i + 1];
                    orderedValues[i + 1] = orderedValues[i];
                    orderedValues[i] = tempValue;
                    swapped = true;
                }
            }
            n = n - 1;
        } while (swapped);
        return orderedValues;
    }

    /**
     * Returns the maximum value in the values provided as argument.
     * <p>
     * @param values The values to analyze.
     * @return The maximum value in the values provided as argument.
     */
    public static double max(double[] values) {
        double maxValue;
        maxValue = Double.MIN_VALUE;
        /*
         * Safety check.
         */
        if (values.length == 0) {
            return Double.NaN;
        }
        for (int i = 0; i < values.length; i++) {
            if (values[i] > maxValue) {
                maxValue = values[i];
            }
        }
        return maxValue;
    }

    /**
     * Returns the minimum value in the values provided as argument.
     * <p>
     * @param values The values to analyze.
     * @return The minimum value in the values provided as argument.
     */
    public static double min(double[] values) {
        double minValue;
        minValue = Double.MAX_VALUE;
        /*
         * Safety check.
         */
        if (values.length == 0) {
            return Double.NaN;
        }
        for (int i = 0; i < values.length; i++) {
            if (values[i] < minValue) {
                minValue = values[i];
            }
        }
        return minValue;
    }

    /**
     * Returns the range of the values provided as argument.
     * <p>
     * @param values The values to analyze.
     * @return The range of the values provided as argument.
     */
    public static double range(double[] values) {
        return max(values) - min(values);
    }
        
}
