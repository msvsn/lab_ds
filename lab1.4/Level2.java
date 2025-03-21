import java.util.Arrays;
import java.util.Comparator;

public class Level2 extends Level1 {

    static class StudentList {
        private Student[] students;
        private int size;

        public StudentList(int capacity) {
            this.students = new Student[capacity];
            this.size = 0;
        }

        public void addStudent(Student student) {
            students[size++] = student;
        }

        public Student[] getStudents() {
            return students;
        }

        public int size() {
            return size;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < size; i++) {
                sb.append(students[i]).append("\n");
            }
            return sb.toString();
        }

        public String toStringSortedByRegionAndCity() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < size; i++) {
                sb.append("Область: ").append(students[i].getRegion())
                        .append(", Місто: ").append(students[i].getCity())
                        .append(", ").append(students[i].getLastName()).append(" ")
                        .append(students[i].getFirstName()).append(", група: ")
                        .append(students[i].getGroup()).append("\n");
            }
            return sb.toString();
        }
    }

    public static void sortByRegionAndCity(StudentList studentList) {
        Student[] students = studentList.getStudents();
        int size = studentList.size();
        Integer[] index = new Integer[size];
        for (int i = 0; i < size; i++) {
            index[i] = i;
        }
        Arrays.sort(index, Comparator.comparing(i -> students[i].getRegion()));
        for (int i = 0; i < size; i++) {
            String currentRegion = students[index[i]].getRegion();
            int start = i;
            while (i + 1 < size && students[index[i + 1]].getRegion().equals(currentRegion)) {
                i++;
            }
            int end = i;
            Arrays.sort(index, start, end + 1, Comparator.comparing(i2 -> students[i2].getCity()));
        }
        Student[] sortedStudents = new Student[size];
        for (int i = 0; i < size; i++) {
            sortedStudents[i] = students[index[i]];
        }
        System.arraycopy(sortedStudents, 0, students, 0, size);
    }

    public static void main(String[] args) {
        StudentList studentList = new StudentList(10);
        studentList.addStudent(new Student("Вишнівенко", "Марія", "ПЗ-21", "Київ", "Київська"));
        studentList.addStudent(new Student("Вишнівецька", "Марія", "ПЗ-22", "Обухів", "Київська"));
        studentList.addStudent(new Student("Вишня", "Антон", "ПЗ-21", "Одеса", "Одеська"));
        studentList.addStudent(new Student("Вишнева", "Оксана", "ПЗ-23", "Білгород-Дністровський", "Одеська"));
        studentList.addStudent(new Student("Вишенька", "Сергій", "ПЗ-22", "Сергіївка", "Одеська"));
        studentList.addStudent(new Student("Вишунька", "Анна", "ПЗ-21", "Вінниця", "Вінницька"));
        studentList.addStudent(new Student("Вишнівський", "Роман", "ПЗ-23", "Житомир", "Житомирська"));
        studentList.addStudent(new Student("Вишніченко", "Наталія", "ПЗ-22", "Батурин", "Чернігівська"));
        studentList.addStudent(new Student("Вишка", "Андрій", "ПЗ-21", "Чернігів", "Чернігівська"));
        studentList.addStudent(new Student("Вишивка", "Олена", "ПЗ-23", "Бровари", "Київська"));

        System.out.println("Список студентів до сортування:");
        System.out.println(studentList);
        sortByRegionAndCity(studentList);
        System.out.println("Список студентів після сортування за областю та містом:");
        System.out.println(studentList.toStringSortedByRegionAndCity());
    }
}
