
public class MergeSort extends SortAlgorithm {

    private static final int INSERTION_THRESHOLD = 10;

    /**
     * This is the recursive step in which you split the array up into
     * a left and a right portion, sort them, and then merge them together.
     * Use Insertion Sort if the length of the array is <= INSERTION_THRESHOLD
     *
     * Best-case runtime: O(NlgN)
     * Worst-case runtime: O(NlgN)
     * Average-case runtime: O(NlgN)
     *
     * Space-complexity: O(N)
     */
    @Override
    public int[] sort(int[] array) {
        if (array.length < 2) {
            return array;
        }
        if (array.length <= INSERTION_THRESHOLD) {
            InsertionSort insertionSorter = new InsertionSort();
            insertionSorter.sort(array);
            return array;
        }
        int halfsize = (array.length / 2);
        int[] left = new int[halfsize];
        int[] right = new int[array.length - halfsize];
        for (int i = 0; i < left.length; i++) {
            left[i] = array[i];
        }
        for (int i = 0; i < right.length; i++) {
            right[i] = array[i+halfsize];
        }
        int[] rightSorted = sort(right);
        int[] leftSorted = sort(left);
        return merge(leftSorted, rightSorted);
    }

    /**
     * Given two sorted arrays a and b, return a new sorted array containing
     * all elements in a and b. A test for this method is provided in `SortTest.java`
     */
    public int[] merge(int[] a, int[] b) {
        int i = 0;
        int j = 0;
        int[] array = new int[a.length+b.length];
        while (i+j < array.length) {
            if (i == a.length) {
                array[i+j] = b[j];
                j++;
            } else if (j == b.length) {
                array[i+j] = a[i];
                i++;
            } else if (a[i] <= b[j]) {
                array[i+j] = a[i];
                i++;
            } else {
                array[i+j] = b[j];
                j++;
            }
        }
        return array;
    }

}
