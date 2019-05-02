import java.util.Arrays;

public class DiceRollSum {

    private static int[] memo;
    // Runtime: O(N)
    // Space: O(N)
    public static int diceRollSum(int N) {
        memo = new int[N+1];
        memo[0] = 1;

        return findWays(N);
    }

    private static int findWays(int goal) {
        if (memo[goal] != 0) {
            return memo[goal];
        }

        int ways = 0;
        for (int i = 1; i <= 6; i++) {
            if (goal - i >= 0) {
                ways += findWays(goal - i);
            }
        }

        memo[goal] = ways;
        return ways;
    }

}
