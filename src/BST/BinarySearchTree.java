package BST;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BinarySearchTree<T extends Comparable<T>> {
    private final BinaryNode<T> root;

    public enum Direction {
        LEFT, RIGHT
    }

    public BinarySearchTree(T rootValue) {
        this.root = new BinaryNode<>(rootValue, null, null);
    }

    public int height() {
        return getLevels().size() - 1;
    }

    public int levels() {
        return getLevels().size();
    }

    public int width() {
        return getLevels().stream().map(List::size).max(Comparator.comparingInt(a -> a)).get();
    }

    public int widthAtLevel(int level) {
        return getLevels().get(level).size();
    }

    public int diameter() {
        return getLevels(root.getLeft()).size() + getLevels(root.getRight()).size() + 1;
    }

    public int numLeaves() {
        return numLeaves(root);
    }

    public int numLeaves(BinaryNode<T> node) {
        if (node == null) return 0;
        if (node.getNumChildren() == 0) return 1;
        return numLeaves(node.getLeft()) + numLeaves(node.getRight());
    }

    public int numNodes() {
        return numNodes(root);
    }

    public int numNodes(BinaryNode<T> node) {
        if (node == null) return 0;
        return 1 + numNodes(node.getLeft()) + numNodes(node.getRight());
    }

    public boolean isFull() {
        return isFull(root);
    }

    public boolean isFull(BinaryNode<T> root) {
        List<List<BinaryNode<T>>> levels = getLevels(root);
        return levels.getLast().size() == Math.pow(2, levels.size() - 1);
    }

    public BinaryNode<T> max() {
        return max(root);
    }

    public BinaryNode<T> max(BinaryNode<T> root) {
        if (root.getRight() == null) return root;
        return max(root.getRight());
    }

    public BinaryNode<T> min() {
        return min(root);
    }

    public BinaryNode<T> min(BinaryNode<T> root) {
        if (root.getLeft() == null) return root;
        return min(root.getLeft());
    }

    public void add(T value) {
        BinaryNode<T> current = root;
        while (true) {
            if (value.compareTo(current.getData()) < 0) {
                if (current.getLeft() == null) {
                    current.setLeft(new BinaryNode<>(value, current, Direction.LEFT));
                    break;
                } else {
                    current = current.getLeft();
                }
            } else {
                if (current.getRight() == null) {
                    current.setRight(new BinaryNode<>(value, current, Direction.RIGHT));
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
                BinaryNode<T> temp = new BinaryNode<>(current.getData(), null, null);
                recursiveRemove(current, prev);
                return temp;
            }
        }
    }

    private void recursiveRemove(BinaryNode<T> current, BinaryNode<T> prev) {
        Direction addDirection = current.getAddDirection();
        if (prev != null) {
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
        } else {
            BinaryNode<T> inOrderSuccessor = getInOrderSuccessor(current);
            current.setData(inOrderSuccessor.getData());
            recursiveRemove(inOrderSuccessor, inOrderSuccessor.getParent());
        }
    }

    private BinaryNode<T> getInOrderSuccessor(BinaryNode<T> node) {
        return min(node.getRight());
    }

    public List<List<BinaryNode<T>>> getLevels(BinaryNode<T> root) {
        List<List<BinaryNode<T>>> result = new ArrayList<>();
        List<BinaryNode<T>> currentLevel = List.of(root);
        while (true) {
            result.add(currentLevel);
            List<BinaryNode<T>> newLevel = new ArrayList<>();
            boolean added = false;
            for (BinaryNode<T> child : currentLevel) {
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

    public List<List<BinaryNode<T>>> getLevels() {
        return getLevels(root);
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

    public List<BinaryNode<T>> levelOrderTraversal() {
        return levelOrderTraversal(root);
    }

    public List<BinaryNode<T>> preOrderTraversal(BinaryNode<T> root) {
        List<BinaryNode<T>> res = new ArrayList<>(List.of(root));
        if (root.getLeft() != null) {
            res.addAll(preOrderTraversal(root.getLeft()));
        }
        if (root.getRight() != null) {
            res.addAll(preOrderTraversal(root.getRight()));
        }
        return res;
    }

    public List<BinaryNode<T>> preOrderTraversal() {
        return preOrderTraversal(root);
    }

    public List<BinaryNode<T>> postOrderTraversal(BinaryNode<T> root) {
        List<BinaryNode<T>> res = new ArrayList<>();
        if (root.getLeft() != null) {
            res.addAll(postOrderTraversal(root.getLeft()));
        }
        if (root.getRight() != null) {
            res.addAll(postOrderTraversal(root.getRight()));
        }
        res.add(root);
        return res;
    }

    public List<BinaryNode<T>> postOrderTraversal() {
        return postOrderTraversal(root);
    }

    public List<BinaryNode<T>> inOrderTraversal(BinaryNode<T> root) {
        List<BinaryNode<T>> res = new ArrayList<>();
        if (root.getLeft() != null) {
            res.addAll(postOrderTraversal(root.getLeft()));
        }
        res.add(root);
        if (root.getRight() != null) {
            res.addAll(postOrderTraversal(root.getRight()));
        }
        return res;
    }

    public List<BinaryNode<T>> inOrderTraversal() {
        return postOrderTraversal(root);
    }

    public List<BinaryNode<T>> reverseOrderTraversal(BinaryNode<T> root) {
        List<BinaryNode<T>> res = inOrderTraversal(root);
        Collections.reverse(res);
        return res;
    }

    public List<BinaryNode<T>> reverseOrderTraversal() {
        return reverseOrderTraversal(root);
    }

    public List<List<BinaryNode<T>>> getDisplayLevels() {
        List<List<BinaryNode<T>>> result = new ArrayList<>();
        List<BinaryNode<T>> currentLevel = List.of(root);
        while (true) {
            result.add(currentLevel);
            List<BinaryNode<T>> newLevel = new ArrayList<>();
            boolean added = false;
            for (BinaryNode<T> child : currentLevel) {
                if (child.getLeft() != null) {
                    newLevel.add(child.getLeft());
                    added = true;
                } else {
                    newLevel.add(new BinaryNode.NullNode());
                }
                if (child.getRight() != null) {
                    newLevel.add(child.getRight());
                    added = true;
                } else {
                    newLevel.add(new BinaryNode.NullNode());
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
        for (var i : getDisplayLevels()) {
            res.append(i.toString()).append("\n");
        }
        return res.toString();
    }
}
