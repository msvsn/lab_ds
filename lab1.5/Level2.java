import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.ArrayList;

public class Level2 extends Level1 {
    static class BSTNode {
        Student student;
        BSTNode left, right;

        BSTNode(Student student) {
            this.student = student;
            this.left = null;
            this.right = null;
        }
    }

    protected BSTNode root;

    public Level2() {
        super();
        root = null;
        for (Student student : students) {
            addNode(student);
        }
    }

    public void addNode(Student student) {
        BSTNode newNode = new BSTNode(student);
        if (root == null) {
            root = newNode;
        } else {
            BSTNode current = root;
            while (true) {
                String currentOperator = current.student.phoneNumber.substring(0, 3);
                String newOperator = student.phoneNumber.substring(0, 3);
                if (newOperator.compareTo(currentOperator) < 0) {
                    if (current.left == null) {
                        current.left = newNode;
                        break;
                    }
                    current = current.left;
                } else {
                    if (current.right == null) {
                        current.right = newNode;
                        break;
                    }
                    current = current.right;
                }
            }
            root = rotateLeft(root);
        }
    }

    BSTNode rotateLeft(BSTNode node) {
        if (node == null || node.right == null) return node;
        BSTNode newRoot = node.right;
        node.right = newRoot.left;
        newRoot.left = node;
        return newRoot;
    }

    BSTNode rotateRight(BSTNode node) {
        if (node == null || node.left == null) return node;
        BSTNode newRoot = node.left;
        node.left = newRoot.right;
        newRoot.right = node;
        return newRoot;
    }

    public ArrayList<BSTNode> search(String operator) {
        ArrayList<BSTNode> result = new ArrayList<>();
        searchAll(root, operator, result);
        return result;
    }

    private void searchAll(BSTNode node, String operator, ArrayList<BSTNode> result) {
        if (node == null) return;

        String currentOperator = node.student.phoneNumber.substring(0, 3);
        int comparison = operator.compareTo(currentOperator);

        if (comparison == 0) {
            result.add(node);
            searchAll(node.left, operator, result);
            searchAll(node.right, operator, result);
        } else if (comparison < 0) {
            searchAll(node.left, operator, result);
        } else {
            searchAll(node.right, operator, result);
        }
    }

    public void printBST() {
        if (root == null) return;
        Queue<BSTNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            BSTNode node = queue.poll();
            System.out.println(node.student);
            if (node.left != null) queue.add(node.left);
            if (node.right != null) queue.add(node.right);
        }
    }

    public static void main(String[] args) {
        Level2 level2 = new Level2();
        System.out.println("Вміст BST:");
        level2.printBST();

        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть код оператора: ");
        String operator = scanner.nextLine();

        ArrayList<BSTNode> found = level2.search(operator);
        if (!found.isEmpty()) {
            System.out.println("Знайдені студенти з оператором " + operator + ":");
            for (BSTNode node : found) {
                System.out.println(node.student);
            }
        } else {
            System.out.println("Студентів з оператором " + operator + " не знайдено.");
        }
    }
}
