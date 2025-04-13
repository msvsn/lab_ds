import java.util.Random;
import java.util.stream.IntStream;

public class Level1 {
    protected static class QuadraticProbingHashTable {
        private Integer[] table;
        protected int size;

        public QuadraticProbingHashTable(int size) {
            this.size = size;
            this.table = new Integer[size];
        }

        protected int hash(int key) {
            return Math.abs(key % size);
        }

        protected int probe(int index, int i) {
            return (index + i * i) % size;
        }

        public void insert(int key) {
            int index = hash(key);
            int i = 0;
            while (table[probe(index, i)] != null) {
                i++;
                if (i >= size) return;
            }
            table[probe(index, i)] = key;
        }

        public boolean search(int key) {
            int index = hash(key);
            int i = 0;
            while (true) {
                int probeIndex = probe(index, i);
                if (table[probeIndex] == null) return false;
                if (table[probeIndex] == key) return true;
                i++;
                if (i >= size) return false;
            }
        }
    }

    protected static int[] generateData(int size) {
        Random random = new Random(42);
        return IntStream.generate(() -> random.nextInt(1000000))
                .limit(size)
                .toArray();
    }

    public static void main(String[] args) {
        int N = 100;
        int[] sizes = {N, N * N, N * N * N};
        int runs = 10;

        for (int size : sizes) {
            System.out.println("Розмірність кількості елементів масиву N=" + size);
            System.out.println("Запускаємо алгоритм " + runs + " разів та знаходимо середній час виконання");

            int[] data = generateData(size);
            double res = 0;
            for (int i = 1; i <= runs; i++) {
                QuadraticProbingHashTable hashTable = new QuadraticProbingHashTable(size * 2);
                long start = System.nanoTime();
                for (int key : data) {
                    hashTable.insert(key);
                }
                for (int key : data) {
                    hashTable.search(key);
                }
                long end = System.nanoTime();
                double time = (double) (end - start) / 1000000000.0;
                System.out.printf("%d ітерація\nКвадратичне зондування: %.9f сек\n", i, time);
                res += time;
            }
            res /= runs;
            System.out.printf("Результат для Квадратичного зондування: %.9f сек\n\n", res);
        }
    }
}
