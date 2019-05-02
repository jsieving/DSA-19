public class DungeonGame {

    static int[][] memo;
    static int[][] map;
    static int width;
    static int height;

    public static int minInitialHealth(int[][] dungeon) {
        map = dungeon;
        height = map.length;
        width = map[0].length;

        memo = new int[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                memo[i][j] = -1;
            }
        }
        return minHealthToEnter(0, 0);
    }

    public static int minHealthToEnter(int x, int y) {
        int fake_inf = Integer.MAX_VALUE;

        // if at target, return value required to "survive" space
        if (x == width-1 && y == height-1) {
            // even if there is a boost on that space, you need 1 health to go there
            memo[y][x] = Math.max(1, -1 * map[y][x] + 1);
            return memo[y][x];
        }

        // if the trip from this space has already been memoized, return it
        if (memo[y][x] != -1) {
            return memo[y][x];
        }

        // set these in case one is off the grid and doesn't get set
        int right = fake_inf; int down = fake_inf;

        // find health needed to survive going to next space (have >=1 left after being there)
        // check minimum for right and down moves
        if (x+1 < width) {
            right = minHealthToEnter(x+1, y);
        }
        if (y+1 < height) {
            down = minHealthToEnter(x, y+1);
        }

        // whatever health you need to survive the next space,
        // you have to have left after being in the current space.
        // however if the current space has a bonus, you still need 1 to get here. No negatives!
        memo[y][x] = Math.max(Math.min(right, down) - map[y][x], 1);
        // and that tells you how much health you must have to start here.
        return memo[y][x];
    }
}
