import java.util.Random;
import java.util.Scanner;

public class Level1 {
    static class Student {
        String surname;
        String name;
        double averageGrade;
        int course;
        String phoneNumber;

        Student(String surname, String name, double averageGrade, int course, String phoneNumber) {
            this.surname = surname;
            this.name = name;
            this.averageGrade = averageGrade;
            this.course = course;
            this.phoneNumber = phoneNumber;
        }

        double getMark() {
            return averageGrade;
        }

        @Override
        public String toString() {
            return "Студент: " + surname + " " + name + ", Середній бал: " + String.format("%.2f", averageGrade).replace(".", ",") + ", Курс: " + course + ", Телефон: " + phoneNumber;
        }
    }

    protected Student[] students;

    public Level1() {
        students = new Student[20];
        Random rand = new Random();
        String[] lastNames = {"Вишнівенко", "Вишнічук", "Вишневський", "Вишня", "Вишка",
                "Вишенька", "Вишнівецька", "Вишнева", "Вишенонька", "Вишок"};
        String[] firstNames = {"Андрій", "Олександр", "Максим", "Данило", "Іван",
                "Марія", "Ольга", "Софія", "Анастасія", "Вікторія"};
        String[] operators = {"050", "067", "097", "063", "093"};

        for (int i = 0; i < 20; i++) {
            double grade = 60 + 40 * rand.nextDouble();
            students[i] = new Student(
                    lastNames[rand.nextInt(lastNames.length)],
                    firstNames[rand.nextInt(firstNames.length)],
                    grade,
                    rand.nextInt(4) + 1,
                    operators[rand.nextInt(operators.length)] + (1000000 + rand.nextInt(9000000))
            );
        }
    }

    public void printArray() {
        for (Student student : students) {
            System.out.println(student);
        }
    }

    public void sortByGrade() {
        for (int i = 0; i < students.length - 1; i++) {
            for (int j = 0; j < students.length - i - 1; j++) {
                if (students[j].averageGrade > students[j + 1].averageGrade) {
                    Student temp = students[j];
                    students[j] = students[j + 1];
                    students[j + 1] = temp;
                }
            }
        }
    }

    public Student interpolationSearch(double key) {
        sortByGrade();
        System.out.println("Відсортований масив:");
        printArray();
        return find(students, 0, students.length - 1, key);
    }

    private Student find(Student[] arr, int left, int right, double key) {
        if (left > right) {
            return null;
        }
        if (left == right) {
            if (Math.abs(arr[left].getMark() - key) <= 0.01) return arr[left];
            return null;
        }
        double range = arr[right].getMark() - arr[left].getMark();
        if (range == 0) return null;
        int med = left + (int)((right - left) * (key - arr[left].getMark()) / range);
        if (med < left || med > right) return null;
        if (Math.abs(arr[med].getMark() - key) <= 0.01) {
            return arr[med];
        }
        if (arr[med].getMark() > key) {
            return find(arr, left, med - 1, key);
        } else {
            return find(arr, med + 1, right, key);
        }
    }

    public static void main(String[] args) {
        Level1 level1 = new Level1();
        System.out.println("Початковий масив:");
        level1.printArray();
        Scanner scanner = new Scanner(System.in);
        double grade;
        System.out.print("Введіть середній бал для пошуку: ");
        String input = scanner.nextLine().replace(",", ".");
        grade = Double.parseDouble(input);
        Student found = level1.interpolationSearch(grade);
        if (found != null) {
            System.out.println("Знайдено: " + found.phoneNumber);
        } else {
            System.out.println("Студента з середнім балом " + String.format("%.2f", grade).replace(".", ",") + " не знайдено.");
        }
    }
}
