
public class InsertionSort extends SortAlgorithm {
    /**
     * Use the insertion sort algorithm to sort the array
     *
     * Best-case runtime: O(N)
     * Worst-case runtime: O(N^2)
     * Average-case runtime: 0(N^2)
     *
     * Space-complexity: O(1)
     */
    @Override
    public int[] sort(int[] array) {
        for (int i = 1; i < array.length; i++) {
            int j = i;
            while (j > 0 && array[j-1] > array[j]) {
                swap(array, j-1, j);
                j--;
            }
        }
        return array;
    }
}
