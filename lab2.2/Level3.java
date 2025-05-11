import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Level3 extends Level2 {

    protected enum CharacterType {
        DIGIT,
        E_CHAR,
        OTHER
    }

    private Map<FAState, Map<CharacterType, FAState>> transitionTable;

    public Level3() {
        super();
        initializeTransitionTable();
    }

    private void initializeTransitionTable() {
        transitionTable = new EnumMap<>(FAState.class);

        Map<CharacterType, FAState> q0Transitions = new EnumMap<>(CharacterType.class);
        q0Transitions.put(CharacterType.DIGIT, FAState.Q1_DIGITS_BEFORE_E);
        q0Transitions.put(CharacterType.E_CHAR, FAState.REJECT);
        q0Transitions.put(CharacterType.OTHER, FAState.REJECT);
        transitionTable.put(FAState.Q0, q0Transitions);

        Map<CharacterType, FAState> q1Transitions = new EnumMap<>(CharacterType.class);
        q1Transitions.put(CharacterType.DIGIT, FAState.Q1_DIGITS_BEFORE_E);
        q1Transitions.put(CharacterType.E_CHAR, FAState.Q2_AFTER_E);
        q1Transitions.put(CharacterType.OTHER, FAState.REJECT);
        transitionTable.put(FAState.Q1_DIGITS_BEFORE_E, q1Transitions);

        Map<CharacterType, FAState> q2Transitions = new EnumMap<>(CharacterType.class);
        q2Transitions.put(CharacterType.DIGIT, FAState.Q3_DIGITS_AFTER_E);
        q2Transitions.put(CharacterType.E_CHAR, FAState.REJECT);
        q2Transitions.put(CharacterType.OTHER, FAState.REJECT);
        transitionTable.put(FAState.Q2_AFTER_E, q2Transitions);

        Map<CharacterType, FAState> q3Transitions = new EnumMap<>(CharacterType.class);
        q3Transitions.put(CharacterType.DIGIT, FAState.Q3_DIGITS_AFTER_E);
        q3Transitions.put(CharacterType.E_CHAR, FAState.Q2_AFTER_E);
        q3Transitions.put(CharacterType.OTHER, FAState.REJECT);
        transitionTable.put(FAState.Q3_DIGITS_AFTER_E, q3Transitions);

        Map<CharacterType, FAState> rejectTransitions = new EnumMap<>(CharacterType.class);
        rejectTransitions.put(CharacterType.DIGIT, FAState.REJECT);
        rejectTransitions.put(CharacterType.E_CHAR, FAState.REJECT);
        rejectTransitions.put(CharacterType.OTHER, FAState.REJECT);
        transitionTable.put(FAState.REJECT, rejectTransitions);
    }

    private CharacterType getCharacterType(char ch) {
        if (Character.isDigit(ch)) {
            return CharacterType.DIGIT;
        } else if (ch == 'E') {
            return CharacterType.E_CHAR;
        } else {
            return CharacterType.OTHER;
        }
    }

    public boolean parseWithTableAndFor(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }

        FAState currentState = FAState.Q0;
        char[] chars = input.toCharArray();

        for (char ch : chars) {
            CharacterType type = getCharacterType(ch);
            currentState = transitionTable.get(currentState).get(type);
            if (currentState == FAState.REJECT) {
                return false;
            }
        }
        return currentState == FAState.Q3_DIGITS_AFTER_E;
    }

    public void createSampleLevel3File(String filename) {
        List<String> lines = List.of(
                "1E2%34E56+7E8E9",
                "invalid_word%10E20",
                "100E200_300E400E500+another",
                "6E7_8E9",
                "noDELIM1E1",
                "1E2E3E4%5E6"
        );
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Приклад файлу '" + filename + "' створено.");
        } catch (IOException e) {
            System.err.println("Помилка при створенні прикладу файла '" + filename + "': " + e.getMessage());
        }
    }

    public void processLevel3File(String filename) {
        if (!Files.exists(Paths.get(filename))) {
            System.err.println("Файл '" + filename + "' не знайдено. Створюємо приклад.");
            createSampleLevel3File(filename);
        }

        String delimiterRegex = "[%+_]+";
        Pattern delimiterPattern = Pattern.compile(delimiterRegex);

        System.out.println("\nОбробка файлу: '" + filename + "'");
        System.out.println("Розділення слів за допомогою розділювачів: %, +, _");
        System.out.println("Перевірка слів проти FA для: (\\d+E)+\\d+\n");

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int lineNumber = 1;
            while ((line = reader.readLine()) != null) {
                System.out.println("Рядок " + lineNumber + ": '" + line + "'");
                String[] words = delimiterPattern.split(line);
                if (words.length == 0 && !line.isEmpty()) {
                    System.out.println("  Слово: '' (пусте після розділення) -> НЕДІЙСНЕ (вимагає непустого для FA)");
                } else if (words.length == 1 && line.matches(delimiterRegex+".*") && words[0].isEmpty()) {
                } else if (words.length == 0 && line.isEmpty()){
                    System.out.println("  Рядок є пустим.");
                }

                for (String word : words) {
                    if (word.isEmpty()) {
                        continue;
                    }
                    boolean isValid = parseWithTableAndFor(word);
                    System.out.println("  Слово: '" + word + "' -> " + (isValid ? "ДІЙСНЕ" : "НЕДІЙСНЕ"));
                }
                lineNumber++;
            }
        } catch (IOException e) {
            System.err.println("Помилка при читанні або обробці файла '" + filename + "': " + e.getMessage());
        }
    }


    public static void main(String[] args) {
        Level3 level3 = new Level3();
        String level3Filename = "level3_input.txt";

        boolean testL2 = level3.parseWithSwitch("123E45E6");
        System.out.println("Тест з L2 (123E45E6): " + (testL2 ? "ДІЙСНЕ" : "НЕДІЙСНЕ"));
        testL2 = level3.parseWithSwitch("123E45X");
        System.out.println("Тест з L2 (123E45X): " + (testL2 ? "ДІЙСНЕ" : "НЕДІЙСНЕ"));
        System.out.println("-------------------------------------------------------------");
        System.out.println("Кінцевий автомат для regex: (\\d+E)+\\d+");
        String testString1 = "98E76";
        String testString2 = "1E2E3E4";
        String testString3 = "1E2E";
        String testString4 = "E12";
        System.out.println("Прямий тест для  парсера:");
        System.out.println("'" + testString1 + "': " + (level3.parseWithTableAndFor(testString1) ? "ДІЙСНЕ" : "НЕДІЙСНЕ"));
        System.out.println("'" + testString2 + "': " + (level3.parseWithTableAndFor(testString2) ? "ДІЙСНЕ" : "НЕДІЙСНЕ"));
        System.out.println("'" + testString3 + "': " + (level3.parseWithTableAndFor(testString3) ? "ДІЙСНЕ" : "НЕДІЙСНЕ"));
        System.out.println("'" + testString4 + "': " + (level3.parseWithTableAndFor(testString4) ? "ДІЙСНЕ" : "НЕДІЙСНЕ"));

        level3.createSampleLevel3File(level3Filename);
        level3.processLevel3File(level3Filename);
    }
}
