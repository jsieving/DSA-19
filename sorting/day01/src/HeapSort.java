public class HeapSort extends SortAlgorithm {
    int size;
    int[] heap;

    private int parent(int i) {
        return (i-1) / 2;
    }

    private int leftChild(int i) { return 2*i + 1; }

    private int rightChild(int i) { return 2*(i+1); }

    // Check children, and swap with larger child if necessary.
    // Corrects the position of element indexed i by sinking it.
    // Use either recursion or a loop to then sink the child
    public void sink(int i) { // O(lgn)
        int left = leftChild(i);
        int right = rightChild(i);
        int leftVal = heap[i];
        int rightVal = leftVal;
        int sinkto;
        if (left < size) {
            leftVal = heap[left];
        }
        if (right < size) {
            rightVal = heap[right];
        }

        if (heap[i] >= leftVal && heap[i] >= rightVal) {
            return;
        }

        if (rightVal > leftVal) {
            sinkto = right;
        } else {
            sinkto = left;
        }
        swap(heap, i, sinkto);
        sink(sinkto);
    }

    // Given the array, build a heap by correcting every non-leaf's position, starting from the bottom, then
    // progressing upward
    public void heapify(int[] array) { // O(n), essentially because each sink op is shorter
        this.heap = array;
        this.size = array.length;
        for (int i = this.size / 2 - 1; i >= 0; i--) { //
            sink(i);
        }
    }

    /**
     * Best-case runtime: O(nlgn)
     * Worst-case runtime: O(nlgn)
     * Average-case runtime: O(nlgn)
     *
     * Space-complexity: O(1)
     */
    @Override
    public int[] sort(int[] array) { // O(nlgn) sinking top element n times to arbitrary height
        heapify(array);
        int temp = size;
        while (size > 0) {
            size--;
            swap(array, size, 0);
            sink(0);
        }
        size = temp;
        return heap;
    }
}
