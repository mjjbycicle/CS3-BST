package BST;

import java.util.ArrayList;
import java.util.List;

public class BinarySearchTree<T extends Comparable<T>> {
    private BinaryNode<T> root;

    public enum Direction {
        LEFT, RIGHT
    }

    public BinarySearchTree(T rootValue) {
        this.root = new BinaryNode<>(rootValue, null);
    }

    public void add(T value) {
        BinaryNode<T> current = root;
        while (true) {
            if (value.compareTo(current.getData()) < 0) {
                if (current.getLeft() == null) {
                    current.setLeft(new BinaryNode<>(value, Direction.LEFT));
                    break;
                } else {
                    current = current.getLeft();
                }
            } else {
                if (current.getRight() == null) {
                    current.setRight(new BinaryNode<>(value, Direction.RIGHT));
                    break;
                } else {
                    current = current.getRight();
                }
            }
        }
    }

    public BinaryNode<T> remove(T value) {
        BinaryNode<T> current = root;
        BinaryNode<T> prev = null;
        while (true) {
            if (value.compareTo(current.getData()) < 0) {
                if (current.getLeft() == null) {
                    return null;
                } else {
                    prev = current;
                    current = current.getLeft();
                }
            } else if (value.compareTo(current.getData()) > 0) {
                if (current.getRight() == null) {
                    return null;
                } else {
                    prev = current;
                    current = current.getRight();
                }
            } else {
                if (prev == null) {

                } else {
                    BinaryNode<T> temp = current;
                    recursiveRemove(current, prev);
                    return temp;
                }
            }
        }
    }

    private void recursiveRemove(BinaryNode<T> current, BinaryNode<T> prev) {
        Direction addDirection = current.getAddDirection();
        int numChildren = current.getNumChildren();
        if (numChildren == 0) {
            if (addDirection == Direction.LEFT) {
                prev.setLeft(null);
            } else {
                prev.setRight(null);
            }
        } else if (numChildren == 1) {
            if (current.getLeft() != null) {
                if (addDirection == Direction.LEFT) {
                    prev.setLeft(current.getLeft());
                } else {
                    prev.setRight(current.getLeft());
                }
            } else {
                if (addDirection == Direction.LEFT) {
                    prev.setLeft(current.getRight());
                } else {
                    prev.setRight(current.getRight());
                }
            }
        } else {
            BinaryNode<T> inOrderSuccessor = getInOrderSuccessor(current);
            current.setData(inOrderSuccessor.getData());
            recursiveRemove(inOrderSuccessor, current);
        }
    }

    private BinaryNode<T> getInOrderSuccessor(BinaryNode<T> node) {
        BinaryNode<T> current = node.getRight();
        while (current.getLeft() != null) {
            current = current.getLeft();
        }
        return current;
    }

    public List<BinaryNode<T>> levelOrderTraversal(BinaryNode<T> root) {
        List<BinaryNode<T>> result = new ArrayList<>();
        List<BinaryNode<T>> currentLevel = List.of(root);
        while (true) {
            result.addAll(currentLevel);
            List<BinaryNode<T>> newLevel = new ArrayList<>();
            boolean added = false;
            for (BinaryNode<T> child : currentLevel) {
                if (child.getLeft() != null) {
                    newLevel.add(child.getLeft());
                    added = true;
                } else {
//                    newLevel.add(new BinaryNode.NullNode());
                }
                if (child.getRight() != null) {
                    newLevel.add(child.getRight());
                    added = true;
                } else {
//                    newLevel.add(new BinaryNode.NullNode());
                }
            }
            currentLevel = newLevel;
            if (!added) {
                break;
            }
        }
        return result;
    }

    public List<BinaryNode<T>> levelOrderTraversal() {
        return levelOrderTraversal(root);
    }
}
