import java.util.ArrayList;
import java.util.List;

public class NQueens {


    /**
     * Checks the 45° and 135° diagonals for an existing queen. For example, if the board is a 5x5
     * and you call checkDiagonal(board, 3, 1), The positions checked for an existing queen are
     * marked below with an `x`. The location (3, 1) is marked with an `o`.
     *
     * ....x
     * ...x.
     * x.x..
     * .o...
     * .....
     *
     * Returns true if a Queen is found.
     *
     * Do not modify this function (the tests use it)
     */
    public static boolean checkDiagonal(char[][] board, int r, int c) {
        int y = r - 1;
        int x = c - 1;
        while (y >= 0 && x >= 0) {
            if (board[y][x] == 'Q') return true;
            x--;
            y--;
        }
        y = r - 1;
        x = c + 1;
        while (y >= 0 && x < board[0].length) {
            if (board[y][x] == 'Q') return true;
            x++;
            y--;
        }
        return false;
    }


    /**
     * Creates a deep copy of the input array and returns it
     */
    private static char[][] copyOf(char[][] A) {
        char[][] B = new char[A.length][A[0].length];
        for (int i = 0; i < A.length; i++)
            System.arraycopy(A[i], 0, B[i], 0, A[0].length);
        return B;
    }

    public static void printBoard(char[][] board) {
        for (char[] row : board) {
            for (char c : row) {
                System.out.printf(" %c", c);
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }

    public static void nQueensHelper(int row, List<Integer> cols, char[][] curr, List<char[][]> solutions) {
        if (cols.size() == 0) {
            solutions.add(curr);
        }
        for (int c : cols) {
            if (!checkDiagonal(curr, row, c)) {
                char[][] curr_temp = copyOf(curr);
                List<Integer> cols_temp = new ArrayList<>(cols);
                cols_temp.remove(Integer.valueOf(c));
                curr_temp[row][c] = 'Q';
                nQueensHelper(row + 1, cols_temp, curr_temp, solutions);
            }
        }

    }

    public static List<char[][]> nQueensSolutions(int n) {
        List<Integer> cols = new ArrayList<>();
        for(int i = 0; i < n; i++) {cols.add(i);}
        char[][] board = new char[n][n];
        for (int x = 0; x < n; x++) {
            for (int y = 0; y < n; y++) {
                board[x][y] = '.';
            }
        }
        List<char[][]> answers = new ArrayList<>();
        nQueensHelper(0, cols, board, answers);
        return answers;
    }

}
