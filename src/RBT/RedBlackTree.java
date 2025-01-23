package RBT;

import BST.BinaryNode;

import java.util.ArrayList;
import java.util.List;

import static RBT.RedBlackNode.getColor;

public class RedBlackTree<T extends Comparable<T>> {
    public enum Direction {
        LEFT,
        RIGHT
    }

    public enum Color {
        RED,
        BLACK
    }

    private RedBlackNode<T> root;

    private RedBlackNode<T> find(T target) {
        RedBlackNode<T> current = root;
        while (true) {
            if (current.getData().equals(target)) return current;
            if (target.compareTo(current.getData()) < 0) {
                if (current.getLeft() == null) return null;
                current = current.getLeft();
            } else if (target.compareTo(current.getData()) > 0) {
                if (current.getRight() == null) return null;
                current = current.getRight();
            }
        }
    }

    public void add(T value) {
        if (root == null) {
            root = new RedBlackNode<>(value, null);
        } else {
            RedBlackNode<T> current = root;
            while (true) {
                if (current.getNumChildren() == 2) {
                    if (current.getLeft().getColor() == Color.RED
                            && current.getRight().getColor() == Color.RED) {
                        current.swapColor();
                        current.getLeft().swapColor();
                        current.getRight().swapColor();
                    }
                }
                if (current.getNumChildren() == 0
                        || (current.getData().compareTo(value) > 0 && current.getLeft() == null)
                        || (current.getData().compareTo(value) < 0 && current.getRight() == null)) {
                    RedBlackNode<T> newNode = new RedBlackNode<>(value, current);
                    if (current.getData().compareTo(value) > 0) {
                        current.setLeft(newNode);
                    } else {
                        current.setRight(newNode);
                    }
                    checkRotations(newNode);
                    break;
                } else {
                    checkRotations(current);
                    if (current.getData().compareTo(value) > 0) {
                        current = current.getLeft();
                    } else {
                        current = current.getRight();
                    }
                }
                if (root.getColor() == Color.RED) root.swapColor();
            }
        }
    }

    private void checkRotations(RedBlackNode<T> node) {
        if (node == root) return;
        if (node.getParent() == root) return;
        if (node.getColor() == Color.RED && node.getParent().getColor() == Color.RED) {
            Direction parentAddDirection = node.getParent().getAddDirection();
            Direction nodeAddDirection = node.getAddDirection();
            if (parentAddDirection == Direction.LEFT) {
                if (nodeAddDirection == Direction.LEFT) {
                    leftLeftRotation(node);
                } else {
                    leftRightRotation(node);
                }
            } else {
                if (nodeAddDirection == Direction.LEFT) {
                    rightLeftRotation(node);
                } else {
                    rightRightRotation(node);
                }
            }
        }
    }

    public void leftLeftRotation(RedBlackNode<T> node) {
        RedBlackNode<T> parent = node.getParent();
        RedBlackNode<T> grandParent = parent.getParent();
        RedBlackNode<T> uncle = grandParent.getRight();
        RedBlackNode<T> sibling = parent.getRight();
        parent.swapData(grandParent);
        parent.unlinkParent();
        if (uncle != null)
            uncle.unlinkParent();
        if (sibling != null)
            sibling.unlinkParent();
        node.unlinkParent();
        grandParent.setRight(parent);
        if (uncle != null)
            parent.setRight(uncle);
        if (sibling != null)
            parent.setLeft(sibling);
        grandParent.setLeft(node);
    }

    public void rightRightRotation(RedBlackNode<T> node) {
        RedBlackNode<T> parent = node.getParent();
        RedBlackNode<T> grandParent = parent.getParent();
        RedBlackNode<T> uncle = grandParent.getLeft();
        RedBlackNode<T> sibling = parent.getLeft();
        parent.swapData(grandParent);
        parent.unlinkParent();
        if (uncle != null)
            uncle.unlinkParent();
        if (sibling != null)
            sibling.unlinkParent();
        node.unlinkParent();
        parent.rawSetRight(null);
        grandParent.setLeft(parent);
        if (uncle != null)
            parent.setLeft(uncle);
        if (sibling != null)
            parent.setRight(sibling);
        grandParent.setRight(node);
    }

    public void leftRightRotation(RedBlackNode<T> node) {
        RedBlackNode<T> parent = node.getParent();
        RedBlackNode<T> grandParent = parent.getParent();
        RedBlackNode<T> leftChild = node.getLeft();
        parent.unlinkParent();
        node.unlinkParent();
        parent.rawSetLeft(null);
        if (leftChild != null)
            leftChild.unlinkParent();
        grandParent.setLeft(node);
        node.setLeft(parent);
        if (leftChild != null)
            parent.setRight(leftChild);
        node.setColor(Color.RED);
        parent.setColor(Color.RED);
        grandParent.setColor(Color.BLACK);
        leftLeftRotation(parent);
    }

    public void rightLeftRotation(RedBlackNode<T> node) {
        RedBlackNode<T> parent = node.getParent();
        RedBlackNode<T> grandParent = parent.getParent();
        RedBlackNode<T> leftChild = node.getRight();
        parent.unlinkParent();
        node.unlinkParent();
        if (leftChild != null)
            leftChild.unlinkParent();
        grandParent.setRight(node);
        node.setRight(parent);
        if (leftChild != null)
            parent.setLeft(leftChild);
        node.setColor(Color.RED);
        parent.setColor(Color.RED);
        grandParent.setColor(Color.BLACK);
        rightRightRotation(parent);
    }

    public void delete(T target) {
        RedBlackNode<T> targetNode = find(target);
        delete(targetNode);
    }

    public void delete(RedBlackNode<T> targetNode) {
        if (targetNode == null) return;
        if (targetNode.getNumChildren() == 0) {
            Color targetNodeColor = getColor(targetNode);
            if (targetNode.getAddDirection() == Direction.LEFT) {
                targetNode.getParent().rawSetLeft(null);
            } else {
                targetNode.getParent().rawSetRight(null);
            }
            if (targetNodeColor == Color.BLACK) {

            }
        } else if (targetNode.getNumChildren() == 1) {
            RedBlackNode<T> targetChild = targetNode.getLeft() == null? targetNode.getRight() : targetNode.getLeft();
            if (getColor(targetNode) == Color.RED || getColor(targetChild) == Color.RED) {
                simpleDelete(targetNode);
            } else {

            }
        } else {
            RedBlackNode<T> inOrderSuccessor = getInOrderSuccessor(targetNode);
            targetNode.swapData(inOrderSuccessor);
            targetNode.swapColor();
            simpleDelete(inOrderSuccessor);
        }
    }

    private RedBlackNode<T> getInOrderSuccessor(RedBlackNode<T> node) {
        RedBlackNode<T> current = node.getRight();
        while (true) {
            if (current.getLeft() == null) return current;
            current = current.getLeft();
        }
    }

    private void fixDoubleBlack()

    private void simpleDelete(RedBlackNode<T> targetNode) {
        RedBlackNode<T> targetChild = targetNode.getLeft() == null? targetNode.getRight() : targetNode.getLeft();
        if (targetChild.getColor() == Color.RED) {
            targetChild.swapColor();
        }
        if (targetNode.getAddDirection() == Direction.LEFT) {
            targetNode.getParent().setLeft(targetChild);
        } else {
            targetNode.getParent().setRight(targetChild);
        }
    }

    public List<List<RedBlackNode<T>>> getLevels(RedBlackNode<T> root) {
        List<List<RedBlackNode<T>>> result = new ArrayList<>();
        List<RedBlackNode<T>> currentLevel = List.of(root);
        while (true) {
            result.add(currentLevel);
            List<RedBlackNode<T>> newLevel = new ArrayList<>();
            boolean added = false;
            for (RedBlackNode<T> child : currentLevel) {
                if (child.getLeft() != null) {
                    newLevel.add(child.getLeft());
                    added = true;
                }
                if (child.getRight() != null) {
                    newLevel.add(child.getRight());
                    added = true;
                }
            }
            currentLevel = newLevel;
            if (!added) {
                break;
            }
        }
        return result;
    }

    public List<List<RedBlackNode<T>>> getDisplayLevels() {
        List<List<RedBlackNode<T>>> result = new ArrayList<>();
        List<RedBlackNode<T>> currentLevel = List.of(root);
        while (true) {
            result.add(currentLevel);
            List<RedBlackNode<T>> newLevel = new ArrayList<>();
            boolean added = false;
            for (RedBlackNode<T> child : currentLevel) {
                if (child.getLeft() != null) {
                    newLevel.add(child.getLeft());
                    added = true;
                } else {
                    newLevel.add(new RedBlackNode.NullNode());
                }
                if (child.getRight() != null) {
                    newLevel.add(child.getRight());
                    added = true;
                } else {
                    newLevel.add(new RedBlackNode.NullNode());
                }
            }
            currentLevel = newLevel;
            if (!added) {
                break;
            }
        }
        return result;
    }

    public List<List<RedBlackNode<T>>> getLevels() {
        return getLevels(root);
    }

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";

    public String toString() {
        List<List<RedBlackNode<T>>> levels = getDisplayLevels();
        StringBuilder sb = new StringBuilder();
        for (var level : levels) {
            for (RedBlackNode<T> node : level) {
                sb.append(
                        node.getColor() == Color.BLACK? ANSI_BLACK : ANSI_RED
                );
                sb.append(node.toString());
                sb.append(ANSI_RESET);
                sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

}
