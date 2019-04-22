import java.util.NoSuchElementException;


public class RedBlackTree<T extends Comparable<T>> extends BinarySearchTree<T> {

    public static final boolean RED = true;
    public static final boolean BLACK = false;

    private boolean isRed(TreeNode x) {
        return x != null && x.color == RED;
    }

    private boolean isBlack(TreeNode x) {
        return x != null && x.color == BLACK;
    }

    // ====================================
    //            Insertion Code
    // ====================================


    public boolean add(T key) {
        super.add(key);
        root.color = BLACK;
        return true;
    }

    void printTree(TreeNode<T> n, int d, char s) {
        if (d == 0) System.out.print('\n');
        if (n != null) {
            System.out.printf("\n%c", s);
            for (int i = 0; i < d; i++) {
                if (n.color == BLACK) {
                    System.out.print(" _ ");
                } else {
                    System.out.print(" . ");
                }
            }
            System.out.print(n.key);
            printTree(n.leftChild, d + 1, 'L');
            printTree(n.rightChild, d + 1, 'R');
        }
    }

    //For the rotates, don't forget to reassign colors. If you are unclear about
    //how to do this, you can try drawing ou examples and make sure you
    //maintain the requirements of a LLRB:
    //-All leaves have the same black distance
    //-No right red nodes
    //-No 2 red nodes in a row

    // make a left-leaning link lean to the right
    TreeNode<T> rotateRight(TreeNode<T> n) {
        if (n.leftChild != null) {
            TreeNode<T> m = n.leftChild;
            TreeNode<T> beta = m.rightChild;
            m.rightChild = n;
            n.leftChild = beta;
            boolean temp = n.color;
            n.color = m.color;
            m.color = temp;
            return m;
        }
        return n;
    }

    // make a right-leaning link lean to the left
    TreeNode<T> rotateLeft(TreeNode<T> n) {
        if (n.rightChild != null) {
            TreeNode<T> m = n.rightChild;
            TreeNode<T> beta = m.leftChild;
            m.leftChild = n;
            n.rightChild = beta;
            boolean temp = n.color;
            n.color = m.color;
            m.color = temp;
            return m;
        }
        return n;
    }

    // flip the colors of a TreeNode and its two children
    TreeNode<T> flipColors(TreeNode<T> h) {
        h.color = !h.color;
        h.rightChild.color = !h.rightChild.color;
        h.leftChild.color = !h.leftChild.color;
        return h;
    }


    /**
     * fix three cases:
     *   1. h.right is red
     *   2. h.left is red, and h.left.left is red
     *   2. h.left and h.right are red
     * return balanced node
     */
    private TreeNode<T> balance(TreeNode<T> h) {
        if (isRed(h.rightChild)) {
            h = rotateLeft(h);
        }
        if (isRed(h.leftChild) && isRed(h.leftChild.leftChild)) {
            h = rotateRight(h);
        }
        if (isRed(h.leftChild) && isRed(h.rightChild)) {
            flipColors(h);
        }
        return h;
    }


    /**
     * Recursively insert a new node into the BST
     * Runtime: O(n)
     */
    @Override
    TreeNode<T> insert(TreeNode<T> h, T key) {
        h = super.insert(h, key);
        h = balance(h);
        return h;
    }


    // ====================================
    //            Deletion Code
    // ====================================


    /**
     * Removes the specified key from the tree
     * (if the key is in this tree).
     *
     * @param key the key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public boolean delete(T key) {
        if (key == null) throw new IllegalArgumentException("argument to delete() is null");
        if (!contains(key)) return false;

        // if both children of root are black, set root to red
        if (!isRed(root.leftChild) && !isRed(root.rightChild))
            root.color = RED;

        root = delete(root, key);
        size--;
        if (!isEmpty()) root.color = BLACK;
        return true;
    }


    // the smallest key in subtree rooted at x; null if no such key
    private TreeNode<T> min(TreeNode<T> x) {
        if (x.leftChild == null) return x;
        else return min(x.leftChild);
    }

    // delete the key-value pair with the minimum key rooted at h
    TreeNode<T> deleteMin(TreeNode<T> h) {
        // OPTIONAL TODO: write this function and use it in delete(h, key)
        if (h.leftChild == null) {
            return null;
        }
        if(!isRed(h.leftChild) && !isRed(h.leftChild.leftChild)) {
            flipColors(h);
            if (isRed(h.rightChild.leftChild)) {
                h.rightChild = rotateRight(h.rightChild);
                h = rotateLeft(h);
                flipColors(h);
            }
        }
        h.leftChild = deleteMin(h.leftChild);
        return balance(h);
    }
    // delete the key-value pair with the given key rooted at h
    TreeNode<T> delete(TreeNode<T> h, T key) {
        // OPTIONAL TODO
        h = super.delete(h, key);
//        h = balance(h);
//        root.color = BLACK;
        return h;
    }

    // ====================================
    //          LLRB Verification
    // ====================================


    public boolean is23() {
        return is23(root);
    }

    // return true if this LLRB is a valid 2-3 tree
    private boolean is23(TreeNode<T> n) {
        if (n == null) return true;
        if (isRed(n.rightChild)) return false;
        if (isRed(n.leftChild) && isRed(n.leftChild.leftChild)) return false;
        return is23(n.rightChild) && is23(n.leftChild);
    }
    // Ensures that node has at most 1 red link on the left side. The maximum number
    // of children for a node with no red links is 2, and the maximum number of
    // children for a pair of nodes with a red link is 3 (since 1 spot on the higher
    // node is occupied.

    public boolean isBalanced() {
        return isBalanced(root) != -1;
    }

    // return -1 if the tree is not balanced. Otherwise, return the black-height of the tree
    private int isBalanced(TreeNode<T> n) {
        if (n == null) return 0;
        int lBalanced = isBalanced(n.leftChild); // first, count black/check balance below n
        int rBalanced = isBalanced(n.rightChild);
        if (lBalanced == -1 || rBalanced == -1) return -1; // if either was not balanced, fail
        if (isBlack(n.leftChild)) lBalanced++; // increase black count if appropriate
        if (isBlack(n.rightChild)) rBalanced++;
        if (lBalanced != rBalanced) return -1; // check that they are balanced
        // calling this on a node with left-red and right-black will work because
        // the left child will have to have a taller tree (higher black-count initially)
        // and that will get made up for when the right black is added.
        return lBalanced;
    }

}
