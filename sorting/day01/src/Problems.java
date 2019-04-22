import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class Problems {

    private static PriorityQueue<Integer> minPQ() {
        return new PriorityQueue<>(11);
    }

    private static PriorityQueue<Integer> maxPQ() {
        return new PriorityQueue<>(11, Collections.reverseOrder());
    }

    private static double getMedian(List<Integer> A) {
        double median = (double) A.get(A.size() / 2);
        if (A.size() % 2 == 0)
            median = (median + A.get(A.size() / 2 - 1)) / 2.0;
        return median;
    }

    // Runtime of this algorithm is O(N^2). Sad! We provide it here for testing purposes
    public static double[] runningMedianReallySlow(int[] A) {
        double[] out = new double[A.length];
        List<Integer> seen = new ArrayList<>();
        for (int i = 0; i < A.length; i++) {
            int j = 0;
            while (j < seen.size() && seen.get(j) < A[i])
                j++;
            seen.add(j, A[i]);
            out[i] = getMedian(seen);
        }
        return out;
    }


    /**
     *
     * @param inputStream an input stream of integers
     * @return the median of the stream, after each element has been added
     */
    public static double[] runningMedian(int[] inputStream) {
        double[] runningMedian = new double[inputStream.length];
        PriorityQueue<Integer> min = minPQ();
        PriorityQueue<Integer> max = maxPQ();
        int temp;
        if (inputStream.length > 0) {
            runningMedian[0] = inputStream[0];
            max.offer(inputStream[0]);
        }
        for (int i = 1; i < inputStream.length; i++) {
            if (inputStream[i] > runningMedian[i-1]) {
                min.offer(inputStream[i]);
            } else {
                max.offer(inputStream[i]);
            }

            if (max.size() == min.size()) {
                runningMedian[i] = (max.peek() + min.peek()) / (float) 2;
            } else if (max.size() > min.size()) {
                if (max.size() - 1 == min.size()) {
                    runningMedian[i] = max.peek();
                } else {
                    temp = max.poll();
                    min.offer(temp);
                    runningMedian[i] = (max.peek() + min.peek()) / (float) 2;
                }
            } else {
                if (min.size() - 1 == max.size()) {
                    runningMedian[i] = min.peek();
                } else {
                    temp = min.poll();
                    max.offer(temp);
                    runningMedian[i] = (max.peek() + min.peek()) / (float) 2;
                }
            }
        }
        return runningMedian;
    }

}
