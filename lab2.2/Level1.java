import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Level1 {

    protected static final String LEVEL1_REGEX = "\\d+\\*E\\d+";
    public void createSampleLevel1File(String filename) {
        List<String> words = List.of(
                "123*E456",
                "abc*E123",
                "9*E0",
                "word",
                "78*E234",
                "123*DEF",
                "45*E",
                "*E345"
        );
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String word : words) {
                writer.write(word);
                writer.newLine();
            }
            System.out.println("Файл '" + filename + "' створений.");
        } catch (IOException e) {
            System.err.println("Помилка при створенні файла '" + filename + "': " + e.getMessage());
        }
    }

    public List<String> findMatchingWords(String filename, String regexPattern) {
        List<String> matchingWords = new ArrayList<>();
        Pattern pattern = Pattern.compile(regexPattern);

        if (!Files.exists(Paths.get(filename))) {
            System.err.println("Файл '" + filename + "' не знайдено. Створюємо зразок.");
            createSampleLevel1File(filename);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Matcher matcher = pattern.matcher(line.trim());
                if (matcher.matches()) {
                    matchingWords.add(line.trim());
                }
            }
        } catch (IOException e) {
            System.err.println("Помилка при читанні файла '" + filename + "': " + e.getMessage());
        }
        return matchingWords;
    }

    public static void main(String[] args) {
        Level1 level1 = new Level1();
        String level1Filename = "level1_input.txt";
        level1.createSampleLevel1File(level1Filename);
        System.out.println("Регулярний вираз для рівня 1: " + LEVEL1_REGEX.replace("\\", ""));
        List<String> matchedWords = level1.findMatchingWords(level1Filename, LEVEL1_REGEX);
        if (matchedWords.isEmpty()) {
            System.out.println("Слів, що відповідають шаблону, не знайдено в '" + level1Filename + "'.");
        } else {
            System.out.println("Слова, що знайдені в '" + level1Filename + "' і відповідають шаблону:");
            for (String word : matchedWords) {
                System.out.println(word);
            }
        }
    }
}
