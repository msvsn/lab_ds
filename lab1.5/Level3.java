import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Level3 extends Level2 {
    private Random rand;

    public Level3() {
        super();
        rand = new Random();
        root = null;
        for (Student student : students) {
            addNodeRandomized(student);
        }
    }

    public void addNodeRandomized(Student student) {
        BSTNode newNode = new BSTNode(student);
        if (root == null) {
            root = newNode;
            System.out.println("Студент " + student.surname + " " + student.name + " доданий як корінь.");
        } else {
            if (rand.nextBoolean()) {
                root = rotateLeft(root);
                System.out.println("Виконана ротація вліво перед додаванням.");
            } else {
                root = rotateRight(root);
                System.out.println("Виконана ротація вправо перед додаванням.");
            }
            BSTNode current = root;
            String direction = "";
            while (true) {
                String currentOperator = current.student.phoneNumber.substring(0, 3);
                String newOperator = student.phoneNumber.substring(0, 3);
                if (newOperator.compareTo(currentOperator) < 0) {
                    if (current.left == null) {
                        current.left = newNode;
                        direction = "ліворуч";
                        break;
                    }
                    current = current.left;
                } else {
                    if (current.right == null) {
                        current.right = newNode;
                        direction = "праворуч";
                        break;
                    }
                    current = current.right;
                }
            }
            System.out.println("Студент " + student.surname + " " + student.name + " доданий " + direction + ".");
        }
        System.out.println("Вміст BST після додавання:");
        printBST();
        System.out.println("-----------------------------------");
    }

    public static void main(String[] args) {
        Level3 level3 = new Level3();

        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть код оператора: ");
        String operator = scanner.nextLine();

        ArrayList<BSTNode> found = level3.search(operator);
        if (!found.isEmpty()) {
            System.out.println("Знайдені студенти з оператором " + operator + ":");
            for (BSTNode node : found) {
                System.out.println(node.student);
            }
        } else {
            System.out.println("Вузол з оператором " + operator + " не знайдено.");
        }
    }
}
