package BinPacking.Utils;

import BinPacking.Problem.BinPackingProblem;
import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Provides the methods to create and handle sets of one dimensional class
 * constrained bin packing problem instances.
 * <p>
 * @author Jose Carlos Ortiz Bayliss (jcobayliss@tec.mx)
 * @version 1.0
 */
public class BinPackingProblemSet {
    
    private final BinPackingProblem[] instances;
      
    /**
     * Creates a new instance of <code>BinPackingProblemSet</code>.
     * <p>
     * @param folder The folder where the instances are stored.     
     */
    public BinPackingProblemSet(String folder) {
        int i, n;
        List<String> fileNames;
        File file = new File(folder);
        if (!file.exists() || !file.isDirectory()) {
            System.err.println("The path \'" + folder + "\'is not a valid directory.");
            System.err.println("The system will halt.");
            System.exit(1);
        }
        fileNames = Arrays.asList(file.list());
        Collections.sort(fileNames);        
        i = 0;
        instances = new BinPackingProblem[fileNames.size()];
        for (String fileName : fileNames) {
            System.out.print("Loading \'" + folder + "/" + fileName + "\'...");            
            instances[i++] = new BinPackingProblem(folder + "/" + fileName);            
            System.out.println(" done.");
        }
    }        
    
    /**
     * Returns the bin packing problem instances contained in this set.
     * <p>
     * @return The bin packing problem instances contained Sin this set.
     */
    public BinPackingProblem[] getInstances() {
        return instances;
    }
    
}
