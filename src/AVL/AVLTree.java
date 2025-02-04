package AVL;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        if (childNode == null) return;
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

    private AVLNode<T> simpleDelete(AVLNode<T> currentNode) {
        if (currentNode.getNumChildren() == 0) {
            currentNode.getParent().remove(currentNode.getAddDirection());
            return currentNode.getParent();
        } else if (currentNode.getNumChildren() == 1) {
            AVLNode<T> child = currentNode.getLeft() == null? currentNode.getRight() : currentNode.getLeft();
            currentNode.getParent().add(child);
            return currentNode.getParent();
        }
        return currentNode.getParent();
    }

    private void balance(AVLNode<T> currentNode) {
        int currentNodeBalanceFactor = AVLNode.getBalanceFactor(currentNode);
        if (currentNodeBalanceFactor > 1) {
            Direction currentNodeHeavierSize = AVLNode.getHeavierSide(currentNode);
            AVLNode<T> childNode = currentNode.get(currentNodeHeavierSize);
            Direction childNodeHeavierSide = AVLNode.getHeavierSide(childNode);
            if (currentNodeHeavierSize == Direction.LEFT) {
                if (childNodeHeavierSide == Direction.LEFT) {
                    rightRotate(currentNode);
                } else {
                    leftRotate(childNode);
                    rightRotate(currentNode);
                }
            } else {
                if (childNodeHeavierSide == Direction.RIGHT) {
                    leftRotate(currentNode);
                } else {
                    rightRotate(childNode);
                    leftRotate(currentNode);
                }
            }
        }
    }

    private void balanceFrom(AVLNode<T> currentNode) {
        balance(currentNode);
        if (currentNode == root) return;
        balanceFrom(currentNode.getParent());
    }

    private void deleteNode(AVLNode<T> node, AVLNode<T> currentNode) {
        if (currentNode.getData().equals(node.getData())) {
            if (currentNode.getNumChildren() == 2) {
                AVLNode<T> inOrderSuccessor = minValueNode(currentNode.getRight());
                AVLNode<T> inOrderSuccessorParent = inOrderSuccessor.getParent();
                currentNode.swapData(inOrderSuccessor);
                simpleDelete(inOrderSuccessor);
                balanceFrom(inOrderSuccessorParent);
            } else {
                simpleDelete(currentNode);
                balanceFrom(currentNode);
            }
        } else {
            deleteNode(node, currentNode.get(currentNode.getNewAddDirection(node)));
        }
    }

    public void delete(T value) {
        deleteNode(new AVLNode<>(value, null, null), root);
    }

    public AVLNode<T> minValueNode(AVLNode<T> node) {
        AVLNode<T> current = node;
        while (current.getLeft() != null) {
            current = current.getLeft();
        }
        return current;
    }

    public AVLNode<T> leftRotate(AVLNode<T> root) {
        AVLNode<T> rightChild = root.getRight();
        AVLNode<T> rightGrandChild = rightChild.getRight();
        AVLNode<T> T1 = root.getLeft();
        AVLNode<T> T2 = rightChild.getLeft();
        root.swapData(rightChild);
        root.addLeft(rightChild);
        rightChild.addLeft(T1);
        rightChild.addRight(T2);
        root.addRight(rightGrandChild);
        return root;
    }

    public AVLNode<T> rightRotate(AVLNode<T> root) {
        AVLNode<T> leftChild = root.getLeft();
        AVLNode<T> leftGrandChild = leftChild.getLeft();
        AVLNode<T> T1 = root.getRight();
        AVLNode<T> T2 = leftChild.getRight();
        root.swapData(leftChild);
        root.addRight(leftChild);
        leftChild.addRight(T1);
        leftChild.addLeft(T2);
        root.addLeft(leftGrandChild);
        return root;
    }

    public List<List<AVLNode<T>>> getDisplayLevels() {
        List<List<AVLNode<T>>> result = new ArrayList<>();
        List<AVLNode<T>> currentLevel = List.of(root);
        while (true) {
            result.add(currentLevel);
            List<AVLNode<T>> newLevel = new ArrayList<>();
            boolean added = false;
            for (AVLNode<T> child : currentLevel) {
                if (child.getLeft() != null) {
                    newLevel.add(child.getLeft());
                    added = true;
                } else {
                    newLevel.add(new AVLNode.NullNode());
                }
                if (child.getRight() != null) {
                    newLevel.add(child.getRight());
                    added = true;
                } else {
                    newLevel.add(new AVLNode.NullNode());
                }
            }
            currentLevel = newLevel;
            if (!added) {
                break;
            }
        }
        return result;
    }

    public String toString() {
        StringBuilder res = new StringBuilder();
        for (int ind = 0; ind <  getDisplayLevels().size(); ind++) {
            var i = getDisplayLevels().get(ind);
            res.append(i.stream().map(AVLNode::toString).collect(Collectors.joining((CharSequence) "|")));
            res.append("\n");
        }
        return res.toString();
    }
}
