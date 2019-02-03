package your_code;
import ADTs.StackADT;

import java.util.LinkedList;

/**
 * An implementation of the Stack interface.
 */
public class MyStack implements StackADT<Integer> {

    private LinkedList<Integer> ll;
    private LinkedList<Integer> maxElems;

    public MyStack() {
        ll = new LinkedList<>();
        maxElems = new LinkedList<>();
    }

    @Override
    public void push(Integer e) {
        if (maxElems.isEmpty() || e >= maxElems.getFirst()) {
            maxElems.addFirst(e);
        }
        ll.addFirst(e);
    }

    @Override
    public Integer pop() {
        Integer pop = ll.removeFirst();
        if (!maxElems.isEmpty() && pop == maxElems.getFirst()) {
            maxElems.removeFirst();
        }
        return pop;
    }

    @Override
    public boolean isEmpty() {
        return ll.isEmpty();
    }

    @Override
    public Integer peek() {
        return ll.getFirst();
    }

    public Integer maxElement() {
        if (!maxElems.isEmpty()) {
            return maxElems.getFirst();
        } else {
            return 0;
        }
    }
}
