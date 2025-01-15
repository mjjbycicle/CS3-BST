package BST;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class BinarySearchTreeDriver {
    public static void main(String[] args) {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        bst.add(19);
        bst.add(5);
        bst.add(27);
        bst.add(2);
        bst.add(10);
        bst.add(6);
        bst.add(12);
        bst.add(14);
        bst.add(44);
        bst.add(4);
        bst.add(17);
        bst.add(34);
        bst.add(49);
        bst.add(25);
        bst.add(40);
        bst.add(50);
        bst.add(22);
        bst.add(26);
        bst.add(38);
        bst.add(41);
        System.out.print(
                bst.getLevels()
        );
    }
}
