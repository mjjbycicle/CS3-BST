package BST;

public class Main {
    public static void main(String[] args) {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>(5);
        for (int i = 0; i < 10; i++) {
            bst.add(
                    (int) (Math.random() * 25)
            );
        }
        System.out.println(bst);
    }
}
