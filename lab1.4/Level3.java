public class Level3 extends Level2 {
    public static void mergeSortByRegion(Student[] students) {
        Student[] temp = new Student[students.length];
        mergeSortRecursive(students, temp, 0, students.length - 1);
    }

    private static void mergeSortRecursive(Student[] array, Student[] temp, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSortRecursive(array, temp, left, mid);
            mergeSortRecursive(array, temp, mid + 1, right);
            merge(array, temp, left, mid, right);
        }
    }

    private static void merge(Student[] array, Student[] temp, int left, int mid, int right) {
        for (int i = left; i <= right; i++) {
            temp[i] = array[i];
        }
        int i = left;
        int j = mid + 1;
        int k = left;

        while (i <= mid && j <= right) {
            if (temp[i].getRegion().compareTo(temp[j].getRegion()) > 0) {
                array[k] = temp[i];
                i++;
            } else {
                array[k] = temp[j];
                j++;
            }
            k++;
        }
        while (i <= mid) {
            array[k] = temp[i];
            i++;
            k++;
        }
    }

    public static String getRegionSortedOutput(Student[] students) {
        StringBuilder sb = new StringBuilder();
        for (Student student : students) {
            sb.append("Область: ").append(student.getRegion())
                    .append(", ").append(student.getLastName()).append(" ")
                    .append(student.getFirstName()).append(", група: ")
                    .append(student.getGroup()).append(", місто: ")
                    .append(student.getCity()).append("\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Student[] students = new Student[12];
        students[0] = new Student("Вишнівецький", "Іван", "ПЗ-21", "Київ", "Київська");
        students[1] = new Student("Вишнівенко", "Марія", "ПЗ-22", "Львів", "Львівська");
        students[2] = new Student("Вишніченко", "Олексій", "ПЗ-21", "Одеса", "Одеська");
        students[3] = new Student("Вишнярук", "Оксана", "ПЗ-23", "Харків", "Харківська");
        students[4] = new Student("Вишневецький", "Сергій", "ПЗ-22", "Дніпро", "Дніпропетровська");
        students[5] = new Student("Вишнівка", "Анна", "ПЗ-21", "Вінниця", "Вінницька");
        students[6] = new Student("Вишенька", "Роман", "ПЗ-23", "Житомир", "Житомирська");
        students[7] = new Student("Вишня", "Наталія", "ПЗ-22", "Полтава", "Полтавська");
        students[8] = new Student("Вишка", "Андрій", "ПЗ-21", "Чернігів", "Чернігівська");
        students[9] = new Student("Вишнівеновка", "Олена", "ПЗ-23", "Бровари", "Київська");
        students[10] = new Student("Вишневий", "Дмитро", "ПЗ-22", "Суми", "Сумська");
        students[11] = new Student("Вишнева", "Софія", "ПЗ-21", "Херсон", "Херсонська");

        System.out.println("Масив студентів до сортування:");
        System.out.println(getRegionSortedOutput(students));
        mergeSortByRegion(students);
        System.out.println("Масив студентів після сортування методом злиття за областю (низхідний порядок):");
        System.out.println(getRegionSortedOutput(students));
    }
}
