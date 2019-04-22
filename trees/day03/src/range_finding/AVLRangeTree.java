package range_finding;

import java.util.LinkedList;
import java.util.List;

public class AVLRangeTree extends BinarySearchTree<Integer> {

    public void printTree(RangeNode n, int d, char s) {
        if (d == 0) System.out.print('\n');
        if (n != null) {
            System.out.printf("%c", s);
            for (int i = 0; i < d; i++) {
                System.out.print(" _");
            }
            System.out.printf("%d : %d / %d\n", n.key, n.subLeft, n.subRight);
            printTree(n.leftChild, d + 1, 'L');
            printTree(n.rightChild, d + 1, 'R');
        }
    }

    /**
     * Delete a key from the tree rooted at the given node.
     */
    @Override
    RangeNode<Integer> delete(RangeNode<Integer> n, Integer key) {
        n = super.delete(n, key);
        if (n != null) {
            n.height = 1 + Math.max(height(n.leftChild), height(n.rightChild));
            if (!n.hasLeftChild()) {n.subLeft = 0;}
            else {n.subLeft = 1 + subLeft(n.leftChild) + subRight(n.leftChild);}
            if (!n.hasRightChild()) {n.subRight = 0;}
            else {n.subRight = 1 + subLeft(n.rightChild) + subRight(n.rightChild);}
            return balance(n);
        }
        return null;
    }

    /**
     * Insert a key into the tree rooted at the given node.
     */
    @Override
    RangeNode<Integer> insert(RangeNode<Integer> n, Integer key) {
        n = super.insert(n, key);
        if (n != null) {
            n.height = 1 + Math.max(height(n.leftChild), height(n.rightChild));
            if (!n.hasLeftChild()) {n.subLeft = 0;}
            else {n.subLeft = 1 + subLeft(n.leftChild) + subRight(n.leftChild);}
            if (!n.hasRightChild()) {n.subRight = 0;}
            else {n.subRight = 1 + subLeft(n.rightChild) + subRight(n.rightChild);}
            return balance(n);
        }
        return null;
    }

    /**
     * Delete the minimum descendant of the given node.
     */
    @Override
    RangeNode<Integer> deleteMin(RangeNode<Integer> n) {
        n = super.deleteMin(n);
        if (n != null) {
            n.height = 1 + Math.max(height(n.leftChild), height(n.rightChild));
            if (!n.hasLeftChild()) {n.subLeft = 0;}
            else {n.subLeft = 1 + subLeft(n.leftChild) + subRight(n.leftChild);}
            return balance(n);
        }
        return null;
    }

    // Return the height of the given node. Return -1 if null.
    private int height(RangeNode x) {
        if (x == null) return -1;
        return x.height;
    }

    // Return the left subtree size of the given node. Return -1 if null.
    private int subLeft(RangeNode x) {
        if (x == null) return -1;
        return x.subLeft;
    }

    // Return the right subtree size of the given node. Return -1 if null.
    private int subRight(RangeNode x) {
        if (x == null) return -1;
        return x.subRight;
    }

    public int height() {
        return Math.max(height(root), 0);
    }

    // Restores the AVL tree property of the subtree.
    RangeNode<Integer> balance(RangeNode<Integer> x) {
        if (balanceFactor(x) > 1) {
            if (balanceFactor(x.rightChild) < 0) {
                x.rightChild = rotateRight(x.rightChild);
            }
            x = rotateLeft(x);
        } else if (balanceFactor(x) < -1) {
            if (balanceFactor(x.leftChild) > 0) {
                x.leftChild = rotateLeft(x.leftChild);
            }
            x = rotateRight(x);
        }
        return x;
    }

    // Return all keys that are below hi (inclusive).
    public int rankHi(int hi, RangeNode n) {
        if (n == null) return 0;
        if ((int) n.key <= hi) {
            return 1 + n.subLeft + rankHi(hi, n.rightChild);
        } else {
            return rankHi(hi, n.leftChild);
        }
    }

    // Count all keys that are above lo (inclusive).
    public int rankLo(int lo, RangeNode n) {
        if (n == null) return 0;
        if ((int) n.key >= lo) {
            return 1 + n.subRight + rankLo(lo, n.leftChild);
        } else {
            return rankLo(lo, n.rightChild);
        }
    }

    // Count all keys that are between [lo, hi] (inclusive).
    public int rankBoth(int lo, int hi, RangeNode n) {
        if (n == null) return 0;
        if (lo <= (int) n.key && (int) n.key <= hi) {
            return 1 + rankLo(lo, n.leftChild) + rankHi(hi, n.rightChild);
        } else if (lo > (int) n.key) {
            return rankBoth(lo, hi, n.rightChild);
        } else if ((int) n.key > hi) {
            return rankBoth(lo, hi, n.leftChild);
        } else { //
            return 0;
        }
    }

    public void rangeTraverse(int lo, int hi, RangeNode n, List l) {
        if (n == null) return;
        if ((int) n.key >= lo) {
            rangeTraverse(lo, hi, n.leftChild, l);
        }
        if (lo <= (int) n.key && (int) n.key <= hi) {
            l.add(n.key);
        }
        if ((int) n.key <= hi) {
            rangeTraverse(lo, hi, n.rightChild, l);
        }
    }

    // Return all keys that are between [lo, hi] (inclusive).
    // runtime = O(n)
    public List<Integer> rangeIndex(int lo, int hi) {
        List<Integer> l = new LinkedList<>();
        rangeTraverse(lo, hi, root, l);
        return l;
    }

    // return the number of keys between [lo, hi], inclusive
    // runtime = O(logN) (explained in nb)
    public int rangeCount(int lo, int hi) {
        return rankBoth(lo, hi, root);
    }

    /**
     * Returns the balance factor of the subtree. The balance factor is defined
     * as the difference in height of the left subtree and right subtree, in
     * this order. Therefore, a subtree with a balance factor of -1, 0 or 1 has
     * the AVL property since the heights of the two child subtrees differ by at
     * most one.
     */
    private int balanceFactor(RangeNode x) {
        return height(x.rightChild) - height(x.leftChild);
    }

    /**
     * Perform a right rotation on node `n`. Return the head of the rotated tree.
     */
    private RangeNode<Integer> rotateRight(RangeNode<Integer> x) {
        RangeNode<Integer> y = x.leftChild;
        x.leftChild = y.rightChild;
        y.rightChild = x;
        x.height = 1 + Math.max(height(x.leftChild), height(x.rightChild));
        y.height = 1 + Math.max(height(y.leftChild), height(y.rightChild));
        x.subLeft = y.subRight;
        y.subRight += 1 + x.subRight;
        return y;
    }

    /**
     * Perform a left rotation on node `n`. Return the head of the rotated tree.
     */
    private RangeNode<Integer> rotateLeft(RangeNode<Integer> x) {
        RangeNode<Integer> y = x.rightChild;
        x.rightChild = y.leftChild;
        y.leftChild = x;
        x.height = 1 + Math.max(height(x.leftChild), height(x.rightChild));
        y.height = 1 + Math.max(height(y.leftChild), height(y.rightChild));
        x.subRight = y.subLeft;
        y.subLeft += 1 + x.subLeft;
        return y;
    }
}
