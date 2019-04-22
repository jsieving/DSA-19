import java.util.Collections;
import java.util.PriorityQueue;

public class Problems {

    public static int leastSum(int[] A) {
        int total = 0;
        PriorityQueue<Integer> minPQ = new PriorityQueue<>(Collections.reverseOrder());
        for (int n : A) { minPQ.add(n); }
        int size = minPQ.size();
        int removed = 0;
        int magnitude = 1;
        while (size > 0) {
            total += minPQ.poll() * magnitude;
            size--;
            removed++;
            if (removed % 2 == 0) { magnitude = magnitude * 10; }
        }
        return total;
    }
}
