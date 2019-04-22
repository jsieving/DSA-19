import javax.print.attribute.HashDocAttributeSet;
import java.util.HashMap;
import java.util.LinkedList;

public class Problems {

    static void sortNumsBetween100s(int[] A) { // O(n+200)
        int counts[] = new int[201];
        for (int n:A) {
            counts[n+100]++;
        }
        int j = 0;
        for (int i = -100; i < 101; i++) {
            while (counts[i+100] > 0) {
                A[j] = i;
                counts[i+100]--;
                j++;
            }
        }
    }

    /**
     * @param n the character number, 0 is the rightmost character
     * @return
     */
    private static int getNthCharacter(String s, int n) {
        return s.charAt(s.length() - 1 - n) - 'a';
    }


    /**
     * Use counting sort to sort the String array according to a character
     *
     * @param n The digit number (where 0 is the least significant digit)
     */
    static void countingSortByCharacter(String[] A, int n) { // O(n+26)
        LinkedList<String>[] L = new LinkedList[26];
        for (int i = 0; i < 26; i++) {
            L[i] = new LinkedList<>();
        }
        for (String s : A) {
            int c = getNthCharacter(s, n);
            L[c].add(s);
        }
        int j = 0; // index in A to place numbers
        for (LinkedList<String> list : L) {
            for (String s : list) {
                A[j] = s;
                j++;
            }
        }
    }

    /**
     * @param stringLength The length of each of the strings in S
     */
    static void sortStrings(String[] S, int stringLength) { // O(w(n+26))   (wordlen * (words+26))
        // Calculate the upper-bound for numbers in A
        int w = 0;
        for(String s : S) {
            if (s.length() > w) {
                w = s.length();
            }
        }
        for (int n = 0; n < w; n++) {
            countingSortByCharacter(S, n);
        }
    }

    /**
     * @param A The array to count swaps in
     */

    public static int countSwaps(int[] A) { // O(nlogn)
        int[] count = new int[1];
        mergeSort(A, count);
        return count[0];
    }

    public static int[] mergeSort(int[] array, int[] count) {
        if (array.length < 2) {
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
        int[] rightSorted = mergeSort(right, count);
        int[] leftSorted = mergeSort(left, count);
        return merge(leftSorted, rightSorted, count);
    }

    public static int[] merge(int[] a, int[] b, int[] count) {
        int i = 0;
        int j = 0;
        int[] array = new int[a.length+b.length];
        while (i+j < array.length) {
            if (i == a.length) {
                array[i+j] = b[j];
                count[0] += Math.max(0, a.length-i);
                j++;
            } else if (j == b.length) {
                array[i+j] = a[i];
                i++;
            } else if (a[i] <= b[j]) {
                array[i+j] = a[i];
                i++;
            } else {
                array[i+j] = b[j];
                count[0] += Math.max(0, a.length-i);
                j++;
            }
        }
        return array;
    }
}
