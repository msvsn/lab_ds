import java.util.Scanner;

public class Level2 extends Level1 {

    protected enum FAState {
        Q0,
        Q1_DIGITS_BEFORE_E,
        Q2_AFTER_E,
        Q3_DIGITS_AFTER_E,
        REJECT
    }

    public boolean parseWithSwitch(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }

        FAState currentState = FAState.Q0;
        char[] chars = input.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            char ch = chars[i];
            switch (currentState) {
                case Q0:
                    if (Character.isDigit(ch)) {
                        currentState = FAState.Q1_DIGITS_BEFORE_E;
                    } else {
                        currentState = FAState.REJECT;
                    }
                    break;
                case Q1_DIGITS_BEFORE_E:
                    if (Character.isDigit(ch)) {
                    } else if (ch == 'E') {
                        currentState = FAState.Q2_AFTER_E;
                    } else {
                        currentState = FAState.REJECT;
                    }
                    break;
                case Q2_AFTER_E:
                    if (Character.isDigit(ch)) {
                        currentState = FAState.Q3_DIGITS_AFTER_E;
                    } else {
                        currentState = FAState.REJECT;
                    }
                    break;
                case Q3_DIGITS_AFTER_E:
                    if (Character.isDigit(ch)) {
                    } else if (ch == 'E') {
                        currentState = FAState.Q2_AFTER_E;
                    } else {
                        currentState = FAState.REJECT;
                    }
                    break;
                case REJECT:
                    return false;
            }
            if (currentState == FAState.REJECT) {
                break;
            }
        }
        return currentState == FAState.Q3_DIGITS_AFTER_E;
    }

    public static void main(String[] args) {
        Level1 level1Demo = new Level1();
        String level1FilenameL2 = "level1_input_from_l2.txt";
        level1Demo.createSampleLevel1File(level1FilenameL2);
        System.out.println("Слова, що відповідають шаблону, для L1 regex (\\d+\\*E\\d+):");
        level1Demo.findMatchingWords(level1FilenameL2, LEVEL1_REGEX)
                .forEach(System.out::println);
        System.out.println("-------------------------------------------------------------");

        Level2 level2 = new Level2();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Кінцевий автомат для regex: (\\d+E)+\\d+");
        System.out.println("Стан: Q0 (Початковий), Q1 (Цифри перед E), Q2 (Після E), Q3 (Цифри після E - приймаючий/цикл)");
        System.out.println("Введіть рядок для перевірки (12E34, 1E2E3):");
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) {
                break;
            }

            boolean isValid = level2.parseWithSwitch(input);
            if (isValid) {
                System.out.println("Рядок '" + input + "' є ДІЙСНИМ.");
            } else {
                System.out.println("Рядок '" + input + "' є НЕДІЙСНИМ.");
            }
        }
        scanner.close();
    }
}
