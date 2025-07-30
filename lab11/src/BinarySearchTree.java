public class BinarySearchTree<T extends Comparable<T>> extends BinaryTree<T> {

    /* Creates an empty BST. Super() calls the constructor for BinaryTree (not in scope). */
    public BinarySearchTree() {
        super();
    }

    /* Creates a BST with root as ROOT. */
    public BinarySearchTree(TreeNode<T> root) {
        super(root);
    }

    /* Returns true if the BST contains the given KEY. */
    public boolean contains(T key) {
        return containsHelper(key,root);
    }
    private boolean containsHelper(T key,TreeNode<T> node) {
        if(node == null) {
            return false;
        }
        else if(key.compareTo((T) node.item)<0) {
            return containsHelper(key,node.left);
        }
        else if(key.compareTo((T) node.item)>0){
            return containsHelper(key,node.right);
        } else if (key.compareTo((T) node.item) == 0) {
            return true;
        }
        else{
            return true;
        }
    }

    /* Adds a node for KEY iff KEY isn't in the BST already. */
    public void add(T key) {
        root = addHelper(key,root);
    }
    private TreeNode<T> addHelper(T key,TreeNode <T>node) {
        if (node == null) {
            return new TreeNode<>(key);
        }
        if(key.compareTo((T) node.item)<0) {
            node.left = addHelper(key,node.left);
        }
        else if(key.compareTo((T) node.item)>0) {
            node.right = addHelper(key,node.right);
        }
    return node;
    }

    /* Deletes a node from the BST. 
     * Even though you do not have to implement delete, you 
     * should read through and understand the basic steps.
    */
    public T delete(T key) {
        TreeNode<T> parent = null;
        TreeNode<T> curr = root;
        TreeNode<T> delNode = null;
        TreeNode<T> replacement = null;
        boolean rightSide = false;

        while (curr != null && !curr.item.equals(key)) {
            if (curr.item.compareTo(key) > 0) {
                parent = curr;
                curr = curr.left;
                rightSide = false;
            } else {
                parent = curr;
                curr = curr.right;
                rightSide = true;
            }
        }
        delNode = curr;
        if (curr == null) {
            return null;
        }

        if (delNode.right == null) {
            if (root == delNode) {
                root = root.left;
            } else {
                if (rightSide) {
                    parent.right = delNode.left;
                } else {
                    parent.left = delNode.left;
                }
            }
        } else {
            curr = delNode.right;
            replacement = curr.left;
            if (replacement == null) {
                replacement = curr;
            } else {
                while (replacement.left != null) {
                    curr = replacement;
                    replacement = replacement.left;
                }
                curr.left = replacement.right;
                replacement.right = delNode.right;
            }
            replacement.left = delNode.left;
            if (root == delNode) {
                root = replacement;
            } else {
                if (rightSide) {
                    parent.right = replacement;
                } else {
                    parent.left = replacement;
                }
            }
        }
        return delNode.item;
    }
}
