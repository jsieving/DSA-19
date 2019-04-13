import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Board definition for the 8 Puzzle challenge
 */
public class Board {

    private int n;
    public int[][] tiles;
    public int[] free;

    // Create a 2D array representing the solved board state
    private int[][] goal;

    private void createGoal() {
        goal = new int[n][n];
        int i = 1;
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                goal[r][c] = i % (n*n);
                i++;
            }
        }
    }

    /*
     * Set the global board size and tile state
     */
    public Board(int[][] b) {
        n = b.length;
        tiles = new int[n][n];
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                tiles[r][c] = b[r][c];
                if (b[r][c] == 0) {
                    free = new int[] {c, r};
                }
            }
        }
        createGoal();
    }

    /*
     * Size of the board 
     (equal to 3 for 8 puzzle, 4 for 15 puzzle, 5 for 24 puzzle, etc)
     */
    private int size() {
        return n;
    }

    public int dist(int i, int r, int c) {
        if (i == 0) return 0;
        i--;
        return Math.abs(i/n-r) + Math.abs(i%n-c);
    }

    /*
     * Sum of the manhattan distances between the tiles and the goal
     */
    public int manhattan() {
        int total = 0;
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                total += dist(tiles[r][c], r, c);
            }
        }
        return total;
    }

    /*
     * Compare the current state to the goal state
     */
    public boolean isGoal() {
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                if (goal[r][c] != tiles[r][c]) return false;
            }
        }
        return true;
    }

    public void printBoard() {
        System.out.print("{\n");
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                System.out.printf("%d, ", tiles[r][c]);
            }
            System.out.print("\n");
        }
        System.out.print("}\n");
    }

    /*
     * Returns true if the board is solvable
     * Research how to check this without exploring all states
     */
    public boolean solvable() {
        int inversions = 0;
        for (int i = 0; i < n*n; i++) {
            for (int j = i + 1; j < n*n; j++) {
                if (tiles[i/n][i%n] > tiles[j/n][j%n] || tiles[i/n][i%n] == 0) inversions ++;
            }
        }
        return inversions % 2 == 0;
    }

    /*
     * Return all neighboring boards in the state tree
     */
    public Iterable<Board> neighbors() {
        LinkedList<Board> boards = new LinkedList<>();
        if (free[0] > 0) { // slide from left if possible
            Board b = new Board(tiles);
            b.tiles[free[1]][free[0]] = b.tiles[free[1]][free[0]-1];
            b.tiles[free[1]][free[0]-1] = 0;
            b.free[0]--;
            boards.add(b);
        }
        if (free[0] < n-1) { // slide from right if possible
            Board b = new Board(tiles);
            b.tiles[free[1]][free[0]] = b.tiles[free[1]][free[0]+1];
            b.tiles[free[1]][free[0]+1] = 0;
            b.free[0]++;
            boards.add(b);
        }
        if (free[1] > 0) { // slide from top if possible
            Board b = new Board(tiles);
            b.tiles[free[1]][free[0]] = b.tiles[free[1]-1][free[0]];
            b.tiles[free[1]-1][free[0]] = 0;
            b.free[1]--;
            boards.add(b);
        }
        if (free[1] < n-1) { // slide from bottom if possible
            Board b = new Board(tiles);
            b.tiles[free[1]][free[0]] = b.tiles[free[1]+1][free[0]];
            b.tiles[free[1]+1][free[0]] = 0;
            b.free[1]++;
            boards.add(b);
        }
        return boards;
    }

    /*
     * Check if this board equals a given board state
     */
    @Override
    public boolean equals(Object x) {
        // Check if the board equals an input Board object
        if (x == this) return true;
        if (x == null) return false;
        if (!(x instanceof Board)) return false;
        // Check if the same size
        Board y = (Board) x;
        if (y.tiles.length != n || y.tiles[0].length != n) {
            return false;
        }
        // Check if the same tile configuration
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.tiles[i][j] != y.tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(tiles);
    }

    public static void main(String[] args) {
        // DEBUG - Your solution can include whatever output you find useful
        int[][] initState = {{1, 2, 3}, {4, 0, 6}, {7, 8, 5}};
        Board board = new Board(initState);

        System.out.println("Size: " + board.size());
        System.out.println("Solvable: " + board.solvable());
        System.out.println("Manhattan: " + board.manhattan());
        System.out.println("Is goal: " + board.isGoal());
        System.out.println("Neighbors:");
        Iterable<Board> it = board.neighbors();
    }
}
