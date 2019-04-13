import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


// this is our implementation of a rubiks cube. It is your job to use A* or some other search algorithm to write a
// solve() function
public class RubiksCube {

    private BitSet cube;
    public int[][] cubies = new int[][]{{0, 16, 21}, {1, 17, 9}, {2, 5, 8}, {3, 4, 20}, {12, 19, 22}, {13, 18, 10}, {14, 6, 11}, {15, 7, 23}};

    // initialize a solved rubiks cube
    public RubiksCube() {
        // 24 colors to store, each takes 3 bits
        cube = new BitSet(24 * 3);
        for (int side = 0; side < 6; side++) {
            for (int i = 0; i < 4; i++) {
                setColor(side * 4 + i, side);
            }
        }
    }

    // initialize a rubiks cube with the input bitset
    private RubiksCube(BitSet s) {
        cube = (BitSet) s.clone();
    }

    // creates a copy of the rubics cube
    public RubiksCube(RubiksCube r) {
        cube = (BitSet) r.cube.clone();
    }

    // return true if this rubik's cube is equal to the other rubik's cube
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RubiksCube))
            return false;
        RubiksCube other = (RubiksCube) obj;
        return other.cube.equals(cube);
    }

    /**
     * return a hashCode for this rubik's cube.
     *
     * Your hashCode must follow this specification:
     *   if A.equals(B), then A.hashCode() == B.hashCode()
     *
     * Note that this does NOT mean:
     *   if A.hashCode() == B.hashCode(), then A.equals(B)
     */
    @Override
    public int hashCode() {
        return cube.hashCode();
    }

    // prints a cube
    public void printCube() {
        int color;
        System.out.print("{");
        for (int i = 0; i < 24; i++) {
            color = getColor(i);
            if ((i+1) % 4 == 0) System.out.printf("%d;  ", color);
            else System.out.printf("%d, ", color);
        }
        System.out.print("}\n");
    }

    private class State {
        // Each state needs to keep track of its cost and the previous state
        private RubiksCube rubiks;
        private int moves; // equal to g-cost in A*
        public double cost; // equal to f-cost in A*
        public char lastMove; // rotation taken to get here
        private State prev;

        public State(RubiksCube rubiks, int moves, char lastMove, State prev) {
            this.rubiks = rubiks;
            this.moves = moves;
            this.lastMove = lastMove;
            this.prev = prev;
            cost = (double) moves + cost();
        }

        @Override
        public boolean equals(Object s) {
            if (s == this) return true;
            if (s == null) return false;
            if (!(s instanceof State)) return false;
            return ((State) s).rubiks.equals(this.rubiks);
        }

        public double cost() {
            double total = 0;
            int correctColor;
            for (int[] cubie : cubies) {
                int cubieTotal = 0;
                for (int square : cubie) {
                    correctColor = square / 4;
                    if (correctColor != rubiks.getColor(square)) cubieTotal++;
                }
                total += Math.pow((double) cubieTotal, .652); // this is DUMB.
            }
            return total;
        }
    }

    public boolean isSolved() {
        return this.equals(new RubiksCube());
    }


    // takes in 3 bits where bitset.get(0) is the MSB, returns the corresponding int
    private static int bitsetToInt(BitSet s) {
        int i = 0;
        if (s.get(0)) i |= 4;
        if (s.get(1)) i |= 2;
        if (s.get(2)) i |= 1;
        return i;
    }

    // takes in a number 0-5, returns a length-3 bitset, where bitset.get(0) is the MSB
    private static BitSet intToBitset(int i) {
        BitSet s = new BitSet(3);
        if (i % 2 == 1) s.set(2, true);
        i /= 2;
        if (i % 2 == 1) s.set(1, true);
        i /= 2;
        if (i % 2 == 1) s.set(0, true);
        return s;
    }

    // index from 0-23, color from 0-5
    private void setColor(int index, int color) {
        BitSet colorBitset = intToBitset(color);
        for (int i = 0; i < 3; i++)
            cube.set(index * 3 + i, colorBitset.get(i));
    }


    // index from 0-23, returns a number from 0-5
    private int getColor(int index) {
        return bitsetToInt(cube.get(index * 3, (index + 1) * 3));
    }

    // given a list of rotations, return a rubik's cube with the rotations applied
    public RubiksCube rotate(List<Character> c) {
        RubiksCube rub = this;
        for (char r : c) {
            rub = rub.rotate(r);
        }
        return rub;
    }


    // Given a character in ['u', 'U', 'r', 'R', 'f', 'F'], return a new rubik's cube with the rotation applied
    // Do not modify this rubik's cube.
    public RubiksCube rotate(char c) {
        int[] faceFrom = null;
        int[] faceTo = null;
        int[] sidesFrom = null;
        int[] sidesTo = null;
        // colors move from the 'from' variable to the 'to' variable
        switch (c) {
            case 'u': // clockwise
            case 'U': // counterclockwise
                faceFrom = new int[]{0, 1, 2, 3};
                faceTo = new int[]{1, 2, 3, 0};
                sidesFrom = new int[]{4, 5, 8, 9, 17, 16, 21, 20};
                sidesTo = new int[]{21, 20, 4, 5, 8, 9, 17, 16};
                break;
            case 'r':
            case 'R':
                faceFrom = new int[]{8, 9, 10, 11};
                faceTo = new int[]{9, 10, 11, 8};
                sidesFrom = new int[]{6, 5, 2, 1, 17, 18, 13, 14};
                sidesTo = new int[]{2, 1, 17, 18, 13, 14, 6, 5};
                break;
            case 'f':
            case 'F':
                faceFrom = new int[]{4, 5, 6, 7};
                faceTo = new int[]{5, 6, 7, 4};
                sidesFrom = new int[]{3, 2, 8, 11, 14, 15, 23, 20};
                sidesTo = new int[]{8, 11, 14, 15, 23, 20, 3, 2};
                break;
            default:
                System.out.println(c);
                assert false;
        }
        // if performing a counter-clockwise rotation, swap from and to
        if (Character.isUpperCase(c)) {
            int[] temp;
            temp = faceFrom;
            faceFrom = faceTo;
            faceTo = temp;
            temp = sidesFrom;
            sidesFrom = sidesTo;
            sidesTo = temp;
        }
        RubiksCube res = new RubiksCube(cube);
        for (int i = 0; i < faceFrom.length; i++) res.setColor(faceTo[i], this.getColor(faceFrom[i]));
        for (int i = 0; i < sidesFrom.length; i++) res.setColor(sidesTo[i], this.getColor(sidesFrom[i]));
        return res;
    }

    // returns a random scrambled rubik's cube by applying random rotations
    public static RubiksCube scrambledCube(int numTurns) {
        RubiksCube r = new RubiksCube();
        char[] listTurns = getScramble(numTurns);
        for (int i = 0; i < numTurns; i++) {
            r= r.rotate(listTurns[i]);
        }
        return r;
    }

    public static char[] getScramble(int size){
        char[] listTurns = new char[size];
        for (int i = 0; i < size; i++) {
            switch (ThreadLocalRandom.current().nextInt(0, 6)) {
                case 0:
                    listTurns[i] = 'u';
                    break;
                case 1:
                    listTurns[i] = 'U';
                    break;
                case 2:
                    listTurns[i] = 'r';
                    break;
                case 3:
                    listTurns[i] = 'R';
                    break;
                case 4:
                    listTurns[i] = 'f';
                    break;
                case 5:
                    listTurns[i] = 'F';
                    break;
            }
        }
        return listTurns;
    }


    // return the list of rotations needed to solve a rubik's cube
    public List<Character> dummySolve() {
        // TODO
        Character[] solvestr = {'f', 'U', 'F', 'r', 'f'};
        ArrayList<Character> solution = new ArrayList<>(Arrays.asList(solvestr)) ;
        RubiksCube rubiks = new RubiksCube(this);
        rubiks.printCube();

        for (char c : solution) {
            rubiks = rubiks.rotate(c);
        }

        rubiks.printCube();
        if (rubiks.isSolved()) {
            System.out.print("Solved!");
        } else {
            System.out.print("You had ONE JOB!");
        }
        return solution;
    }

    private List<Character> unwrap(State curr) {
        LinkedList<Character> solution = new LinkedList<>();
        while (curr.lastMove != '\0') {
            solution.addFirst(curr.lastMove);
            curr = curr.prev;
        }
        return solution;
    }

    public List<Character> solve() {
        HashMap<RubiksCube,Integer> visited = new HashMap<>();
        // (o1, o2) -> {return o1.cost - o2.cost;} // for when cost is an int
        Queue<State> queue = new PriorityQueue<>((o1, o2) -> {return Double.compare(o1.cost, o2.cost);});
        visited.put(this, 0);
        State curr = new State(this, 0, '\0', null);
        queue.offer(curr);
        char[] moves = new char[]{'r', 'R', 'u', 'U', 'f', 'F'};
        RubiksCube newCube;

        while (!queue.isEmpty()) {
            curr = queue.remove();
            if (curr.rubiks.isSolved()) {
                return unwrap(curr);
            }
            for (char c : moves) {
                newCube = curr.rubiks.rotate(c);
                if (visited.get(newCube) == null || visited.get(newCube) > curr.moves + 1) {
                    visited.put(newCube, curr.moves + 1);
                    queue.add(new State(newCube, curr.moves + 1, c, curr));
                }
            }
        }
        return null;
    }

    public List<Character> BFSsolve() {
        HashMap<RubiksCube,Integer> visited = new HashMap<>();
        Queue<State> queue = new LinkedList<>();
        visited.put(this, 0);
        State curr = new State(this, 0, '\0', null);
        queue.offer(curr);
        char[] moves = new char[]{'r', 'R', 'u', 'U', 'f', 'F'};
        RubiksCube newCube;

        while (!queue.isEmpty()) {
            curr = queue.remove();
            if (curr.rubiks.isSolved()) {
                return unwrap(curr);
            }
            for (char c : moves) {
                newCube = curr.rubiks.rotate(c);
                if (visited.get(newCube) == null) {
                    visited.put(newCube, 0);
                    queue.add(new State(newCube, 0, c, curr));
                }
            }
        }
        return null;
    }
}