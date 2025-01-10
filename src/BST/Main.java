package BST;

public class Main {
    public static void main(String[] args) {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>(5);
        bst.add(5);
        bst.add(8);
        bst.add(3);
        bst.add(7);
        bst.add(1);
        bst.add(4);
        System.out.println(bst.levelOrderTraversal());
        bst.remove(3);
        System.out.println(bst.levelOrderTraversal());
    }
}
