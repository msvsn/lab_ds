import java.util.Random;
import java.util.stream.IntStream;

public class Level3 extends Level2 {
    private static int[] generateBestCaseData(int size) {
        return IntStream.range(0, size).toArray();
    }

    private static int[] generateWorstCaseData(int size, int tableSize) {
        int[] data = new int[size];
        int hash = new Random().nextInt(tableSize);
        for (int i = 0; i < size; i++) {
            data[i] = hash + i * tableSize;
        }
        return data;
    }

    private static int[] generateAverageCaseData(int size) {
        return generateData(size);
    }

    private static double measureTime(QuadraticProbingHashTable table, int[] data) {
        long start = System.nanoTime();
        for (int key : data) table.insert(key);
        for (int key : data) table.search(key);
        long end = System.nanoTime();
        return (double) (end - start) / 1000000000.0;
    }

    public static void main(String[] args) {
        int size = 10000;
        int tableSize = size * 2;
        int runs = 10;

        System.out.println("Розмірність кількості елементів масиву N=" + size);

        System.out.println("Найкращий випадок розташування елементів");
        int[] bestData = generateBestCaseData(size);
        double quadBestRes = 0, linearBestRes = 0;
        for (int i = 1; i <= runs; i++) {
            double quadTime = measureTime(new QuadraticProbingHashTable(tableSize), bestData);
            double linearTime = measureTime(new LinearProbingHashTable(tableSize), bestData);
            quadBestRes += quadTime;
            linearBestRes += linearTime;
            System.out.printf("%d ітерація\nКвадратичне зондування: %.9f сек\nЛінійне зондування: %.9f сек\n", i, quadTime, linearTime);
        }
        quadBestRes /= runs;
        linearBestRes /= runs;
        System.out.printf("Результат для Квадратичного зондування: %.9f сек\n", quadBestRes);
        System.out.printf("Результат для Лінійного зондування: %.9f сек\n\n", linearBestRes);

        System.out.println("Найгірший випадок розташування елементів");
        int[] worstData = generateWorstCaseData(size, tableSize);
        double quadWorstRes = 0, linearWorstRes = 0;
        for (int i = 1; i <= runs; i++) {
            double quadTime = measureTime(new QuadraticProbingHashTable(tableSize), worstData);
            double linearTime = measureTime(new LinearProbingHashTable(tableSize), worstData);
            quadWorstRes += quadTime;
            linearWorstRes += linearTime;
            System.out.printf("%d ітерація\nКвадратичне зондування: %.9f сек\nЛінійне зондування: %.9f сек\n", i, quadTime, linearTime);
        }
        quadWorstRes /= runs;
        linearWorstRes /= runs;
        System.out.printf("Результат для Квадратичного зондування: %.9f сек\n", quadWorstRes);
        System.out.printf("Результат для Лінійного зондування: %.9f сек\n\n", linearWorstRes);

        System.out.println("Середній випадок розташування елементів");
        int[] avgData = generateAverageCaseData(size);
        double quadAvgRes = 0, linearAvgRes = 0;
        for (int i = 1; i <= runs; i++) {
            double quadTime = measureTime(new QuadraticProbingHashTable(tableSize), avgData);
            double linearTime = measureTime(new LinearProbingHashTable(tableSize), avgData);
            quadAvgRes += quadTime;
            linearAvgRes += linearTime;
            System.out.printf("%d ітерація\nКвадратичне зондування: %.9f сек\nЛінійне зондування: %.9f сек\n", i, quadTime, linearTime);
        }
        quadAvgRes /= runs;
        linearAvgRes /= runs;
        System.out.printf("Результат для Квадратичного зондування: %.9f сек\n", quadAvgRes);
        System.out.printf("Результат для Лінійного зондування: %.9f сек\n", linearAvgRes);
    }
}
