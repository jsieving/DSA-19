public class SplitCoins {

    static int total;
    static int[][] memo;
    static int[] coins;
    static int num;

    public static int splitCoins(int[] C) {
        total = 0;
        coins = new int[C.length+1];
        num = C.length;
        for (int i = 0; i < num; i++) { // get sum of coins
            total += C[i];
            coins[i+1] = C[i];
        }

        memo = new int[num+1][(total/2)+1]; // make grid with rows: coins included & cols: total in 1 pile
        for (int i = 0; i <= num; i++) {
            for (int j = 0; j <= total/2; j++) {
                memo[i][j] = -1;
            }
        }

        memo[0][0] = 1;

        int col = total / 2 + 1;
        int res;
        boolean found = false;
        while (!found && col >= 0) {
             col--;
             res = recurse(num, col);
             if (res == 1) {
                 found = true;
             }
        }
        return Math.abs((total - 2 * col));
    }

    public static int recurse(int i, int pileSum) {
        if (pileSum < 0) return 0; // negative sum is impossible
        if (i == 0 && pileSum > 0) {
            memo[i][pileSum] = 0; // if there are no coins, positive sum is impossible
        }
        if (i == 0 && pileSum == 0) {
            memo[i][pileSum] = 1; // if there are no coins, 0 sum is possible
        }

        if (memo[i][pileSum] != -1) { // if there's a stored answer, return it
            return memo[i][pileSum];
        }

        // max is used as EITHER here: if either placement is possible, it's possible
        memo[i][pileSum] = Math.max(recurse(i-1, pileSum), // last coin is used in "other" pile
                                    recurse(i-1, pileSum-coins[i])); // last coin is used in this pile
        return memo[i][pileSum];
    }

    private static void printMemo() {
        for (int i = 0; i <= num; i++) {
            System.out.print("[ ");
            for (int j = 0; j <= total; j++) {
                System.out.printf("%d, ", memo[i][j]);
            }
            System.out.print("]\n");
        }
        System.out.print("\n");
    }
}
