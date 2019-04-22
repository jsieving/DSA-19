import com.sun.source.tree.Tree;

public class AVLTree<T extends Comparable<T>> extends BinarySearchTree<T> {

    void printTree(TreeNode<T> n, int d) {
        if (d == 0) System.out.print('\n');
        if (n != null) {
            for (int i = 0; i < d; i++) System.out.print(" _ ");
            System.out.print(n.key);
            System.out.print("\n|");
            printTree(n.leftChild, d + 1);
            printTree(n.rightChild, d + 1);
        }
    }

    /**
     * Delete a key from the tree rooted at the given node.
     */
    @Override
    TreeNode<T> delete(TreeNode<T> n, T key) {
        n = super.delete(n, key);
        if (n != null) {
            n.height = height(n);
            n = balance(n);
        }
        return n;
    }

    /**
     * Insert a key into the tree rooted at the given node.
     */
    @Override
    TreeNode<T> insert(TreeNode<T> n, T key) {
        n = super.insert(n, key);
        if (n != null) {
            n = balance(n);
            n.height = height(n);
        }
        return n;
    }

    /**
     * Delete the minimum descendant of the given node.
     */
    @Override
    TreeNode<T> deleteMin(TreeNode<T> n) {
        n = super.deleteMin(n);
        if (n != null) {
            n.height = 1 + Math.max(height(n.leftChild), height(n.rightChild));
            return balance(n);
        }
        return null;
    }

    // Return the height of the given node. Return -1 if null.
    private int height(TreeNode<T> n) {
        if (n == null) {
            return -1;
        }
        return 1 + Math.max(height(n.leftChild), height(n.rightChild));
    }

    public int height() {
        return Math.max(height(root), 0);
    }

    // Restores the AVL tree property of the subtree. Return the head of the new subtree
    TreeNode<T> balance(TreeNode<T> n) {
        if (balanceFactor(n) > 1) {
            if (balanceFactor(n.rightChild) < 0) {
                n.rightChild = rotateRight(n.rightChild);
            }
            n = rotateLeft(n);
        }
        if (balanceFactor(n) < -1) {
            if (balanceFactor(n.leftChild) > 0) {
                n.leftChild = rotateLeft(n.leftChild);
            }
            n = rotateRight(n);
        }
        return n;
    }

    /**
     * Returns the balance factor of the subtree. The balance factor is defined
     * as the difference in height of the left subtree and right subtree, in
     * this order. Therefore, a subtree with a balance factor of -1, 0 or 1 has
     * the AVL property since the heights of the two child subtrees differ by at
     * most one.
     */
    private int balanceFactor(TreeNode<T> n) {
        if (n == null) { return 0;}
        return height(n.rightChild) - height(n.leftChild);
    }

    /**
     * Perform a right rotation on node `n`. Return the head of the rotated tree.
     */
    private TreeNode<T> rotateRight(TreeNode<T> n) {
        if (n.hasLeftChild()) {
            TreeNode m = n.leftChild;
            TreeNode beta = m.rightChild;
            m.rightChild = n;
            n.leftChild = beta;
            n.height = height(n); // --;
            m.height = height(m); // ++;
            return m;
        }
        return n;
    }

    /**
     * Perform a left rotation on node `n`. Return the head of the rotated tree.
     */
    private TreeNode<T> rotateLeft(TreeNode<T> n) {
        if (n.hasRightChild()) {
            TreeNode m = n.rightChild;
            TreeNode beta = m.leftChild;
            m.leftChild = n;
            n.rightChild = beta;
            n.height = height(n); // --;
            m.height = height(m); // ++;
            return m;
        }
        return n;
    }
}
