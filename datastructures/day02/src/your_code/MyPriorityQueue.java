package your_code;

import java.util.LinkedList;

/**
 * An implementation of a priority Queue
 */
public class MyPriorityQueue {

    private LinkedList<Integer> ll;

    public MyPriorityQueue() { ll = new LinkedList<>(); }

    public void enqueue(int item) {
        if (ll.isEmpty()) {
            ll.add(item);
        } else {
            int i = 0;
            Object[] arr = ll.toArray();
            while (i < arr.length && item < (int) arr[i]) {
                i++;
            }
            ll.add(i, item);
        }
    }

    /**
     * Return and remove the largest item on the queue.
     */
    public int dequeueMax() {
        return ll.removeFirst();
    }

}