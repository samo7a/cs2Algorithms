import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeSet;

// The main class
public class Main {
	// The main method
	@SuppressWarnings("unchecked")
	public static void main(String[] Args) {
		// Scanner object
		Scanner scnr = new Scanner(System.in);
		// read in the number of nodes and edges
		int v = scnr.nextInt();
		int e = scnr.nextInt();
		// create a graph and tree graphs
		Graph graph = new Graph(v);  //graph will hold the original graph 
		Graph tree = new Graph(v); // tree will hold the graph after removing the cycles
		// Read in the edges
		for (int i = 0; i < e; i++) {
			int a = scnr.nextInt() - 1;
			int b = scnr.nextInt() - 1;
			// add the edges to the graph and the tree
			graph.add(a, b);
			tree.add(a, b);
		}
		//System.out.println("original graph");
		//graph.printGraph();
		//System.out.println();
		//System.out.println();
		scnr.close(); // close the scanner object
		// a tree set to hold the answers created from the bfs
		// I chose the treeset because it doesnt allow duplicate values and
		// I can get the lowest value fast
		TreeSet<Integer> answers = new TreeSet<>();
		int ans = Integer.MAX_VALUE;
		answers.add(ans); // add a max value to the tree set
		
		for(int i = 0; i < v; i++) {  // for every node in the graph do the following
			// remove the cycles until none can found.
			boolean isCycleDetected = true;
			while(isCycleDetected) isCycleDetected = tree.detectAndRemoveCycles(i);
			
			//System.out.println("tree:");
			//tree.printGraph();
			//System.out.println();
			//System.out.println();
			int diameter = tree.findTheDiameter(); //find the diameter
			//System.out.printf("diameter = %d%n",diameter);
			if(ans > diameter) ans = diameter; // this will us to the diameter only if it is less than the 
			// the answer
			//System.out.printf("answer = %d%n",ans);
			answers.add(ans); 
			
			// restore the graph again for the next node
			for(int j = 0; j < v; j++) {
				tree.adj[j] = (TreeSet<Integer>) graph.adj[j].clone();
			}
			//System.out.println("from tree to the original graph again");
			//tree.printGraph();
			//System.out.println();
			//System.out.println();
		}
		//System.out.println(answers);
		System.out.println(answers.first());  // prints the answer.

	}
	
	// graph class
	public static class Graph {
		TreeSet <Integer>[] adj; // we use an adjacency list to represent the graph
		int v; // number of nodes
		boolean[] visited; // keep track of whether the node is visited or not
		int [] flag; // this will have three values, -1, 0, 1. 
		// -1: The node is unvisited
		// 0: the node added to the queue
		// 1: the node is visited

		@SuppressWarnings("unchecked")
		// the constructor
		Graph(int v) {
			this.v = v;
			adj = new TreeSet[v];
			for (int i = 0; i < v; i++)
				adj[i] = new TreeSet<Integer>();
		}
		
		
		// prints the graph
		void printGraph() {
			for(int i = 0; i < v; i++) {
				for (Integer index: adj[i]) {
					System.out.printf("The node %d is connected to %d\n",i+1, index+1);
				}
			}

		}
		
		// adds two nodes together
		// note: undirected graph
		void add(int i, int j) {
			adj[i].add(j);
			adj[j].add(i);
		}
		// delete the edge between 2 nodes
		void delete(int i, int j) {
			adj[i].remove(j);
			adj[j].remove(i);
		}
		
		// detect and remove cycles in the graph(uses BFs)	
		boolean detectAndRemoveCycles(int index) {
			flag = new int[v];
			for (int i = 0; i < v; i++) {
				flag[i] = -1;
			}
			Queue<Integer> q = new LinkedList<Integer>();
			q.add(index);  
			flag[index] = 0;

			while (!q.isEmpty()) {
				int cur = q.poll();
				flag[cur] = 1;
				for(Integer next: adj[cur]) {
					if (flag[next] == -1) {
						q.add(next);
						flag[next] = 0;
					}
					else if (flag[next] == 0) {
						delete(cur, next);
						return true;
					}
					else continue;
				}
			}
			return false;
		}
		// return the diameter of the tree by using BFS twice
		int findTheDiameter() {
			Random rndm = new Random(); // to start with a random node
			int startIndex = rndm.nextInt(v);

			Queue<Integer> q = new LinkedList<Integer>();
			q.add(startIndex);
			visited = new boolean[v];
			visited[startIndex] = true;
			int saveIndex = 0;  // to save the last index and use it in the second BFS
			while (!q.isEmpty()) {
				int cur = q.poll();				
				for (Integer next : adj[cur]) {
					if (!visited[next]) {
						q.add(next);
						visited[next] = true; 
						saveIndex = next;
					}
				}
			}

			int [] levels = new int[v]; // stores the levels of the tree
			// build up the level as we traverse far from our the start point
			q.add(saveIndex);
			for(int i = 0; i < v; i++) visited[i] = false;
			visited[saveIndex] = true;
			while (!q.isEmpty()) {
				int cur = q.poll();
				for (Integer next : adj[cur]) {
					if (!visited[next]) {
						q.add(next);
						visited[next] = true; 
						levels[next] = levels[cur] + 1;
					}

				}


			}
			Arrays.sort(levels);  
			return levels[v-1];
		}


	}
}