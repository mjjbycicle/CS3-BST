package BST;

public class BinaryNode<T extends Comparable<T>> {
    private T data;
    private final BinaryNode<T> parent;
    private BinaryNode<T> left;
    private BinaryNode<T> right;
    private final BinarySearchTree.Direction addDirection;
    public BinaryNode(T data, BinaryNode<T> parent, BinarySearchTree.Direction addDirection) {
        this.data = data;
        this.left = null;
        this.right = null;
        this.addDirection = addDirection;
        this.parent = parent;
    }

    public T getData() {
        return data;
    }

    public BinaryNode<T> getParent() {
        return parent;
    }

    public void setData(T data) {
        this.data = data;
    }

    public BinarySearchTree.Direction getAddDirection() {
        return addDirection;
    }

    public BinaryNode<T> getLeft() {
        return left;
    }

    public BinaryNode<T> getRight() {
        return right;
    }

    public void setLeft(BinaryNode<T> left) {
        this.left = left;
    }

    public void setRight(BinaryNode<T> right) {
        this.right = right;
    }

    public int getNumChildren() {
        if (left == null) {
            if (right == null) {
                return 0;
            } else {
                return 1;
            }
        } else {
            if (right == null) {
                return 1;
            } else {
                return 2;
            }
        }
    }

    public String toString() {
        return data.toString();
    }

    public static class NullNode extends BinaryNode {
        public NullNode() {
            super(null, null, null);
        }

        @Override
        public String toString() {
            return "null";
        }
    }
}
