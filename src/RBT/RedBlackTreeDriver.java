package RBT;

public class RedBlackTreeDriver {
    public static void main(String[] args) {
        RedBlackTree<Integer> rbt = new RedBlackTree<>();
        for (int i = 0; i < 50; i++) {
            rbt.add(
                    (int) (Math.random() * 100)
            );
            System.out.println(rbt);
        }
    }
}
