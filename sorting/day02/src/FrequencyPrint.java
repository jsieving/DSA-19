import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Arrays;
import java.util.LinkedList;

public class FrequencyPrint {

    static void printArr(ArrayList<String> l) {
        System.out.print("[");
        for (String s : l) {
            System.out.printf("%s, ", s);
        }
        System.out.print("]\n");
    }

    static void printArr(int[] array) {
        System.out.print("[");
        for (int n : array) {
            System.out.printf("%d, ", n);
        }
        System.out.print("]\n");
    }

    static void printHist(HashMap<String, Integer> h) {
        System.out.print("{\n");
        for (String k : h.keySet()) {
            int v = h.get(k);
            System.out.printf("%s:\t%d\n", k, v);
        }
        System.out.print("}\n");
    }

    static void printMap(HashMap<Integer, ArrayList> h) {
        System.out.print("{\n");
        for (Integer k : h.keySet()) {
            ArrayList<String> l = h.get(k);
            System.out.printf("%d:\t", k);
            for (String w : l) {
                System.out.printf("%s, ", w);
            }
            System.out.print("\n");
        }
        System.out.print("}\n");
    }

    static String frequencyPrint(String s) {
        String[] words = s.split(" ");
        HashMap<String, Integer> h = new HashMap<>();
        HashMap<Integer, ArrayList> inv = new HashMap<>();
        StringBuilder sb = new StringBuilder();

        for (String w : words) {
            if (h.get(w) == null) {
                h.put(w, 1);
            } else {
                int c = h.get(w);
                h.put(w, c+1);
            }
        }

        for (String w : h.keySet()) {
            int i = h.get(w);
            if (inv.get(i) == null) {
                ArrayList l = new ArrayList();
                for (int j = 0; j < i; j++) {
                    l.add(w);
                }
                inv.put(i, l);
            } else {
                ArrayList l = inv.get(i);
                for (int j = 0; j < i; j++) {
                    l.add(w);
                }
            }
        }

        int idx = 0;
        int[] keys = new int[inv.size()];
        for (int k : inv.keySet()) {
            keys[idx] = k;
            idx++;
        }

        Arrays.sort(keys);

        for (int k : keys) {
            ArrayList<String> l = inv.get(k);
            for (String w : l) {
                sb.append(w);
                sb.append(' ');
            }
        }
        return sb.toString();
    }

}
