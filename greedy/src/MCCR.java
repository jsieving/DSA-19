import java.util.ArrayList;

public class MCCR {
        public static int MCCR(EdgeWeightedGraph G) {
            Integer fakeInf = Integer.MAX_VALUE;
            IndexPQ<Integer> queue = new IndexPQ<>(G.numberOfV());
            ArrayList<Integer> spanned = new ArrayList<Integer>();
            int res = 0;
            queue.insert(0, 0); // add first node with 0 weight
            for (int v = 1; v < G.numberOfV(); v++) { // add other nodes with max weight
                queue.insert(v, fakeInf);
            }
            while (!queue.isEmpty()) {
                res += queue.getMinWeight(); // add weight of path by which 'u' is connected
                int u = queue.delMin(); // u is connected, don't need to queue it
                spanned.add(u);
                for (Edge e: G.edges(u)) {
                    int v = e.other(u);
                    if (queue.contains(v)) {
                        queue.lesserKey(v, e.weight());
                    }
                }
            }
            return res;
        }
    }

