// important imports
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class main {
	// main method
    public static void main(String[] Args) {
    	
/***************************** READING THE INPUT PHASE **********************************/
    	// create a scanner object to read in from standard input
        Scanner scnr = new Scanner(System.in);
    	int nodes = scnr.nextInt();  // no. of pedestals
    	int edges = scnr.nextInt();  // no. of paths
    	//Disjoint graph = new Disjoint(nodes); // just for testing
    	ArrayList<Event> events = new ArrayList<>();  // array list of events
    	
    	// reading in all the paths and add it to the events
    	for (int i = 0; i < edges; i++) {
    		int type = 1;
    		int start = scnr.nextInt() - 1;
    		int end = scnr.nextInt() - 1;
    		int weight = scnr.nextInt();
    		events.add(new Event(type, start, end, weight));
    		//graph.connect(start, end);   just for testing
		}
    	
    	int archaeologists = scnr.nextInt(); // no of queries
    	// reading in all the queries and add it to the events
    	for (int i = 0; i < archaeologists; i++) {
			int type = 0;
			int start = scnr.nextInt() -1;
			int end = scnr.nextInt() - 1;
			int weight = scnr.nextInt();
			events.add(new Event(type, start, end, weight));
		}
    	scnr.close();
    	
    	Collections.sort(events); // sort the events
    	System.out.println(events); // just for testing
//    	for (int i = 0; i < nodes; i++) {
//			// just for testing
//    		System.out.println(graph.parent[i]);
//		}  
    	
/********************* COMPUTING QUERIES PHASE *****************************/
    	
    	boolean answer[] = new boolean[archaeologists]; // array to hold answers
    	Disjoint set = new Disjoint(nodes);  // disjoint set
    	
    	// loop through the events
    	for (int i = 0; i < events.size(); i++) {
    		// if it is a query, check if the start and the end are connected 
    		// if they are connected, so it is safe
    		// and it is not safe otherwise
			if(events.get(i).type == 0) { // query.
				if(set.isConnected(events.get(i).start, events.get(i).end))
					answer[events.get(i).index - 1] = true;
				else answer[events.get(i).index - 1] = false;

			}
			// if it is a path, just connect the two nodes (start and end).
			else {// path 
				set.connect(events.get(i).start, events.get(i).end);
			}
		}
    	
    	
    	
 /************************** PRINTING OUTPUT PHASE ***************************/
    	// print the answer array
    	for (int i = 0; i < archaeologists; i++) {
			if(answer[i] == true) System.out.println("Yes");
			else System.out.println("NO");
		}
    	
    }

   
    
    public static class Event implements Comparable<Event>{
    	int type; // zero for query, 1 for path
    	int start;
    	int end;
    	int weight;
    	int index; // index to identify each path or query
    	static int path = 0; // increment path indices
    	static int query = 0; // to increment query indices
    	
    	// constructor
    	Event(int type, int start, int end, int weight){
    		this.type = type;
    		this.start = start;
    		this.end = end;
    		this.weight = weight;
    		// assign and increment according to type
    		if(this.type == 0) this.index = ++query;
    		else this.index = ++path;
    	}
    	
    	// to sort events' weights from the highest to the lowest
    	public int compareTo(Event e) {
    		return Integer.compare(e.weight, this.weight);
    	}
    	
    	// to print the events, helped me in debugging 
    	public String toString() {
        	if (this.type == 0) return "Archaeologist #" + this.index + ", weight = " + this.weight + ", start = " + this.start + ", end " + this.end + "\n";
        	else return "Path #" + this.index + ", weight = " + this.weight + ", start = " + this.start + ", end " + this.end + "\n";
        }
    }
    
    
    public static class Disjoint {
        int[] parent;
        int[] rank; 
        
        Disjoint (int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                rank[i] = 1;
            }
        }
        
        // return the representative
        public int find(int node) {
            if (node == parent[node]) return node; 
            return find(parent[node]);
        }
         
        // connect two nodes
        public boolean connect(int a, int b) {
            int arep = find(a);
            int brep = find(b); 
            
            
            // here we are trying to avoid larger branches
            if (rank[arep] < rank[brep]) {
                parent[arep] = brep;
                return true;
            }

            parent[brep] = arep;
            if (rank[arep] == rank[brep]) {
                rank[arep]++;
            }
            return true;
        }
        
        // find out if 2 nodes are in the same set or not
        public boolean isConnected(int a, int b) {
            int arep = find(a);
            int brep = find(b); 
            // they have the same representative, then they are in the same set
            if (arep == brep) return true; 
            return false; // return false otherwise
        }
        
         
    }
}
