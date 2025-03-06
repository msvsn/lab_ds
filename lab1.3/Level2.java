import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Level2 extends Level1 {
    public Level2() {
        super();
    }

    public ArrayList<Student> searchByCriteria() {
        ArrayList<Student> result = new ArrayList<>();
        if (root == null) return result;

        Queue<Node> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            Student s = current.getStudent();
            if (s.getCourse() == 3 && s.getGpa() >= 90 && s.getCitizenship().equalsIgnoreCase("Україна")) {
                result.add(s);
            }
            if (current.getLeft() != null) queue.add(current.getLeft());
            if (current.getRight() != null) queue.add(current.getRight());
        }
        return result;
    }

    public static void main(String[] args) {
        Level2 tree = new Level2();

        tree.insert(new Student("Вишнівенко", 3, 325435, 95.5, "Україна"));
        tree.insert(new Student("Семенова", 2, 35644, 85.0, "Україна"));
        tree.insert(new Student("Порошенко", 3, 124322, 92.0, "Україна"));
        tree.insert(new Student("Армагедон", 4, 35646, 88.5, "Україна"));
        tree.insert(new Student("Сіренька", 3, 12334, 87.0, "Польща"));
        tree.insert(new Student("Нгуєн", 3, 53421, 91.5, "В'єтнам"));

        ArrayList<Student> allStudents = tree.breadthFirstTraversal();
        printTree(allStudents, "Повне дерево (обхід у ширину)");

        ArrayList<Student> foundStudents = tree.searchByCriteria();
        if (!foundStudents.isEmpty()) {
            printTree(foundStudents, "Знайдені студенти (3-й курс, відм. оцінки, Україна)");
        } else {
            System.out.println("Студентів за заданим критерієм не знайдено");
        }
    }
}