package AVL;

@SuppressWarnings("rawtypes")
public class AVLNode<T extends Comparable<T>> {
    private T data;
    private AVLNode<T> parent;
    private AVLNode<T> left = null, right = null;
    private Direction addDirection;

    public AVLNode(T data, AVLNode<T> parent, Direction addDirection) {
        this.data = data;
        this.parent = parent;
        this.addDirection = addDirection;
    }

    public AVLNode<T> getParent() {
        return parent;
    }

    public void rawSetParent(AVLNode<T> newParent) {
        this.parent = newParent;
    }

    public AVLNode<T> getLeft() {
        return left;
    }

    public AVLNode<T> getRight() {
        return right;
    }

    public AVLNode<T> get(Direction direction) {
        return switch (direction) {
            case LEFT -> getLeft();
            case RIGHT -> getRight();
        };
    }

    public int getNumChildren() {
        int res = 0;
        res += getLeft() == null? 0 : 1;
        res += getRight() == null? 0 : 1;
        return res;
    }

    public Direction getAddDirection() {
        return addDirection;
    }

    public Direction getNewAddDirection(AVLNode<T> node) {
        if (getData().compareTo(node.getData()) > 0) {
            return Direction.LEFT;
        } else {
            return Direction.RIGHT;
        }
    }

    public void setAddDirection(Direction newDirection) {
        addDirection = newDirection;
    }

    public void addLeft(AVLNode<T> newLeft) {
        left = newLeft;
        if (newLeft != null) {
            newLeft.rawSetParent(this);
            newLeft.setAddDirection(Direction.LEFT);
        }
    }

    public void addRight(AVLNode<T> newRight) {
        right = newRight;
        if (newRight != null) {
            newRight.rawSetParent(this);
            newRight.setAddDirection(Direction.RIGHT);
        }
    }

    public void add(AVLNode<T> newNode) {
        if (this.getData().compareTo(newNode.getData()) > 0) {
            addLeft(newNode);
        } else {
            addRight(newNode);
        }
    }

    public AVLNode<T> removeLeft() {
        AVLNode<T> res = left;
        left = null;
        return res;
    }

    public AVLNode<T> removeRight() {
        AVLNode<T> res = right;
        right = null;
        return res;
    }

    public AVLNode<T> remove(Direction direction) {
        return switch (direction) {
            case LEFT -> removeLeft();
            case RIGHT -> removeRight();
        };
    }

    public T getData() {
        return data;
    }

    private void setData(T data) {
        this.data = data;
    }

    public void swapData(AVLNode<T> node) {
        T temp = node.getData();
        node.setData(data);
        setData(temp);
    }

    public static int getHeight(AVLNode node) {
        if (node == null) return 0;
        AVLNode left = node.getLeft();
        AVLNode right = node.getRight();
        int leftHeight = getHeight(left);
        int rightHeight = getHeight(right);
        return Math.max(leftHeight, rightHeight) + 1;
    }

    private static int getDirectedBalanceFactor(AVLNode node) {
        return getHeight(node.getLeft()) - getHeight(node.getRight());
    }

    public static int getBalanceFactor(AVLNode node) {
        if (node == null) return 0;
        return Math.abs(getDirectedBalanceFactor(node));
    }

    public static Direction getHeavierSide(AVLNode node) {
        return getDirectedBalanceFactor(node) < 0 ? Direction.RIGHT : Direction.LEFT;
    }

    public String toString() {
        return data + "";
    }

    public static class NullNode extends AVLNode {
        public NullNode() {
            super(null, null, null);
        }

        @Override
        public String toString() {
            return "--";
        }
    }
}
