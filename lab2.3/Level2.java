import java.util.Scanner;

public class Level2 extends Level1 {
    protected long arrangementsWithRepetition(int n, int k) {
        return (long) Math.pow(n, k);
    }

    @Override
    public void solve() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Обчислення кількості паролів із голосних букв");

        int n = 0;
        while (true) {
            System.out.print("Введіть кількість голосних букв: ");
            if (scanner.hasNextInt()) {
                n = scanner.nextInt();
                if (n > 0 && n <= 6) break;
                else if (n > 6) {
                    System.out.println("В латинському алфавіті є 6 голосних букв.");
                } else {
                    System.out.println("Кількість букв має бути більше 0.");
                }
            } else {
                System.out.println("Введіть ціле число.");
                scanner.next();
            }
        }

        int k = 0;
        while (true) {
            System.out.print("Введіть довжину пароля: ");
            if (scanner.hasNextInt()) {
                k = scanner.nextInt();
                if (k > 0) break;
                else System.out.println("Довжина пароля має бути більше 0.");
            } else {
                System.out.println("Введіть ціле число.");
                scanner.next();
            }
        }

        long result = arrangementsWithRepetition(n, k);
        System.out.println("Кількість паролів довжиною " + k + " із " + n + " голосних букв: " + result);
    }
    public static void main(String[] args) {
        Level2 level2 = new Level2();
        level2.solve();
    }
}
