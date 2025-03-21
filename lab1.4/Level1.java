import java.util.Arrays;

public class Level1 {
    static class Student {
        protected String lastName;
        protected String firstName;
        protected String group;
        protected String city;
        protected String region;

        public Student(String lastName, String firstName, String group, String city, String region) {
            this.lastName = lastName;
            this.firstName = firstName;
            this.group = group;
            this.city = city;
            this.region = region;
        }

        @Override
        public String toString() { //до сортування
            return lastName + " " + firstName + ", група: " + group + ", місто: " + city + ", область: " + region;
        }
        public String toStringSortedByRegion() { //після сортування
            return "Область: " + region + ", " + lastName + " " + firstName + ", група: " + group + ", місто: " + city;
        }

        public String getLastName() {
            return lastName;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getGroup() {
            return group;
        }

        public String getCity() {
            return city;
        }

        public String getRegion() {
            return region;
        }
    }

    public static void selectionSortByRegion(Student[] students) {
        for (int i = 0; i < students.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < students.length; j++) {
                if (students[j].getRegion().compareTo(students[minIndex].getRegion()) < 0) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                Student temp = students[i];
                students[i] = students[minIndex];
                students[minIndex] = temp;
            }
        }
    }

    public static void main(String[] args) {
        Student[] students = {
                new Student("Вишнівенко", "Марія", "ПЗ-21", "Київ", "Київська"),
                new Student("Вишнівська", "Марія", "ПЗ-22", "Львів", "Львівська"),
                new Student("Вишнівецька", "Мар'я", "ПЗ-21", "Одеса", "Одеська"),
                new Student("Вишня", "Оксана", "ПЗ-23", "Харків", "Харківська"),
                new Student("Вишенька", "Сергій", "ПЗ-22", "Дніпро", "Дніпропетровська"),
                new Student("Вишнева", "Анна", "ПЗ-21", "Вінниця", "Вінницька"),
                new Student("Вишневецький", "Роман", "ПЗ-23", "Житомир", "Житомирська")
        };

        System.out.println("Список студентів до сортування:");
        for (Student student : students) {
            System.out.println(student);
        }
        selectionSortByRegion(students);
        System.out.println("\nСписок студентів після сортування за областю:");
        for (Student student : students) {
            System.out.println(student.toStringSortedByRegion());
        }
    }
} 
