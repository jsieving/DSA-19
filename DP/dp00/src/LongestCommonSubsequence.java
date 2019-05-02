import java.util.ArrayList;

public class LongestCommonSubsequence {

    static char[] A1;
    static char[] A2;
    static int[][] memo;

    // Runtime: O(M*N) (length of each string)
    // Space: O(M*N)
    public static int LCS(String S1, String S2) {
        A1 = S1.toCharArray();
        A2 = S2.toCharArray();
        memo = new int[A1.length][A2.length];
        for (int i = 0; i < A1.length; i++) {
            for (int j = 0; j < A2.length; j++) {
                memo[i][j] = -1;
            }
        }

        return recursiveFunction(0, 0);
    }

    private static void printMemo() {
        for (int i = 0; i < A1.length; i++) {
            System.out.print("[ ");
            for (int j = 0; j < A2.length; j++) {
                System.out.printf("%d, ", memo[i][j]);
            }
            System.out.print("]\n");
        }
        System.out.print("\n");
    }

    private static int recursiveFunction(int i, int j){

        System.out.print("\n");


        if (i == A1.length || j == A2.length) {
//            System.out.printf("Out of bounds: %d, %d\n", i, j);
            return 0;
        }

//        System.out.printf("%d, %d --- %c, %c\n", i, j, A1[i], A2[j]);

        // BASE CASES
        if (memo[i][j] != -1) {
//            System.out.printf("Memo at %d, %d is %d\n", i, j, memo[i][j]);
            return memo[i][j];
        }

        if (A1[i] == A2[j]) {
//            System.out.printf("Strings at %d, %d are equal: %c = %c\n", i, j, A1[i], A2[j]);
            memo[i][j] = 1 + recursiveFunction(i+1, j+1);
            return memo[i][j];
        }

//        System.out.printf("Strings at %d, %d are different: %c != %c\n", i, j, A1[i], A2[j]);
        memo[i][j] = Math.max(recursiveFunction(i, j+1), recursiveFunction(i+1, j));
        return memo[i][j];
    }
}