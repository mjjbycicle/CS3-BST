package BST;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class BinarySearchTreeDriver {
    public static void main(String[] args) {
        BinarySearchTree<String> bst = new BinarySearchTree<>();
        Scanner sc = new Scanner(System.in);
        int lines = sc.nextInt();
        sc.nextLine();
        List<String> words = new ArrayList<>();
        for (int i = 0; i < lines; i++) {
            words.addAll(
                    Arrays.stream(sc.nextLine().split(" ")).toList()
            );
        }
        for (String word : words) {
            bst.add(word);
        }

        System.out.println("Proper Tree Display");
        System.out.print(bst);

        System.out.print("Tree-->");
        System.out.println(
                bst.inOrderTraversal().stream().map(BinaryNode::toString).collect(Collectors.joining((CharSequence) " "))
        );

        System.out.println("PRE ORDER");
        System.out.println(
                bst.preOrderTraversal().stream().map(BinaryNode::toString).collect(Collectors.joining((CharSequence) " "))
        );
        System.out.println("POST ORDER");
        System.out.println(
                bst.postOrderTraversal().stream().map(BinaryNode::toString).collect(Collectors.joining((CharSequence) " "))
        );
        System.out.println("IN ORDER");
        System.out.println(
                bst.inOrderTraversal().stream().map(BinaryNode::toString).collect(Collectors.joining((CharSequence) " "))
        );
        System.out.println("REVERSE ORDER");
        System.out.println(
                bst.reverseOrderTraversal().stream().map(BinaryNode::toString).collect(Collectors.joining((CharSequence) " "))
        );
        System.out.println("LEVEL ORDER");
        System.out.println(
                bst.levelOrderTraversal()
        );

        System.out.println("Number of leaves: " + bst.numLeaves());
        System.out.println("Number of levels: " + bst.levels());
        System.out.println("The Tree width: " + bst.width());
        System.out.println("The Tree height: " + bst.height());
        System.out.println("The Tree diameter: " + bst.diameter());
        System.out.println("Number of nodes: " + bst.numNodes());

        for (int i = 0; i < bst.levels(); i++) {
            System.out.println(
                    "width at level " + i + " " + bst.getLevels().get(i).size()
            );
        }

        System.out.println(
                "Tree is"
                + (bst.isFull()? " " : " not ")
                + "full"
        );

        int contains = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < contains; i++) {
            String word = sc.nextLine();
            System.out.println(
                    "Tree"
                    + (bst.contains(word)? " contains " : " does not contain ")
                    + word
            );
        }

        System.out.println(
                "Largest value: "
                + bst.max().getData()
        );
        System.out.println(
                "Smallest value: "
                + bst.min().getData()
        );

        int remove = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < remove; i++) {
            bst.remove(sc.nextLine());
        }

        System.out.println("Proper Tree Display");
        System.out.print(bst);
    }
}
