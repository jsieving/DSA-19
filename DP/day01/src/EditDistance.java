import java.util.ArrayList;
import java.util.Arrays;

public class EditDistance {

    static char[] A;
    static char[] B;
    static int[][] memo;

    public static int minEditDist(String a, String b) {
        char[] a_arr = a.toCharArray();
        char[] b_arr = b.toCharArray();
        A = new char[a_arr.length+1];
        B = new char[b_arr.length+1];
        System.arraycopy(a_arr, 0, A, 1, a_arr.length);
        System.arraycopy(b_arr, 0, B, 1, b_arr.length);

        memo = new int[A.length][B.length];

        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < B.length; j++) {
                memo[i][j] = distanceFunction(i, j);
            }
        }

        printMemo();

        return memo[A.length-1][B.length-1];
    }

    private static int distanceFunction(int i, int j){

        System.out.print("\n");

        // if one string is empty, just return the number of remaining characters in the other
        if (i == 0) return j;
        if (j == 0) return i;


        // if last characters are the same, cut them off and keep going
        if (A[i] == B[j]) {
            return memo[i-1][j-1];
        }

        // else, see which change provides the shortest distance:
        return 1 + Math.min(
                        Math.min(memo[i][j-1], // remove B[j] / insert B[j] to A
                                memo[i-1][j]), // remove A[i] / insert A[i] to B
                        memo[i-1][j-1]); // replace A[i] with B[j] / replace B[j] with A[i]
    }

    private static void printMemo() {
        for (int i = 0; i < A.length; i++) {
            System.out.print("[ ");
            for (int j = 0; j < B.length; j++) {
                System.out.printf("%d, ", memo[i][j]);
            }
            System.out.print("]\n");
        }
        System.out.print("\n");
    }
}
