import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class Level1 {
    protected long factorial(int n) {
        if (n < 0) return 0;
        long result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    protected long combinations(int n, int k) {
        if (k < 0 || n < k) return 0;
        return factorial(n) / (factorial(k) * factorial(n - k));
    }

    private void generateCombinations(int n, int k, int[] combination, int start, FileWriter writer) throws IOException {
        if (combination.length == k) {
            for (int i = 0; i < k; i++) {
                writer.write((combination[i] + 1) + " ");
            }
            writer.write("\n");
            return;
        }
        for (int i = start; i < n; i++) {
            int[] newCombination = new int[combination.length + 1];
            System.arraycopy(combination, 0, newCombination, 0, combination.length);
            newCombination[combination.length] = i;
            generateCombinations(n, k, newCombination, i + 1, writer);
        }
    }

    public void solve() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Обчислення кількості команд із студентів та збереження комбінацій");

        int n = 0, k = 0;
        while (true) {
            System.out.print("Введіть кількість учасників: ");
            if (scanner.hasNextInt()) {
                n = scanner.nextInt();
                if (n > 0) break;
                else System.out.println("Число має бути більше 0.");
            } else {
                System.out.println("Введіть ціле число.");
                scanner.next();
            }
        }

        while (true) {
            System.out.print("Введіть кількість студентів у команді: ");
            if (scanner.hasNextInt()) {
                k = scanner.nextInt();
                if (k > 0 && k <= n) break;
                else System.out.println("Число має бути більше 0 та не перевищувати кількість учасників.");
            } else {
                System.out.println("Будь ласка, введіть ціле число.");
                scanner.next();
            }
        }

        long result = combinations(n, k);
        System.out.println("Кількість різних за складом команд із " + k + " студентів із " + n + " учасників: " + result);

        try (FileWriter writer = new FileWriter("lab2_3_level1.txt")) {
            generateCombinations(n, k, new int[0], 0, writer);
            System.out.println("Комбінації збережено у файл lab2_3_level1.txt");
        } catch (IOException e) {
            System.out.println("Помилка при записі до файлу: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Level1 level1 = new Level1();
        level1.solve();
    }
}
