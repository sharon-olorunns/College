import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Stack;
import java.lang.NullPointerException;
import java.util.Iterator;
import java.lang.UnsupportedOperationException;


/*
 * A Contest to Meet (ACM) is a reality TV contest that sets three contestants at three random
 * city intersections. In order to win, the three contestants need all to meet at any intersection
 * of the city as fast as possible.
 * It should be clear that the contestants may arrive at the intersections at different times, in
 * which case, the first to arrive can wait until the others arrive.
 * From an estimated walking speed for each one of the three contestants, ACM wants to determine the
 * minimum time that a live TV broadcast should last to cover their journey regardless of the contestantsâ€™
 * initial positions and the intersection they finally meet. You are hired to help ACM answer this question.
 * You may assume the following:
 *    ï‚· Each contestant walks at a given estimated speed.
 *    ï‚· The city is a collection of intersections in which some pairs are connected by one-way
 * streets that the contestants can use to traverse the city.
 *
 * This class implements the competition using Floyd-Warshall algorithm
 */


public class CompetitionFloydWarshall{

    public static AdjMatrixEdgeWeightedDigraph graph;
    public int slowest;
    public double maximumDistance;
    public String filename;
    public FloydWarshall shortestPaths;
    public boolean graphValidity;


    CompetitionFloydWarshall (String filename, int sA, int sB, int sC){
        try{

            this.filename = filename;
            File file = new File(filename);
            Scanner in = new Scanner(file);
            this.slowest = Math.min(Math.min(sA,sB),sC);
            this.filename = filename;
            this.graph = new AdjMatrixEdgeWeightedDigraph(in);
            this.shortestPaths = new FloydWarshall(this.graph);
            this.maximumDistance = 0.0;

        } catch (FileNotFoundException | NullPointerException | UnsupportedOperationException e){

            this.filename = null;
            this.graph = null;
            this.graphValidity = false;
        }

        if(this.shortestPaths != null && this.graph.isValid() && !this.shortestPaths.hasNegativeCycle){

            this.graphValidity = true;

            for (int v = 0; v < this.graph.V(); v++) {
                for (int w = 0; w < this.graph.V(); w++) {
                    if (shortestPaths.hasPath(v, w)){
                        if(this.maximumDistance < shortestPaths.dist(v, w))
                            this.maximumDistance = shortestPaths.dist(v, w);
                    }
                }
            }
        }
        else {
            System.out.println("Invalid graph!");
        }
    }


    public int timeRequiredforCompetition(){

        if(this.maximumDistance <= 0.0 || this.slowest <= 0 || this.filename == null)
            return -1;

        double time = (1000*this.maximumDistance)/this.slowest;
        return (int) Math.ceil(time);
    }
}