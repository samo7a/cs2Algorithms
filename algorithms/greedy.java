
// minimum number of announcements problem solution
import java.util.*;

public class greedy {
    public static int oo = 987654321;
    public static void main(String[] Args) {
        // These events store the student sorted by their end time
        ArrayList<Event> eves = new ArrayList<Event>();
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        // Grab all the times
        for (int i = 0; i < n; i++) {
            int start = sc.nextInt();
            int end = sc.nextInt();
            eves.add(new Event(start, end));
        }

        // Sort by the end
        Collections.sort(eves);

        // we haven't informed any student yet
        int lastTimeInformingStudents = -oo;

        // Keep track of the answer
        int numOfInforms = 0;

        // Loop over the student
        for (Event e : eves) {

            // Check if the student arrived after the last announcement
            if (e.start > lastTimeInformingStudents) {
                numOfInforms++;
                lastTimeInformingStudents = e.end;
            } // otherwise the student heard the inform the last you talked
        }

        // Print the answer
        System.out.println(numOfInforms);
    }

    // Student interval
    public static class Event implements Comparable<Event> {
        int start, end;

        Event(int start, int end) {
            this.start = start;
            this.end = end;
        }
        
        public int compareTo(Event o) { // (this, o) (FIRST, SECOND)
            // if (this.end < o.end) return -1;  // FIRST comes first in ordering
            // if (this.end > o.end) return 1;   // FIRST comes later in ordering
            // if (this.end == o.end) return 0;  // FIRST can come first or later
            return Integer.compare(this.end, o.end);
        }
    }
}
