import java.util.Arrays;

public class BalloonPopping {

    static int[][] memo;
    static int[] balloons;
    static int size;

    public static int maxPoints(int[] B) {
        size = B.length;
        balloons = new int[size];
        System.arraycopy(B, 0, balloons, 0, B.length);

        memo = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++){
                if (i > j) memo[i][j] = 0;
                else memo[i][j] = -1;
            }
        }

        printMemo();
        int res = recurse(0, size-1);
        printMemo();
        return res;
    }

    public static int recurse(int i, int j) {
        if (i > j) {
            memo[i][j] = 0;
            return 0;
        }

        if (memo[i][j] != -1) {
            return memo[i][j];
        }

        if (i == j) {
            memo[i][j] = getBalloon(i-1) * getBalloon(i) * getBalloon(i+1);
            return memo[i][j];
        }

        int leftVal = recurse(i, j-1);
        int downVal = recurse(i+1, j);
        int leftFirst = leftVal + downVal * getBalloon(i-1) / getBalloon(j);
        int downFirst = downVal + leftVal * getBalloon(j+1) / getBalloon(i+1);

        memo[i][j] = Math.max(leftFirst, downFirst);
        return memo[i][j];
    }

    public static int getBalloon(int i) {
        if (0<=i && i<size) {
            return balloons[i];
        }
        return 1;
    }

    private static void printMemo() {
        for (int i = 0; i < size; i++) {
            System.out.print("[ ");
            for (int j = 0; j < size; j++) {
                System.out.printf("%d, ", memo[i][j]);
            }
            System.out.print("]\n");
        }
        System.out.print("\n");
    }
}
