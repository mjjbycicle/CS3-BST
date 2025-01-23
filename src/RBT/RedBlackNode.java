package RBT;

public class RedBlackNode<T extends Comparable<T>> {
    private T data;
    private RedBlackNode<T> parent;
    private RedBlackNode<T> left;
    private RedBlackNode<T> right;
    private RedBlackTree.Color color;
    public RedBlackNode(T data, RedBlackNode<T> parent) {
        this.data = data;
        this.left = null;
        this.right = null;
        this.parent = parent;
        this.color = RedBlackTree.Color.RED;
    }

    public T getData() {
        return data;
    }

    public RedBlackTree.Color getColor() {
        return color;
    }

    public void swapColor() {
        if (this.color == RedBlackTree.Color.RED) this.color = RedBlackTree.Color.BLACK;
        else this.color = RedBlackTree.Color.RED;
    }

    public void setColor(RedBlackTree.Color color) {
        this.color = color;
    }

    public RedBlackNode<T> getParent() {
        return parent;
    }

    public void setParent(RedBlackNode<T> parent) {
        this.parent = parent;
    }

    public RedBlackTree.Direction getAddDirection() {
        if (parent.getLeft() == this) return RedBlackTree.Direction.LEFT;
        return RedBlackTree.Direction.RIGHT;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void swapData(RedBlackNode<T> other) {
        T temp = other.getData();
        other.setData(this.data);
        this.setData(temp);
    }

    public void unlinkParent() {
        if (getAddDirection() == RedBlackTree.Direction.LEFT) {
            parent.rawSetLeft(null);
        } else {
            parent.rawSetRight(null);
        }
        this.parent = null;
    }

    public RedBlackNode<T> getLeft() {
        return left;
    }

    public RedBlackNode<T> getRight() {
        return right;
    }

    public void rawSetLeft(RedBlackNode<T> left) {
        this.left = left;
    }

    public void rawSetRight(RedBlackNode<T> right) {
        this.right = right;
    }

    public void setLeft(RedBlackNode<T> left) {
        this.left = left;
        left.setParent(this);
    }

    public void setRight(RedBlackNode<T> right) {
        this.right = right;
        right.setParent(this);
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

    public static class NullNode extends RedBlackNode {
        public NullNode() {
            super(null, null);
        }

        @Override
        public RedBlackTree.Color getColor() {
            return RedBlackTree.Color.BLACK;
        }

        @Override
        public String toString() {
            return "--";
        }
    }

    public static RedBlackTree.Color getColor(RedBlackNode node) {
        if (node == null) return RedBlackTree.Color.BLACK;
        return node.getColor();
    }
}
