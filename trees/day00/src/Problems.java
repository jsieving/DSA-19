import java.util.*;

public class Problems {

    public static BinarySearchTree<Integer> minimalHeight(List<Integer> values) {
        // TODO
        return new BinarySearchTree<>();
    }

    public static boolean isIsomorphic(TreeNode n1, TreeNode n2) {
        organize(n1);
        organize(n2);
        List<Integer> n1Sorted = new ArrayList<>();
        List<Integer> n2Sorted = new ArrayList<>();
        traverse(n1, n1Sorted);
        traverse(n2, n2Sorted);
        return n1Sorted.equals(n2Sorted);
    }

    public static void traverse(TreeNode n, List l) {
        if (n != null) {
            traverse(n.leftChild, l);
            l.add(n.key);
            traverse(n.rightChild, l);
        }
    }

    public static void organize(TreeNode n) {
        if (n.isLeaf()) {}
        else if (!n.hasLeftChild()) {
            n.leftChild = n.rightChild;
            n.rightChild = null;
            organize(n.leftChild);
        }
        else if (!n.hasRightChild()) {
            organize(n.leftChild);
        }
        else {
            if ((int) n.rightChild.key < (int) n.leftChild.key) {
                TreeNode temp = n.leftChild;
                n.leftChild = n.rightChild;
                n.rightChild = temp;
            }
            organize(n.leftChild);
            organize(n.rightChild);
        }
    }
}
