package RBT;

public class RedBlackTreeDriver {
    public static void main(String[] args) {
        RedBlackTree<Integer> rbt = new RedBlackTree<>();
        for (String s : "723 175 180 368 581 358 753 952 268 962 194 759 515 798 266 535 468 536 245 464".split(" ")) {
            rbt.add(Integer.parseInt(s));
            System.out.println(rbt);
        }
    }
}
