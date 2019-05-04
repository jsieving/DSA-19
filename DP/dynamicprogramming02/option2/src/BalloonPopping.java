public class BalloonPopping {

    static int[][] memo;
    static int[] balloons;
    static int size;

    public static int maxPoints(int[] B) {
        size = B.length;
        balloons = B;

        memo = new int[size][size]; // make a memo array: row is start of popped balloons, column is end
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++){
                if (i <= j) { memo[i][j] = -1; }
            }
        }

        int res = maxPointsOver(0, size-1); // find best way to pop balloons 0:size
//        printMemo();
        return res;
    }

    public static int maxPointsOver(int i, int j) {
        if (i > j) { // if start is more than end, don't do anything
            return 0;
        }

        if (memo[i][j] != -1) { // if this subarray has already been solved, return its result
            return memo[i][j];
        }

        if (i == j) { // if you're only popping one balloon, calculate the points you get
            memo[i][j] = getBalloon(i-1) * getBalloon(i) * getBalloon(i+1);
            return memo[i][j];
        }

        int max = 0; // max for this subarray
        int test; // result of popping a particular balloon last
        int lastProduct; // points from popping each last balloon
        for (int last = i; last <= j; last++) { // iterate through which balloon was popped last
            lastProduct = getBalloon(i-1) * getBalloon(last) * getBalloon(j+1); // points from that
            // sum the max points possible from popping the previous balloons on the left,
            // the previous balloons on the right, and this last one
            test = maxPointsOver(i, last-1) + lastProduct + maxPointsOver(last+1, j);
            if (test > max) { max = test; } // update the max
        }

        memo[i][j] = max;
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
