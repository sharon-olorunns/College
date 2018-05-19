import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.util.Scanner;
import java.util.Stack;
import java.util.Iterator;

import org.junit.Test;

import java.util.NoSuchElementException;
import java.io.FileNotFoundException;
import java.lang.UnsupportedOperationException;
import java.util.NoSuchElementException;

/*
    1. Justify the choice of the data structures used in CompetitionDijkstra and CompetitionFloydWarshall

        a) CompetitionDijkstra:

            Ans: In Dijkstra's shortest path algorithm only one node is allocated to be the source node. All
                 the shortest paths to the other nodes in the graph can be found using the source node. I wanted
                 use a data structure that would efficiently and easily allows us to access the adjacency
                 matrix of vertices. That is why I decided to use the different classes and bag.java from the
                 Algorithms, 4th Edition by Robert Sedgewick and Kevin Wayne.


        b) CompetitionFloydWarshall:

           Ans: Floyd-Warhsall's algorithm is used when any of the nodes can be the source so you need to find
                the shortest path from all nodes to every other node. However unlike the Dijkstra algorithm
                Floyd-Warshall allows for negative-weighted edges. The two main data structures used were an
                Adjacency Matrix containing an optimal edge between node x and node y and a 2-D array containing the best
                routes between all nodes.




    2. a) Explain theoretical differences in the performance of Dijkstra and Floyd-Warshall algorithms
        in the given problem.


            Ans:  Dijkstra's algorithm has to be performed V times for V being the amount of nodes
                  within the graph. Without the use of a min-priority queue Dijkstra's runs in O(V^2) or when using
                  a min-priority queue O(E(edges in graph) + V log V). Using Floyd-Warshall, you compare all possible
                  paths through the graph between each pair of vertices. This is ideal for the current problem where we
                  need to know the longest path from any two vertices in order to compute the minimum time needed for
                  the competition to run. Unfortunately, the Floyd-Warshall algorithm runs in O(V^3).
 */

import static org.junit.Assert.*;
import java.io.FileNotFoundException;
import org.junit.Test;

public class CompetitionTests {


    @Test
    public void testDijkstraConstructor()
    {
        System.out.println("\nDijkstra Valid");
        CompetitionDijkstra cD = new CompetitionDijkstra("tinyEWD.txt", 50, 80, 60);
        assertEquals("Time required", 38, cD.timeRequiredforCompetition());
        System.out.println("tinyEWD: Minimum time required: " + cD.timeRequiredforCompetition() + " (mins)");

        cD = new CompetitionDijkstra("tinyEWD.txt", 60, 80, 60);
        assertEquals("Time required", 31, cD.timeRequiredforCompetition());
        System.out.println("tinyEWD: Minimum time required: " + cD.timeRequiredforCompetition() + " (mins)");

        cD = new CompetitionDijkstra("tinyEWD.txt", 100, 100, 100);
        assertEquals("Time required", 19, cD.timeRequiredforCompetition());
        System.out.println("tinyEWD: Minimum time required: " + cD.timeRequiredforCompetition() + " (mins)");

    }

    @Test
    public void testDijkstraLoop()
    {
        System.out.println("\nDijkstra Loops");
        CompetitionDijkstra cD = new CompetitionDijkstra("tinyLoopEWD.txt", 50, 80, 60);
        //assertEquals("Time required", 38, cD.timeRequiredforCompetition());
        System.out.println("tinyEWD: Minimum time required: " + cD.timeRequiredforCompetition() + " (mins)");
    }

    @Test
    public void testInvalidDijkstraConstructor()
    {
        System.out.println("\nDijkstra Invalid");

        //vertices not in graph
        CompetitionDijkstra cD = new CompetitionDijkstra("invalidTinyEWD.txt", 50, 80, 60);
        assertEquals("Time required", -1, cD.timeRequiredforCompetition());
        System.out.println("invalidTinyEWD: Minimum time required: " + cD.timeRequiredforCompetition() + " (mins)");

        //negative distances
        cD = new CompetitionDijkstra("invalidTinyEWD2.txt", 50, 80, 60);
        assertEquals("Time required", -1, cD.timeRequiredforCompetition());
        System.out.println("invalidTinyEWD2: Minimum time required: " + cD.timeRequiredforCompetition() + " (mins)");

        //negative edges
        cD = new CompetitionDijkstra("invalidTinyEWD3.txt", 50, 80, 60);
        assertEquals("Time required", -1, cD.timeRequiredforCompetition());
        System.out.println("invalidTinyEWD3: Minimum time required: " + cD.timeRequiredforCompetition() + " (mins)");

        //negative edges
        cD = new CompetitionDijkstra("invalidTinyEWD4.txt", 50, 80, 60);
        assertEquals("Time required", -1, cD.timeRequiredforCompetition());
        System.out.println("invalidTinyEWD4: Minimum time required: " + cD.timeRequiredforCompetition() + " (mins)");


    }


    @Test
    public void testSpeedsDijkstra()
    {
        System.out.println("\nDijkstra Speeds");
        CompetitionDijkstra cD = new CompetitionDijkstra("tinyEWD.txt", -1, -1, -1);
        assertEquals("Time required", -1, cD.timeRequiredforCompetition());
        System.out.println("tinyEWD: Minimum time required: " + cD.timeRequiredforCompetition() + " (mins)");

        cD = new CompetitionDijkstra("tinyEWD.txt", 20, 10, 5);
        //assertEquals("Time required", -1, cD.timeRequiredforCompetition());
        System.out.println("tinyEWD: Minimum time required: " + cD.timeRequiredforCompetition() + " (mins)");

        cD = new CompetitionDijkstra("tinyEWD.txt", 120, 200, 999);
        //assertEquals("Time required", -1, cD.timeRequiredforCompetition());
        System.out.println("tinyEWD: Minimum time required: " + cD.timeRequiredforCompetition() + " (mins)");

        cD = new CompetitionDijkstra("input-I.txt", 4, 7, 1);
        assertEquals("Time required", 12000, cD.timeRequiredforCompetition());
        System.out.println("input-I: Minimum time required: " + cD.timeRequiredforCompetition() + " (mins)");

        cD = new CompetitionDijkstra("input-I.txt", 3233,7,2368726);
        assertEquals("Time required", 1715, cD.timeRequiredforCompetition());
        System.out.println("input-I: Minimum time required: " + cD.timeRequiredforCompetition() + " (mins)");

        cD = new CompetitionDijkstra("input-K.txt", 51,7,2266262);
        assertEquals("Time required", 2286, cD.timeRequiredforCompetition());
        System.out.println("input-K: Minimum time required: " + cD.timeRequiredforCompetition() + " (mins)");
    }

    @Test
    public void testFNFDijkstra()
    {
        System.out.println("\nDijkstra missing file");
        CompetitionDijkstra cD = new CompetitionDijkstra("", 50, 80, 60);
        assertEquals("Time required", -1, cD.timeRequiredforCompetition());
        System.out.println("null: Minimum time required: " + cD.timeRequiredforCompetition() + " (mins)");

        cD = new CompetitionDijkstra("whereAmI.txt", 50, 80, 60);
        assertEquals("Time required", -1, cD.timeRequiredforCompetition());
        System.out.println("whereAmI.txt: Minimum time required: " + cD.timeRequiredforCompetition() + " (mins)");

        cD = new CompetitionDijkstra(null, 50, 80, 60);
        assertEquals("Time required", -1, cD.timeRequiredforCompetition());
        System.out.println("whereAmI.txt: Minimum time required: " + cD.timeRequiredforCompetition() + " (mins)");
    }


    @Test
    public void testFWConstructor()
    {
        System.out.println("\nFW Valid");
        CompetitionFloydWarshall cFW = new CompetitionFloydWarshall("tinyEWD.txt", 50, 80, 60);
        assertEquals("Time required", 38, cFW.timeRequiredforCompetition());
        System.out.println("tinyEWD: Minimum time required: " + cFW.timeRequiredforCompetition() + " (mins)");

        cFW = new CompetitionFloydWarshall("tinyEWD.txt", 60, 80, 60);
        assertEquals("Time required", 31, cFW.timeRequiredforCompetition());
        System.out.println("tinyEWD: Minimum time required: " + cFW.timeRequiredforCompetition() + " (mins)");

        cFW = new CompetitionFloydWarshall("tinyEWD.txt", 100, 100, 100);
        assertEquals("Time required", 19, cFW.timeRequiredforCompetition());
        System.out.println("tinyEWD: Minimum time required: " + cFW.timeRequiredforCompetition() + " (mins)");

    }


    @Test
    public void testFWLoop()
    {
        System.out.println("\nFW Loop");
        CompetitionFloydWarshall cFW = new CompetitionFloydWarshall("tinyLoopEWD.txt", 50, 80, 60);
        //assertEquals("Time required", 38, cFW.timeRequiredforCompetition());
        System.out.println("tinyLoopEWD: Minimum time required: " + cFW.timeRequiredforCompetition() + " (mins)");
    }

    @Test
    public void testInvalidFWConstructor()
    {
        System.out.println("\nFW Invalid");
        //vertices not in graph
        CompetitionFloydWarshall cFW = new CompetitionFloydWarshall("invalidTinyEWD.txt", 50, 80, 60);
        assertEquals("Time required", -1, cFW.timeRequiredforCompetition());
        System.out.println("invalidTinyEWD: Minimum time required: " + cFW.timeRequiredforCompetition() + " (mins)");

        //negative distances
        cFW = new CompetitionFloydWarshall("invalidTinyEWD2.txt", 50, 80, 60);
        assertEquals("Time required", -1, cFW.timeRequiredforCompetition());
        System.out.println("invalidTinyEWD2: Minimum time required: " + cFW.timeRequiredforCompetition() + " (mins)");

        //negative edges
        cFW = new CompetitionFloydWarshall("invalidTinyEWD3.txt", 50, 80, 60);
        assertEquals("Time required", -1, cFW.timeRequiredforCompetition());
        System.out.println("invalidTinyEWD3: Minimum time required: " + cFW.timeRequiredforCompetition() + " (mins)");

        //negative vertices
        cFW = new CompetitionFloydWarshall("invalidTinyEWD4.txt", 50, 80, 60);
        assertEquals("Time required", -1, cFW.timeRequiredforCompetition());
        System.out.println("invalidTinyEWD4: Minimum time required: " + cFW.timeRequiredforCompetition() + " (mins)");

    }

    @Test
    public void testSpeedsFW()
    {
        System.out.println("\nFW Speeds");
        CompetitionFloydWarshall cFW = new CompetitionFloydWarshall("tinyEWD.txt", -1, -1, -1);
        assertEquals("Time required", -1, cFW.timeRequiredforCompetition());
        System.out.println("tinyEWD: Minimum time required: " + cFW.timeRequiredforCompetition() + " (mins)");

        cFW = new CompetitionFloydWarshall("tinyEWD.txt", 20, 10, 5);
        //assertEquals("Time required", -1, cFW.timeRequiredforCompetition());
        System.out.println("tinyEWD: Minimum time required: " + cFW.timeRequiredforCompetition() + " (mins)");

        cFW = new CompetitionFloydWarshall("tinyEWD.txt", 120, 200, 999);
        //assertEquals("Time required", -1, cFW.timeRequiredforCompetition());
        System.out.println("tinyEWD: Minimum time required: " + cFW.timeRequiredforCompetition() + " (mins)");

        cFW = new CompetitionFloydWarshall("input-I.txt", 4,7,1);
        assertEquals("Time required", 12000, cFW.timeRequiredforCompetition());
        System.out.println("tinyEWD: Minimum time required: " + cFW.timeRequiredforCompetition() + " (mins)");

        cFW = new CompetitionFloydWarshall("input-I.txt", 3233,7,2368726);
        assertEquals("Time required", 1715, cFW.timeRequiredforCompetition());
        System.out.println("tinyEWD: Minimum time required: " + cFW.timeRequiredforCompetition() + " (mins)");

        cFW = new CompetitionFloydWarshall("input-K.txt", 51,7,266262);
        assertEquals("Time required", 2286, cFW.timeRequiredforCompetition());
        System.out.println("tinyEWD: Minimum time required: " + cFW.timeRequiredforCompetition() + " (mins)");
    }


    @Test
    public void testFNFFW()
    {
        System.out.println("\nFW missing file");
        CompetitionFloydWarshall cFW = new CompetitionFloydWarshall("", 50, 80, 60);
        assertEquals("Time required", -1, cFW.timeRequiredforCompetition());
        System.out.println("null: Minimum time required: " + cFW.timeRequiredforCompetition() + " (mins)");

    }


}
