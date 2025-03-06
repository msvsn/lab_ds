import java.util.ArrayList;

public class Level3 extends Level2 {
    public Level3() {
        super();
    }

    public void deleteByCriteria() {
        ArrayList<Student> toDelete = searchByCriteria();
        for (Student student : toDelete) {
            root = deleteRecursive(root, student.getStudentId());
        }
    }

    private Node deleteRecursive(Node current, int studentId) {
        if (current == null) return null;

        if (studentId < current.getStudent().getStudentId()) {
            current.setLeft(deleteRecursive(current.getLeft(), studentId));
        } else if (studentId > current.getStudent().getStudentId()) {
            current.setRight(deleteRecursive(current.getRight(), studentId));
        } else {
            if (current.getLeft() == null) return current.getRight();
            if (current.getRight() == null) return current.getLeft();

            Node minNode = findMin(current.getRight());
            current.setStudent(minNode.getStudent());
            current.setRight(deleteRecursive(current.getRight(), minNode.getStudent().getStudentId()));
        }
        return current;
    }

    private Node findMin(Node node) {
        Node current = node;
        while (current.getLeft() != null) {
            current = current.getLeft();
        }
        return current;
    }

    public static void main(String[] args) {
        Level3 tree = new Level3();

        tree.insert(new Student("Вишнівенко", 3, 325435, 95.5, "Україна"));
        tree.insert(new Student("Семенова", 2, 35644, 85.0, "Україна"));
        tree.insert(new Student("Порошенко", 3, 124322, 92.0, "Україна"));
        tree.insert(new Student("Армагедон", 4, 35646, 88.5, "Україна"));
        tree.insert(new Student("Сіренька", 3, 12334, 87.0, "Польща"));
        tree.insert(new Student("Нгуєн", 3, 53421, 91.5, "В'єтнам"));

        ArrayList<Student> allStudents = tree.breadthFirstTraversal();
        printTree(allStudents, "Повне дерево (обхід у ширину)");
        ArrayList<Student> foundStudents = tree.searchByCriteria();
        printTree(foundStudents, "Студенти, що будуть видалені");

        tree.deleteByCriteria();
        ArrayList<Student> updatedStudents = tree.breadthFirstTraversal();
        printTree(updatedStudents, "Дерево після видалення");
    }
}