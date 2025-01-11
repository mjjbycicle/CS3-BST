package BST;

public class Main {
    public static void main(String[] args) {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>(15);
        bst.add(26);
        bst.add(43);
        bst.add(46);
        bst.add(39);
        bst.add(31);
        bst.add(13);
        bst.add(45);
        bst.add(27);
        bst.add(44);
        bst.add(49);
        bst.add(28);
        bst.add(1);
        bst.add(33);
        bst.add(3);
        System.out.println(bst);
        System.out.println(bst.height());
        System.out.println(bst.diameter());
    }
}
