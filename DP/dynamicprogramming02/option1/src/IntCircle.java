public class IntCircle {

    static int[][] memo;
    static int[] circle;
    static int size;

    public static int maxValue(int[] C) {
        size = C.length;
        circle = C;

        memo = new int[size][size];
        for (int j = 2; j < size; j+=3) {
            for (int i = 0; i < size; i++) {
                memo[i][j] = maxValueOver(i, j);
            }
        }

        int max = 0;
        int test;
        for (int i = 0; i < size; i++) {
            test = memo[i][size-1];
            if (test > max) max = test;
        }
        printMemo();
        return max;
    }

    public static int maxValueOver(int i, int ext) {
        if (ext < 2) {return 0;}

        if (ext == 2) {
            int res = circle[i] * circle[mod(i+1)] * circle[mod(i+2)];
            return res;
        }

        int max = memo[mod(i)][2] + memo[mod(i+3)][ext-3]; // edge case where you kill 3 in a row
        int test;
        int lastProduct;
        int leftTotal;
        int rightTotal;

        for (int last = i + 1; last < i + ext; last+=3) {
            lastProduct = circle[i] * circle[mod(last)] * circle[mod(i+ext)];

            if (dist(i, last) >= 4) { leftTotal = memo[mod(i+1)][dist(i+1, last-1)]; }
            else { leftTotal = 0; }
            if (dist(last, i+ext) >= 4) { rightTotal = memo[mod(last+1)][dist(last+1, i+ext-1)]; }
            else {rightTotal = 0; }

            test = leftTotal + lastProduct + rightTotal;
            if (test > max) { max = test; }
        }

        memo[i][ext] = max;
        return memo[i][ext];
    }

    private static void printMemo() {
        System.out.print("   { ");
        for (int j = 0; j < size; j++) {
            System.out.printf("%d, ", circle[j]);
        }
        System.out.print("}\n");
        for (int i = 0; i < size; i++) {
            System.out.printf("%d  [ ", circle[i]);
            for (int j = 2; j < size; j+=3) {
                System.out.printf("%d, ", memo[i][j]);
            }
            System.out.print("]\n");
        }
        System.out.print("\n");
    }

    private static int mod(int a) {
        while (a < 0) a += size;
        a = a % size;
        return a;
    }

    private static int dist(int a, int b) {
        int d = b - a;
        while (d < 0) d += size;
        d = d % size;
        return d;
    }
}