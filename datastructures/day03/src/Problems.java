import java.util.*;

public class Problems {

    public static class Node {
        int val;
        Node next;

        Node(int d) {
            this.val = d;
            next = null;
        }
    }

    public static class Tuple<S> {
        S right;
        S op;
        S left;

        Tuple(S right, S op, S left) {
            this.right = right;
            this.op = op;
            this.left = left;
        }
    }

    public static void printList(int[] l) {
        System.out.print("\n");
        for (int i : l) {
            System.out.printf("%d, ", i);
        }
        System.out.print("\n");
    }

    public static List<Integer> removeKDigits(int[] A, int k) {
        int[] numArr = new int[A.length+2];
        numArr[0] = 0;
        numArr[A.length+1] = 0;
        for (int i = 0; i < A.length; i++) {
            numArr[i+1] = A[i];
        }
        LinkedList<Integer> newList = new LinkedList<>();
        for (int n : numArr) {
            newList.add(n);
        }

        int count = 0;
        while (count < k) {
            Iterator<Integer> prev = newList.iterator();
            Iterator<Integer> curr = newList.iterator(); curr.next();
            Iterator<Integer> next = newList.iterator(); next.next(); next.next();
            while (curr.hasNext()) {
                int a = prev.next(); int b = curr.next(); int c = next.next();
                if (a <= b && b > c) {
                    curr.remove();
                    count++;
                    break;
                }
            }
        }
        newList.removeFirst();
        newList.removeLast();
        return newList;
    }

    public static boolean isPalindrome(Node n) {
        // Check if empty, find tail & length
        if (n == null) return true;
        Node head = n;
        Node tail = n;
        int length = 1;
        while (tail.next != null) {
            tail = tail.next;
            length++;
        }
        if (length < 4 && head.val == tail.val) {
            return true;
        }
        // Find 2 middle nodes (the innermost which need be compared)
        Node mid1 = n;
        for (int i = 0; i < length/2-1; i++) {
            mid1 = mid1.next;
        }
        Node mid2 = mid1.next;
        if (length % 2 != 0) {
            mid2 = mid2.next;
        }
        // Connect each half into a closed loop
        mid1.next = head;
        tail.next= mid2;
        // Reverse one of the loops
        Node prev = tail;
        Node curr = mid2;
        Node temp;
        if (length > 5) {               // for short lists, reversing is not needed
            while (tail.next == mid2) {
                temp = curr.next;
                curr.next = prev;
                prev = curr;
                curr = temp;
            }
        }
        // Run through each loop and compare each element
        for (int i = 0; i < length / 2; i++) {
            if (head.val == tail.val) {
                head = head.next;
                tail = tail.next;
            } else {
                return false;
            }
        }
        return true;
    }

    public static String infixToPostfix(String s) {
        char[] sArr = s.toCharArray();
        char[] strip = new char[sArr.length];
        int l = 0;
        for (char c : sArr) {
            if (c != ' ') {
                strip[l] = c;
                l++;
            }
        }
        char[] text = new char[l];
        for (int i = 0; i < l; i++) {
            text[i] = strip[i];
        }

        LinkedList stack = new LinkedList();
        StringBuilder sb = new StringBuilder();
        sb.append(text);
        infixToStack(sb, stack);
        String res = parseStack(stack);

        return res;
    }

    private static String infixToStack(StringBuilder p, LinkedList s) {
        int start = 0;
        int end = 0;
        int length = p.length();
        if (length < 2) {
            return p.toString();
        }
        if (p.charAt(0) == '(' && p.charAt(length-1) == ')') {
            start++; end++; length--;
        }

        String left = null;
        String op;
        String right;

        int paren = 0;
        while (end < 2 || paren != 0) {
            if (p.charAt(end) == '(') {
                paren++;
            } else if (p.charAt(end) ==')') {
                paren--;
            }
            end++;
        }

        left = p.substring(start, end);

        start = end;
        end++;

        if (end < length) {
            op = p.substring(start, end);
            start++; end = length;
        } else {
            return left;
        }

        right = p.substring(start, end);

        StringBuilder R = new StringBuilder();
        StringBuilder L = new StringBuilder();
        R.append(right);
        L.append(left);

        Tuple<String> t = new Tuple<>(infixToStack(L, s), op, infixToStack(R, s));
        s.push(t);
        return "";
    }

    private static String parseStack(LinkedList s) {
        Iterator i = s.iterator();
        if (s.peek() == null) {
            return "";
        }
        Tuple t = (Tuple) s.pop();
        if (t.right == "") {
            t.right = parseStack(s);
        }
        if (t.left == "") {
            t.left = parseStack(s);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(t.right);
        sb.append(' ');
        sb.append(t.left);
        sb.append(' ');
        sb.append(t.op);

        return sb.toString();
    }

}
