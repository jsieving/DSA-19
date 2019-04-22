public class TreeNode<T extends Comparable<T>> {
    T key;
    TreeNode<T> parent, leftChild, rightChild;

    TreeNode(T key) {
        this.key = key;
        this.parent = null;
        this.leftChild = null;
        this.rightChild = null;
    }

    public boolean isLeftChild() {
        if (parent != null)
            return parent.hasLeftChild() && parent.leftChild == this;
        return false;
    }

    public boolean isRightChild() {
        if (parent != null)
            return parent.hasRightChild() && parent.rightChild == this;
        return false;
    }

    public boolean hasLeftChild() {
        return this.leftChild != null;
    }

    public boolean hasRightChild() {
        return this.rightChild != null;
    }

    public boolean isRoot() {
        return this.parent == null;
    }

    public boolean isLeaf() {
        return !this.hasLeftChild() && !this.hasRightChild();
    }

    public void printTree(TreeNode<T> n, int d) {
        if (d == 0) System.out.print('\n');
        if (n != null) {
            for (int i = 0; i < d; i++) System.out.print(" _ ");
            System.out.print(n.key);
            System.out.print("\n|");
            printTree(n.leftChild, d + 1);
            printTree(n.rightChild, d + 1);
        }
    }

    public void replaceWith(TreeNode<T> n) {
        if (isLeftChild())
            parent.leftChild = n;

        if (isRightChild())
            parent.rightChild = n;

        if (n != null)
            n.parent = parent;
    }

    public void moveChildrenFrom(TreeNode<T> n) {
        leftChild = n.leftChild;
        rightChild = n.rightChild;
        if (leftChild != null)
            leftChild.parent = this;
        if (rightChild != null)
            rightChild.parent = this;
    }

    public boolean equals(TreeNode other) {
        return other.key.equals(this.key);
    }

    @Override
    public String toString() {
        String parent = this.parent == null ? "" : ", parent=" + this.parent.key.toString();
        String left = this.leftChild == null ? "" : ", left=" + this.leftChild.key.toString();
        String right = this.rightChild == null ? "" : ", right=" + this.rightChild.key.toString();
        return "TreeNode<T>{key=" + key + parent + left + right + '}';
    }
}
