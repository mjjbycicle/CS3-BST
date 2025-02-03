package AVL;

public class AVLTreeDriver {
    public static void main(String[] args) {
        AVLTree<Integer> avlTree = new AVLTree<>();
        for (var i : "718 725 541 610 320 431 862 753 327 516 493 364 388 890 792 497 531 211 195 921".split(" ")) {
            avlTree.add(Integer.parseInt(i));
            System.out.println(avlTree);
        }
        for (var i : "610 753 364 497 211".split(" ")) {
            avlTree.delete(Integer.parseInt(i));
            System.out.println(avlTree);
            for (var x : avlTree.getDisplayLevels()) {
                for (var j : x) {
                    if (j != null) {
                        System.out.println(AVLNode.getBalanceFactor(j));
                        System.out.println(j);
                    }
                }
            }
        }
    }
}
