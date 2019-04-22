import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class Boomerang {

    public static int numberOfBoomerangs(int[][] points) {
        int total = 0;
        for (int[] p1 : points) {
            HashMap<Double, ArrayList> h = new HashMap<>();
            for (int[] p2 : points) {
                double dist = dist(p1, p2);
                ArrayList<int[]> list = h.get(dist);
                if (list != null) {
                    list.add(p2);
                } else {
                    list = new ArrayList<>();
                    list.add(p2);
                    h.put(dist, list);
                }
            }
            Collection<ArrayList> vals = h.values();
            for (ArrayList l : vals) {
                int len = l.size();
                total += len * (len-1);
            }
        }
        return total;
    }

    public static double dist(int[] a, int[] b) {
        double dx2 = Math.pow(a[0] - b[0], 2);
        double dy2 = Math.pow(a[1] - b[1], 2);
        return Math.sqrt(dx2 + dy2);
    }
}

