public class RedBlackTree<T extends Comparable<T>> {

    /* Root of the tree. */
    RBTreeNode<T> root;

    static class RBTreeNode<T> {

        final T item;
        boolean isBlack;
        RBTreeNode<T> left;
        RBTreeNode<T> right;

        /* Creates a RBTreeNode with item ITEM and color depending on isBlack
           value. */
        RBTreeNode(boolean isBlack, T item) {
            this(isBlack, item, null, null);
        }

        /* Creates a RBTreeNode with item ITEM, color depending on isBlack
           value, left child LEFT, and right child RIGHT. */
        RBTreeNode(boolean isBlack, T item, RBTreeNode<T> left,
                   RBTreeNode<T> right) {
            this.isBlack = isBlack;
            this.item = item;
            this.left = left;
            this.right = right;
        }
    }

    /* Creates an empty RedBlackTree. */
    public RedBlackTree() {
        root = null;
    }

    /* Creates a RedBlackTree from a given 2-3 TREE. */
    public RedBlackTree(TwoThreeTree<T> tree) {
        Node<T> ttTreeRoot = tree.root;
        root = buildRedBlackTree(ttTreeRoot);
    }

    /* Builds a RedBlackTree that has isometry with given 2-3 tree rooted at
       given node R, and returns the root node. */
    RBTreeNode<T> buildRedBlackTree(Node<T> r) {
        if(r == null) {
            return null;
        }
        else if(r.getItemCount() == 1 ){
            RBTreeNode node = new RBTreeNode(true, r.getItemAt(0), null, null);
            node.left = buildRedBlackTree(r.getChildAt(0));
            node.right = buildRedBlackTree(r.getChildAt(1));
            return node;
        }
        else if(r.getItemCount() == 2){
            RBTreeNode node = new RBTreeNode(false, r.getItemAt(0), null, null);
            node.left = buildRedBlackTree(r.getChildAt(0));
            node.right = buildRedBlackTree(r.getChildAt(1));
            RBTreeNode node1 = new RBTreeNode(true, r.getItemAt(1), node, null);
            node1.right = buildRedBlackTree(r.getChildAt(2));
            return node1;
        }
        return null;
    }



    void flipColors(RBTreeNode<T> node) {
        node.isBlack = !node.isBlack;
        node.left.isBlack = !node.left.isBlack;
        node.right.isBlack = !node.right.isBlack;
    }


    RBTreeNode<T> rotateRight(RBTreeNode<T> node) {
        RBTreeNode<T> x = node.left;
        node.left = x.right;
        x.right = node;
        x.isBlack = node.isBlack;
        node.isBlack = false;
        return x;
    }


    RBTreeNode<T> rotateLeft(RBTreeNode<T> node) {
        RBTreeNode<T> x = node.right;
        node.right = x.left;
        x.left = node;
        x.isBlack = node.isBlack;
        node.isBlack = false;
        return x;
    }

    public void insert(T item) {
        root = insert(root, item);
        root.isBlack = true;
    }


    private RBTreeNode<T> insert(RBTreeNode<T> node, T item) {
        if(node == null) {
            return new RBTreeNode<>(false, item, null, null);
        }
        int comp = item.compareTo(node.item);
        if(comp == 0) {
            return node;
        }
        else if(comp < 0) {
            node.left = insert(node.left, item);
        }
        else {
            node.right = insert(node.right, item);
        }
        if (!isRed(node.left)&& isRed(node.right) ) {
            node = rotateLeft(node);
        }
        if (isRed(node.left) && isRed(node.left.left) ) {
            node = rotateRight(node);
        }
        if (isRed(node.left) && isRed(node.right)) {
            flipColors(node);
        }
        return node;
    }


    private boolean isRed(RBTreeNode<T> node) {
        return node != null && !node.isBlack;
    }
}
