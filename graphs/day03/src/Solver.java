/**
 * Solver definition for the 8 Puzzle challenge
 * Construct a tree of board states using A* to find a path to the goal
 */

import java.util.*;

public class Solver {

    public int minMoves = -1;
    private State solutionState;
    private boolean solved = false;

    /**
     * State class to make the cost calculations simple
     * This class holds a board state and all of its attributes
     */
    private class State {
        // Each state needs to keep track of its cost and the previous state
        private Board board;
        private int moves; // equal to g-cost in A*
        public int cost; // equal to f-cost in A*
        private State prev;

        public State(Board board, int moves, State prev) {
            this.board = board;
            this.moves = moves;
            this.prev = prev;
            cost = moves + board.manhattan();
        }

        @Override
        public boolean equals(Object s) {
            if (s == this) return true;
            if (s == null) return false;
            if (!(s instanceof State)) return false;
            return ((State) s).board.equals(this.board);
        }
    }

    /*
     * Return the root state of a given state
     */
    private State root(State state) {
        // TODO: Your code here, unless you don't understand what this function is for and don't seem to need it
        return null;
    }

    /*
     * A* Solver
     * Find a solution to the initial board using A* to generate the state tree
     * and a identify the shortest path to the the goal state
     */
    public Solver(Board initial) {
        solutionState = new State(initial, 0, null);
        if (!initial.solvable()) { return; }
        HashMap<Board,Integer> visited = new HashMap<>();
        Queue<State> queue = new PriorityQueue<>((o1, o2) -> {return o1.cost - o2.cost;});
        State last;
        visited.put(solutionState.board, 0);
        queue.offer(solutionState);
        while (!queue.isEmpty()) {
            last = queue.poll();
//            last.board.printBoard();
            if (last.board.isGoal()) {
                solved = true;
                minMoves = last.moves;
                solutionState = last;
//                System.out.printf("Solution found in %d moves!\n", minMoves);
//                solutionState.board.printBoard();
                return;
            }
            for (Board b : last.board.neighbors()) {
                if (visited.get(b) == null || visited.get(b) > last.moves + 1) {
                    State n = new State(b, last.moves + 1, last);
                    visited.put(b, null);
                    queue.add(n);
                }
            }
        }
    }

    /*
     * Is the input board a solvable state
     * Research how to check this without exploring all states
     */
    public boolean isSolvable() {
        return solutionState.board.solvable();
    }

    /*
     * Return the sequence of boards in a shortest solution, null if unsolvable
     */
    public Iterable<Board> solution() {
        if (!isSolvable()) return null;
        LinkedList<Board> path = new LinkedList<>();
        State last = solutionState;
        path.add(last.board);
        while (last.prev != null) {
            last = last.prev;
            path.addFirst(last.board);
        }
        return path;
    }

    public State find(Iterable<State> iter, Board b) {
        for (State s : iter) {
            if (s.board.equals(b)) {
                return s;
            }
        }
        return null;
    }

    /*
     * Debugging space
     */
    public static void main(String[] args) {
        int[][] initState = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Board initial = new Board(initState);

        Solver solver = new Solver(initial);
    }


}
