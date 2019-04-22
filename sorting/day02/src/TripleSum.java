import java.util.Arrays;
import java.util.HashMap;
import java.lang.Math;

public class TripleSum {

    static void printMap(HashMap<Integer, Integer> h) {
        System.out.print("{\n");
        for (Integer k : h.keySet()) {
            int v = h.get(k);
            System.out.printf("%d:\t%d\n", k, v);
        }
        System.out.print("}\n");
    }

    static int tripleSum(int arr[], int sum) {
        Arrays.sort(arr);
        int total = 0;
        for (int i = 0; i < arr.length; i++) {
            int S = sum - arr[i];
            HashMap<Integer, Integer> h = new HashMap();
            for (int j = 0; j < arr.length; j++) {
                if (j != i) {
                    if (h.get(arr[j]) == null) {
                        h.put(arr[j], 1);
                    } else {
                        int c = h.get(arr[j]);
                        h.put(arr[j], c+1);
                    }
                }
            }
            printMap(h);
            for (int j = 0; arr[j] <= S / 2; j++) {
                if (j != i) {
                    int J = S - arr[j];
                    System.out.printf("%d= %d, %d, %d", sum, arr[i], arr[j], J);
                    if (h.get(J) != null) {
                        total += Math.min(h.get(arr[j]), h.get(J));
                        System.out.print(" Y ");
                    }
                    System.out.print("\n");
                }
            }
            arr[i] = sum + 1;
        }
        return total;
    }
}
