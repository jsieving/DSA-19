import java.util.*;

public class RadioTowers {
    static class Tower {
        double x, y;
        Tower(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    private static double getDist(Tower t1, Tower t2) {
        double xDiff = t1.x - t2.x;
        double yDiff = t1.y - t2.y;
        return Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }

    private static boolean isWithin(Tower t1, Tower t2, int dist) {
        return getDist(t1, t2) <= dist;
    }

    // Strip contains a list of Towers sorted by x-coordinate, whose y-coordinates all fall in a 2-mile strip
    // Return true if no two towers are within 1 mile
    public static boolean checkStrip(List<Tower> strip) {
        for (int i = 0; i < strip.size(); i++) {
            for (int j = i + 1; j < Math.min(i+8, strip.size()); j++) {
                if (isWithin(strip.get(i), strip.get(j), 1)) {
                    return false;
                }
            }
        }
        return true;
    }

    // Return true if no two towers are within distance 1 of each other
    public static boolean validTowers(List<Tower> Lx, List<Tower> Ly) {
        assert Lx.size() == Ly.size();
        if (Ly.size() < 2) {return true;}
        double bottom = Ly.get(0).y;
        double top = Ly.get(Ly.size()-1).y;
        if (top - bottom <= 2) {
            return checkStrip(Lx);
        }
        double middle = (bottom + top) / 2;
        double overlap_low = middle - 1;
        double overlap_high = middle + 1;
        int second_half = 0;
        List<Tower> midY = new ArrayList<>();
        for (Tower curr : Ly) {
            if (overlap_low <= curr.y && curr.y <= overlap_high) {
                midY.add(curr);
            }
            if (curr.y <= middle) {
                second_half++;
            }
        }
        List<Tower> lowerY = Ly.subList(0, second_half);
        List<Tower> upperY = Ly.subList(second_half, Ly.size());
        List<RadioTowers.Tower> lowerX = new ArrayList<>(lowerY);
        Collections.sort(lowerX, Comparator.comparingDouble(o -> o.x));

        List<RadioTowers.Tower> midX = new ArrayList<>(midY);
        Collections.sort(midX, Comparator.comparingDouble(o -> o.x));

        List<RadioTowers.Tower> upperX = new ArrayList<>(upperY);
        Collections.sort(upperX, Comparator.comparingDouble(o -> o.x));

        System.out.print("------>\n");
        for (RadioTowers.Tower u : upperX) {
            System.out.printf("(%.1f, %.1f); ", u.x, u.y);
        }
        System.out.print("\n-----\n");
        for (RadioTowers.Tower v : midX) {
            System.out.printf("(%.1f, %.1f); ", v.x, v.y);
        }
        System.out.print("\n-----\n");
        for (RadioTowers.Tower w : lowerX) {
            System.out.printf("(%.1f, %.1f); ", w.x, w.y);
        }
        System.out.print("\n<------\n");

        return validTowers(upperX, upperY) && validTowers(lowerX, lowerY) && validTowers(midX, midY);
    }
//    public static boolean validTowers(List<Tower> Lx, List<Tower> Ly) {
//        assert Lx.size() == Ly.size();
//        int strip_start = 0;
//        int strip_end = 0;
//        int strip_y = 0;
//        while (strip_end < Ly.size()) {
//            while (strip_start < Ly.size() && Ly.get(strip_start).y <= strip_y) {
//                strip_start++;
//            }
//            while (strip_end < Ly.size() && Ly.get(strip_end).y <= strip_y + 2) {
//                strip_end++;
//            }
//            List<Tower> strip = Ly.subList(strip_start, strip_end); // Why is ArrayList provided? Is it better for this?
//            if (!checkStrip(strip)) { return false; }
//            strip_y++;
//        }
//        return true;
//    }
}
