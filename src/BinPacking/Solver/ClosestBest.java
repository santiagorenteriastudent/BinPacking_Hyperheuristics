package BinPacking.Solver;

public class ClosestBest
{
    public Heuristic closestHeur,bestHeur;
    public double closestCost, bestCost;
    
    public ClosestBest(Heuristic closestHeur, Heuristic bestHeur, double closestCost, double bestCost){
        this.bestCost = bestCost;
        this.bestHeur = bestHeur;
        this.closestCost = closestCost;
        this.closestHeur = closestHeur;
    }
}