package AVL;

public class AVLTree<T extends Comparable<T>> {
    private AVLNode<T> root;

    public void add(T value) {
        AVLNode<T> newNode = new AVLNode<>(value, null, null);
        if (root == null) {
            root = newNode;
        } else {
            add(newNode, root);
        }
    }

    public void add(AVLNode<T> node, AVLNode<T> currentNode) {
        Direction addDirection = currentNode.getNewAddDirection(node);
        if (currentNode.get(addDirection) == null) {
            currentNode.add(node);
            return;
        } else {
            add(node, currentNode.get(addDirection));
        }
        int currentNodeBalanceFactor = AVLNode.getBalanceFactor(currentNode);
        Direction currentNodeHeavierSize = AVLNode.getHeavierSide(currentNode);
        AVLNode<T> childNode = currentNode.get(currentNodeHeavierSize);
        Direction childNodeAddDirection = childNode.getNewAddDirection(node);
        if (currentNodeBalanceFactor > 1) {
            if (currentNodeHeavierSize == Direction.LEFT) {
                if (childNodeAddDirection == Direction.LEFT) {
                    rightRotate(currentNode);
                } else {
                    leftRotate(childNode);
                    rightRotate(currentNode);
                }
            } else {
                if (childNodeAddDirection == Direction.RIGHT) {
                    leftRotate(currentNode);
                } else {
                    rightRotate(childNode);
                    leftRotate(currentNode);
                }
            }
        }
    }

    public void leftRotate(AVLNode<T> root) {
        AVLNode<T> rightChild = root.getRight();
        AVLNode<T> rightGrandChild = rightChild.getRight();
        AVLNode<T> T1 = root.getLeft();
        AVLNode<T> T2 = rightChild.getLeft();
        root.swapData(rightChild);
        root.addLeft(rightChild);
        rightChild.addLeft(T1);
        rightChild.addRight(T2);
        root.addRight(rightGrandChild);
    }

    public void rightRotate(AVLNode<T> root) {
        AVLNode<T> leftChild = root.getLeft();
        AVLNode<T> leftGrandChild = leftChild.getLeft();
        AVLNode<T> T1 = root.getRight();
        AVLNode<T> T2 = leftChild.getRight();
        root.swapData(leftChild);
        root.addRight(leftChild);
        leftChild.addRight(T1);
        leftChild.addLeft(T2);
        root.addLeft(leftGrandChild);
    }
}
