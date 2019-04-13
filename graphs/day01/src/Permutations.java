import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Permutations {

    public static void permuteHelper(List<Integer> current, List<Integer> unused, List<List<Integer>>permutations) {
        if (unused.size() == 0) {
            permutations.add(current);
        }

        for (int n : unused) {
            List<Integer> current_temp = new LinkedList<>(current);
            current_temp.add(n);
            List<Integer> unused_temp = new LinkedList<>(unused);
            unused_temp.remove(Integer.valueOf(n));
            permuteHelper(current_temp, unused_temp, permutations);
        }
    }

    public static List<List<Integer>> permutations(List<Integer> A) {
        List<List<Integer>> permutations = new LinkedList<>();
        List<Integer> unused = new LinkedList<>(A);
        permuteHelper(new LinkedList<>(), unused, permutations);
        return permutations;
    }

}
