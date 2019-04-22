import java.util.LinkedList;

public class CountingSort {

    /**
     * Use counting sort to sort non-negative integer array A.
     * Runtime: O(n+k)  must sort each element, and iterate through each of k locations
     *
     * k: maximum element in array A
     */
    static void countingSort(int[] A) {
        int max = 0;
        for (int n : A) {
            if (n > max) {
                max = n;
            }
        }
        LinkedList<Integer>[] counts = new LinkedList[max+1];
        for (int i = 0; i < counts.length; i++) {
            counts[i] = new LinkedList<>();
        }
        for (int n : A) {
            LinkedList l = counts[n];
            l.add(n);
        }
        int i = 0;
        for (LinkedList<Integer> l : counts) {
            for (int n : l) {
                A[i] = n;
                i++;
            }
        }
    }

}
