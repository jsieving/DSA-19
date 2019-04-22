package divide_and_conquer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Skyline {

    public static class Point {
        public int x;
        public int y;
        private Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static class Building {
        private int l, r, h;
        public Building(int l, int r, int h) {
            this.l = l;
            this.r = r;
            this.h = h;
        }
    }

    public static void printSkyline(List<Point> s) {
        System.out.print("----->\n");
        for (Point p : s) {
            System.out.printf("[%d, %d]; ", p.x, p.y);
        }
        System.out.print("<-----\n");

    }

    // Given an array of buildings, return a list of points representing the skyline
    public static List<Point> skyline(Building[] B) {
        if (B.length == 0) {
            return new ArrayList<>();
        } else if (B.length ==1) {
            return toSkyline(B[0]);
        }
        int mid = B.length/2;
        Building[] leftHalf = Arrays.copyOfRange(B, 0, mid);
        Building[] rightHalf = Arrays.copyOfRange(B, mid, B.length);
        List<Point> leftSkyline = skyline(leftHalf);
        List<Point> rightSkyline = skyline(rightHalf);
        return merge(leftSkyline, rightSkyline);
    }

    public static List<Point> toSkyline(Building b) {
        int start = b.l;
        int end = b.r;
        int height = b.h;
        Point p1 = new Point(start, height);
        Point p2 = new Point(end, 0);
        List<Point> result = new ArrayList<>();
        result.add(p1);
        result.add(p2);
        return result;
    }

public static List<Point> merge(List<Point> s1, List<Point> s2) {
        int i = 0;
        int j = 0;
        int H1 = 0;
        int H2 = 0;
        int currH = 0;
        int L;
        Point b1;
        Point b2;
        List<Point> results = new ArrayList<>();
        while (i < s1.size() && j < s2.size()) {
            b1 = s1.get(i);
            b2 = s2.get(j);
            if (b1.x == b2.x) {
                L = b1.x;
                H1 = b1.y;
                H2 = b2.y;
                i++; j++;
            } else if (b1.x < b2.x) {
                L = b1.x;
                H1 = b1.y;
                i++;
            } else {
                L = b2.x;
                H2 = b2.y;
                j++;
            }
            if (Math.max(H1, H2) != currH) {
                currH = Math.max(H1, H2);
                results.add(new Point(L, currH));
            }
        }
        while (i < s1.size()) { results.add(s1.get(i)); i++;}
        while (j < s2.size()) { results.add(s2.get(j)); j++;}

        return results;
    }
}
