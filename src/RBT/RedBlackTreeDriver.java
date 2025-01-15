package RBT;

public class RedBlackTreeDriver {
    public static void main(String[] args) {
        RedBlackTree<Integer> rbt = new RedBlackTree<>();
        rbt.add(0);
        rbt.add(1);
        rbt.add(2);
        System.out.println(rbt.getLevels());
    }
}
