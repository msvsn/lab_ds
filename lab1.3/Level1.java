import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

class Student {
    private String surname;
    private int course;
    private int studentId;
    private double gpa;
    private String citizenship;

    public Student(String surname, int course, int studentId, double gpa, String citizenship) {
        this.surname = surname;
        this.course = course;
        this.studentId = studentId;
        this.gpa = gpa;
        this.citizenship = citizenship;
    }

    public int getCourse() {
        return course;
    }

    public int getStudentId() {
        return studentId;
    }

    public double getGpa() {
        return gpa;
    }

    public String getCitizenship() {
        return citizenship;
    }

    @Override
    public String toString() {
        return String.format("%-15s | %-5d | %-10d | %-5.1f | %-15s",
                surname, course, studentId, gpa, citizenship);
    }
}

class Node {
    private Student student;
    private Node left;
    private Node right;

    public Node(Student student) {
        this.student = student;
        this.left = null;
        this.right = null;
    }

    public Student getStudent() {
        return student;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public void setRight(Node right) {
        this.right = right;
    }
}

public class Level1 {
    protected Node root;

    public Level1() {
        this.root = null;
    }

    public void insert(Student student) {
        root = insertRecursive(root, student);
    }

    protected Node insertRecursive(Node current, Student student) {
        if (current == null) {
            return new Node(student);
        }

        if (student.getStudentId() < current.getStudent().getStudentId()) {
            current.setLeft(insertRecursive(current.getLeft(), student));
        } else if (student.getStudentId() > current.getStudent().getStudentId()) {
            current.setRight(insertRecursive(current.getRight(), student));
        }

        return current;
    }

    public ArrayList<Student> breadthFirstTraversal() {
        ArrayList<Student> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Queue<Node> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            Node node = queue.poll();
            result.add(node.getStudent());

            if (node.getLeft() != null) {
                queue.add(node.getLeft());
            }

            if (node.getRight() != null) {
                queue.add(node.getRight());
            }
        }

        return result;
    }

    public static void printTree(ArrayList<Student> students, String title) {
        System.out.println("\n" + title);
        System.out.println("-".repeat(65));
        System.out.printf("%-15s | %-5s | %-10s | %-5s | %-15s%n",
                "Прізвище", "Курс", "Студ. квиток", "Ср. бал", "Громадянство");
        System.out.println("-".repeat(65));
        for (Student student : students) {
            System.out.println(student);
        }
        System.out.println("-".repeat(65));
    }

    public static void main(String[] args) {
        Level1 tree = new Level1();

        tree.insert(new Student("Вишнівенко", 3, 32543, 95.5, "Україна"));
        tree.insert(new Student("Семенова", 2, 35644, 85.0, "Україна"));
        tree.insert(new Student("Порошенко", 3, 12432, 92.0, "Україна"));
        tree.insert(new Student("Армагедон", 4, 35646, 88.5, "Україна"));
        tree.insert(new Student("Сіренька", 3, 12334, 87.0, "Польща"));
        tree.insert(new Student("Нгуєн", 3, 53421, 91.5, "В'єтнам"));

        ArrayList<Student> students = tree.breadthFirstTraversal();
        printTree(students, "Повне дерево (обхід у ширину)");
    }
}