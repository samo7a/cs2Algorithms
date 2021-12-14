import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.TreeSet;

public class main {
	// main method
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		// create the scanner object
		Scanner scnr = new Scanner(System.in);

		// read the first three values
		int n = scnr.nextInt();
		int c = scnr.nextInt();
		int r = scnr.nextInt();

		int [][] grid = new int[r][c];
		for (int i = 0; i < r; i++) 
			for (int j = 0; j < c; j++)
				grid[i][j] = 0;

		// creating a nodes array to read in every x and y coordinate
		// and save it into a node
		Node[] nodes = new Node[n + 1];
		for (int i = 0; i < n; i++) {
			int x = scnr.nextInt() - 1;
			int y = scnr.nextInt() - 1;
			nodes[i] = new Node(x, y, i);
			// set the grid to 1 to indicate that there is a pole here
			grid[x][y] = 1;
		}
		nodes[n] = new Node(n,n,n);
		scnr.close();


		// 3 arrays of LinkedLists of type Edge
		// they are to hold the different trees
		// this one is for the initial tree(graph)
		ArrayList<Edge> [] graph = new ArrayList[n + 1];
		for (int i = 0; i < n + 1; i++) {
			graph[i] = new ArrayList<Edge>(); 
		}
		// this is for the minimum spanning tree after applying  
		// prim's algorithm on the initial graph
		ArrayList<Edge> [] mst = new ArrayList[n + 1];
		for (int i = 0; i < n + 1; i++) {
			mst[i] = new ArrayList<Edge>(); 
		}
		//this for the final minimum spanning tree after adding the 
		// loose pole and applying the algorithm one more time to get the result
		ArrayList<Edge> [] finalmst = new ArrayList[n + 1];
		for (int i = 0; i < n + 1; i++) {
			finalmst[i] = new ArrayList<Edge>(); 
		}

		// creating the edges between nodes
		for (int i = 0; i < n; i++) {
			int start = nodes[i].nodeNumber; // start node
			for (int j = 0; j < n; j++) {
				int end = nodes[j].nodeNumber; // end node
				if (start == end) continue; // to ensure that there is node pointing to itself
				// distance formula
				double distance = Math.sqrt( Math.pow(nodes[start].x - nodes[end].x, 2) + Math.pow(nodes[start].y - nodes[end].y, 2) );
				// add everything to the graph
				graph[start].add(new Edge(start, end, distance));
			}
		}

		//printGraph(graph); // printing the graph for testing 

		//a data structure to hold the connected nodes so far
		TreeSet<Integer> connectedNodes = new TreeSet<>();
		connectedNodes.add(0);// add the first node (node 0)
		// the possible edges that might be added to the minimum spanning tree
		PriorityQueue <Edge> edgeHeap = new PriorityQueue<Edge>();
		for (Edge o : graph[0])  edgeHeap.add(o);  // add all edges in the first node

		// creating the mst from the graph
		createMST(graph, mst, connectedNodes, edgeHeap, n);
		//System.out.println("mst graph ---->");
		//printGraph(mst);  // for testing
		//System.out.println("");


		// now we need to loop through the grid and a node to the mst
		// and connect the added node(loose pole) to the rest of the nodes
		// and find the mst of this new graph and return the final answer

		// very useful for the looping process
		ArrayList<Edge>[] mstClone = new ArrayList[n + 1];
		ArrayList<Edge>[] finalmstClone = new ArrayList[n + 1];

		for (int i = 0; i < n + 1; i++) {
			mstClone[i] = (ArrayList<Edge>) mst[i].clone();
			finalmstClone[i] = (ArrayList<Edge>) finalmst[i].clone();
		}

		//		System.out.println("mstClone, should be the same as the above");
		//		printGraph(mstClone);

		TreeSet <Double> distances = new TreeSet<>();
		// loop through the loop
		for (int i = 0; i < r; i++) {
			for (int j = 0; j < c; j++) {
				if(grid[i][j] == 1) continue; // we cannot place the loose pole in the place of a fixed pole
				nodes[n].x = i; 
				nodes[n].y = j;

				//				for (int q = 0; q < n + 1; q++) {
				//					System.out.println(nodes[q]);
				//				}
				// connecting the loose pole to all the other poles
				for (int k = 0; k < n; k++) {
					int end = nodes[n].nodeNumber;
					int start = nodes[k].nodeNumber;
					double distance = Math.sqrt(Math.pow(nodes[start].x - nodes[end].x, 2) + Math.pow(nodes[start].y - nodes[end].y, 2));
					mstClone[start].add(new Edge(start, end, distance));
					mstClone[end].add(new Edge(end, start, distance));
				}

				//				System.out.println("mstClone after adding the node ____---->>,>");
				//				printGraph(mstClone);

				// reseting everything for the next algorithm to be run
				connectedNodes.clear();
				connectedNodes.add(0);
				edgeHeap.clear();
				for (Edge o : mstClone[0])  edgeHeap.add(o);
				distances.add(createMST(mstClone, finalmstClone, connectedNodes, edgeHeap, n + 1)); // n + 1 for the loose pole

				for (int z = 0; z < n + 1; z++) {
					mstClone[z] =  null;
					finalmstClone[z] = null;
					mstClone[z] = (ArrayList<Edge>) mst[z].clone();
					finalmstClone[z] = (ArrayList<Edge>) finalmst[z].clone();
				} 
				System.gc();
				//break;
			}
			//break;
		}
		//System.out.println(distances);
		System.out.println(distances.first());
	}



	// simple nested for loop to iterate through the grid and print every element
	// used it for testing purposes only
	public static void printGrid (int[][] grid, int c, int r) {
		System.out.println();
		for (int i = 0; i < c; i++) {
			for (int j = 0; j < r; j++) {
				System.out.printf("%d ", grid[i][j]);
			}
			System.out.println("");
		}
	}
	
	// print the graph/mst with some information //  helped with testing
	public static void printGraph (ArrayList<Edge>[] graph) {
		System.out.println();
		for (int i = 0; i < graph.length; i++) {
			for(Edge e: graph[i]) System.out.printf("Node number " + e + "%n");
		}
		System.out.println();
	}

	// prim's algorithm. the same as the one the professor did but with minor difference
	public static double createMST(ArrayList<Edge>[] graph, ArrayList<Edge>[] mst, TreeSet<Integer> connectedNodes, PriorityQueue <Edge> edgeHeap, int n) {
		double ans = 0.0;
		int st = 0;
		while(connectedNodes.size() != n) {
		
			Edge edge = edgeHeap.poll();
			if (connectedNodes.contains(edge.dest)) continue;

			ans = ans + edge.distance;
			connectedNodes.add(edge.dest);

			

			mst[st].add(new Edge(st, edge.dest, edge.distance));
			mst[edge.dest].add(new Edge(edge.dest, st, edge.distance));




			//edgeHeap.clear();
			for (Edge o: graph[edge.dest]) {
				if (!connectedNodes.contains(o.dest))
					edgeHeap.add(o);
			}
			st = edgeHeap.peek().st;
		}
		
		return ans;
	}


	// Edge class
	public static class Edge implements Comparable<Edge>{
		int st;
		int dest;  // destination
		double distance;  // distance (aka weight)
		// constructor
		Edge(int st, int dest, double distance){
			this.st = st;
			this.dest = dest;
			this.distance = distance;
		}

		// to get the edges from the least to the most (distance speaking)
		@Override
		public int compareTo(Edge e) {
			return Double.compare(distance, e.distance);
		}
		// to print the edge information
		@Override
		public String toString() {

			return st + " is connected to " + dest + " and is " + new DecimalFormat("00.0000").format(distance) + " units far from it";
		}

	}

	public static class Node {
		int x;
		int y;
		int nodeNumber;

		Node(int x, int y, int nodeNumber){
			this.x = x;
			this.y = y;
			this.nodeNumber = nodeNumber;
		}
		@Override
		public String toString() {
			return "Node # " + nodeNumber + " (x,y) = (" + x + "," + y + ").";
		}
	}
}

