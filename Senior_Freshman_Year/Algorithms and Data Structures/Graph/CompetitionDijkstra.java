import java.io.File;
import java.io.FileNotFoundException;
import java.lang.NullPointerException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Stack;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 * A Contest to Meet (ACM) is a reality TV contest that sets three contestants at three random
 * city intersections. In order to win, the three contestants need all to meet at any intersection
 * of the city as fast as possible.
 * It should be clear that the contestants may arrive at the intersections at different times, in
 * which case, the first to arrive can wait until the others arrive.
 * From an estimated walking speed for each one of the three contestants, ACM wants to determine the
 * minimum time that a live TV broadcast should last to cover their journey regardless of the contestants’
 * initial positions and the intersection they finally meet. You are hired to help ACM answer this question.
 * You may assume the following:
 *     Each contestant walks at a given estimated speed.
 *     The city is a collection of intersections in which some pairs are connected by one-way
 * streets that the contestants can use to traverse the city.
 *
 * This class implements the competition using Dijkstra's algorithm
 */

public class CompetitionDijkstra {

    public EdgeWeightedDiGraph graph;
    public int slowest;
    public double maximumDistance;
    public String filename;
    public boolean graphValidity;


    CompetitionDijkstra(String filename, int sA, int sB, int sC) {

        try{

            this.filename = filename;
            File file = new File(filename);
            Scanner in = new Scanner(file);

            this.graph = new EdgeWeightedDiGraph(in);
            this.slowest = Math.min(Math.min(sA,sB),sC);
            this.filename = filename;

            this.maximumDistance = 0.0;

        } catch (FileNotFoundException | NullPointerException e){
            this.graph = null;
            this.graphValidity = false;
            this.filename = null;
        }


        if(this.graph != null && this.graph.isValid()){

            this.graphValidity = true;

            for(int i = 0; i < graph.V(); i++) {

                DijkstraSP routedGraph = new DijkstraSP(graph, i);

                //Calculate longest distance for current routedGraph
                for(int j = 0; j < graph.V(); j++) {
                    //If a path exists within the graph to node j
                    if(routedGraph.hasPathTo(j)) {
                        if(this.maximumDistance < routedGraph.distTo(j)) {
                            this.maximumDistance = routedGraph.distTo(j);
                        }
                    }
                }
            }
        }
    }


    public int timeRequiredforCompetition() {

        if(this.maximumDistance <= 0.0 || this.slowest <= 0 || this.filename == null)
            return -1;
        double time = (1000*this.maximumDistance)/this.slowest;
        return (int) Math.ceil(time);
    }
}