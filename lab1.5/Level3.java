import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Level3 extends Level2 {
    private Random rand;

    static class RandomizedBSTNode extends BSTNode {
        int countNodes;

        RandomizedBSTNode(Student student) {
            super(student);
            this.countNodes = 1;
        }
    }

    public Level3() {
        super();
        rand = new Random();
        root = null;
        for (Student student : students) {
            addNodeRandomized(student);
            System.out.println("-----------------------------------");
        }
    }

    public void addNodeRandomized(Student student) {
        root = insertRandomized((RandomizedBSTNode)root, student);
        System.out.println("Вміст BST після додавання:");
        printBST();
    }

    private RandomizedBSTNode insertRandomized(RandomizedBSTNode current, Student student) {
        if (current == null) {
            RandomizedBSTNode newNode = new RandomizedBSTNode(student);
            System.out.println("Студент " + student.surname + " " + student.name + " доданий як корінь.");
            return newNode;
        }
        if (Math.random() * current.countNodes < 1.0) {
            current = insertRoot(current, student);
            System.out.println("Студент " + student.surname + " " + student.name + " доданий як новий корінь.");
        } else {
            String currentOperator = current.student.phoneNumber.substring(0, 3);
            String newOperator = student.phoneNumber.substring(0, 3);
            if (newOperator.compareTo(currentOperator) < 0) {
                current.left = insertRandomized((RandomizedBSTNode)current.left, student);
                if (current.left != null && current.left.student == student) {
                    System.out.println("Студент " + student.surname + " " + student.name + " доданий ліворуч.");
                }
            } else {
                current.right = insertRandomized((RandomizedBSTNode)current.right, student);
                if (current.right != null && current.right.student == student) {
                    System.out.println("Студент " + student.surname + " " + student.name + " доданий праворуч.");
                }
            }
        }
        current.countNodes++;
        return current;
    }

    private RandomizedBSTNode insertRoot(RandomizedBSTNode current, Student student) {
        if (current == null) {
            RandomizedBSTNode node = new RandomizedBSTNode(student);
            node.countNodes = 1;
            return node;
        }
        String currentOperator = current.student.phoneNumber.substring(0, 3);
        String newOperator = student.phoneNumber.substring(0, 3);

        if (newOperator.compareTo(currentOperator) < 0) {
            current.left = insertRoot((RandomizedBSTNode)current.left, student);
            current.countNodes = current.countNodes - ((RandomizedBSTNode)current.left).countNodes;
            current = (RandomizedBSTNode)rotateRight(current);
            ((RandomizedBSTNode)current.right).countNodes = count((RandomizedBSTNode)current.right);
        } else {
            current.right = insertRoot((RandomizedBSTNode)current.right, student);
            current.countNodes = current.countNodes - ((RandomizedBSTNode)current.right).countNodes;
            current = (RandomizedBSTNode)rotateLeft(current);
            ((RandomizedBSTNode)current.left).countNodes = count((RandomizedBSTNode)current.left);
        }
        current.countNodes = count(current);
        return current;
    }

    private int count(RandomizedBSTNode current) {
        if (current == null) return 0;
        if (current.left != null) {
            if (current.right != null) {
                current.countNodes = ((RandomizedBSTNode)current.left).countNodes + ((RandomizedBSTNode)current.right).countNodes;
            } else {
                current.countNodes = ((RandomizedBSTNode)current.left).countNodes;
            }
        } else if (current.right != null) {
            current.countNodes = ((RandomizedBSTNode)current.right).countNodes;
        }
        return ++current.countNodes;
    }

    @Override
    public void printBST() {
        printBSTRecursive((RandomizedBSTNode)root, 0);
    }

    private void printBSTRecursive(RandomizedBSTNode node, int level) {
        if (node == null) return;
        printBSTRecursive((RandomizedBSTNode)node.left, level + 1);
        System.out.println(node.student + " (кількість вузлів: " + node.countNodes + ")");
        printBSTRecursive((RandomizedBSTNode)node.right, level + 1);
    }

    @Override
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

    public static void main(String[] args) {
        Level3 level3 = new Level3();
        System.out.println("\nФінальний стан BST:");
        level3.printBST();

        Scanner scanner = new Scanner(System.in);
        System.out.print("\nВведіть код оператора: ");
        String operator = scanner.nextLine();

        ArrayList<BSTNode> found = level3.search(operator);
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
