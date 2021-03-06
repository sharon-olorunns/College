//Based on code from https://algs4.cs.princeton.edu/44sp/EdgeWeightedDigraph.java.html

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Stack;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class EdgeWeightedDiGraph {

		private final int V;
		private int E;
		private boolean isValid;
		public int[] indegree;
		public Bag<DirectedEdge>[] edgesAdjacentTo;

		@SuppressWarnings("unchecked")
		public EdgeWeightedDiGraph(Scanner in) {

			this.V = in.nextInt();
			this.E = in.nextInt();
			this.isValid = true;

			int currentEdges = E;

			edgesAdjacentTo = (Bag<DirectedEdge>[]) new Bag[V];

			if(this.V < 3)
				this.isValid = false;

			if(this.isValid){
				this.indegree = new int[V];
				//Create empty adjacency matrix to house adjacent edges
				for (int v = 0; v < this.V; v++)
					edgesAdjacentTo[v] = new Bag<DirectedEdge>();

				//Extract all edges from input file
				for (int i = 0; i < currentEdges; i++) {

					int v = in.nextInt();
					int w = in.nextInt();
					double weight = in.nextDouble();

					//Verify that there is no negative vertices/weights
					if( v >= 0 && w >= 0 && weight >= 0.0)
						addEdge(new DirectedEdge(v, w, weight));

					else
						this.isValid = false;
				}
				validateGraph();
			}
		}

		public void validateGraph(){

			if(!this.isValid)
				return;

			boolean graphValid = true;

			for(int v=0; v<this.V; v++){
				if(indegree[v] < 1){
					graphValid = false;
					break;
				}
			}
			this.isValid = graphValid;

		}

		public boolean isValid() {
			if(this.isValid)
				return true;
			else
				return false;
		}

		public int V() {
			return V;
		}

		public int E() {
			return E;
		}

		public void addEdge(DirectedEdge e) {

			int v = e.from();
			int w = e.to();
			indegree[w]++;
			//Add directed edge e to v's adjacent edges
			edgesAdjacentTo[v].add(e);

			E++;
		}

		public Iterable<DirectedEdge> edgesAdjacentTo(int v) {
			return edgesAdjacentTo[v];
		}

		public Iterable<DirectedEdge> edges() {

			//Create empty Bag to house edges
			Bag<DirectedEdge> list = new Bag<DirectedEdge>();

			//Iterate over all vertices
			for (int v = 0; v < V; v++) {

				for (DirectedEdge e : edgesAdjacentTo(v)) {
					list.add(e);
				}
			}
			return list;
		}
	}
