import java.util.HashMap;

public class LargestSubArray {

    static void printArr(int[] array, String s) {
        System.out.printf("%s[", s);
        for (int n : array) {
            System.out.printf("%d, ", n);
        }
        System.out.print("]\n");
    }

    static void printMap(HashMap<Integer, int[]> h) {
        System.out.print("{\n");
        for (Integer k : h.keySet()) {
            int[] v = h.get(k);
            System.out.printf("%d:\t%d,\t%d\n", k, v[0], v[1]);
        }
        System.out.print("}\n");
    }

    static int[] largestSubarray(int[] nums) {
        int[] totals = new int[nums.length+1];
        totals[0] = 0;
        HashMap<Integer,int[]> h = new HashMap<>();
        int[] initial = {0, 0};
        h.put(0, initial);
        int longestKey = 0;
        int longestLen = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                totals[i+1] = totals[i] - 1;
            } else {
                totals[i+1] = totals[i] + 1;
            }

            if (h.get(totals[i+1]) == null) {
                int[] entry = {i+1, i+1};
                h.put(totals[i+1], entry);
            } else {
                int[] entry = h.get(totals[i+1]);
                entry[1] = i;
                int len = entry[1] - entry[0];
                if (len > longestLen) {
                    longestLen = len;
                    longestKey = totals[i+1];
                }
            }
        }
        return h.get(longestKey);
    }
}
