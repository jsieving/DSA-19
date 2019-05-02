import java.util.*;

class SortSpans implements Comparator<int[]>
{
    public int compare(int[] a, int[] b)
    {
        return (a[1]-a[0]) - (b[1]-b[0]);
    }
}

public class BarnRepair {

    public static int solve(int M, int[] occupied) {
        // figure out what's occupied in a fast way
        HashMap<Integer, Void> occSet = new HashMap<Integer, Void>();
        // find the first and last occupied stalls
        int curr = occupied[0];
        int last = 0;
        for (int i : occupied) {
            occSet.put(i, null);
            if (i > last) {last = i;}
            if (i < curr) {curr = i;}
        }

        // figure out which stalls to unnecessarily cover first
        Queue<int[]> uncovered = new PriorityQueue<>(new SortSpans());

        // need to get this <= M
        int coverSpans = 0;
        // this will be your answer
        int coverStalls = occupied.length;

        int start; // start of a span
        int end = curr; // end of a span
        int[] pair; // a span
        boolean occFlag = true; // current span is occupied

        while (end < last) { // you don't need to keep going if the last loop ended at the last stall
            start = curr; // wherever you left off, start there
            while (occSet.containsKey(curr) == occFlag && curr <= last) { // check that curr has the same occupancy as previous
                end = curr; // move the end to match "first" - start and end can be same
                curr++; // increase first - if the loop ends here, it'll start off the next span
            }

            if (occFlag) {
                coverSpans++;
                occFlag = !occFlag;
            } else {
                pair = new int[] {start, end};
                uncovered.add(pair);
                occFlag = !occFlag;
            }
        }

        while (coverSpans > M) {
            pair = uncovered.poll();
            coverSpans--;
            coverStalls += pair[1] - pair[0] + 1;
        }

        return coverStalls;
    }
}