public class Level2 extends Level1 {
    protected static class LinearProbingHashTable extends QuadraticProbingHashTable {
        public LinearProbingHashTable(int size) {
            super(size);
        }

        @Override
        protected int probe(int index, int i) {
            return (index + i) % size;
        }
    }

    public static void main(String[] args) {
        int N = 100;
        int[] sizes = {N, N * N, N * N * N};
        int runs = 10;

        for (int size : sizes) {
            System.out.println("Розмірність кількості елементів масиву N=" + size);
            System.out.println("Запускаємо алгоритм " + runs + " разів та знаходимо середній час виконання");
            int[] data = generateData(size);
            double quadRes = 0;
            for (int i = 1; i <= runs; i++) {
                QuadraticProbingHashTable quadTable = new QuadraticProbingHashTable(size * 2);
                long start = System.nanoTime();
                for (int key : data) {
                    quadTable.insert(key);
                }
                for (int key : data) {
                    quadTable.search(key);
                }
                long end = System.nanoTime();
                double time = (double) (end - start) / 1000000000.0;
                System.out.printf("%d ітерація\nКвадратичне зондування: %.9f сек\n", i, time);
                quadRes += time;
            }
            quadRes /= runs;
            System.out.printf("Результат для Квадратичного зондування: %.9f сек\n\n", quadRes);
            double linearRes = 0;
            for (int i = 1; i <= runs; i++) {
                LinearProbingHashTable linearTable = new LinearProbingHashTable(size * 2);
                long start = System.nanoTime();
                for (int key : data) {
                    linearTable.insert(key);
                }
                for (int key : data) {
                    linearTable.search(key);
                }
                long end = System.nanoTime();
                double time = (double) (end - start) / 1000000000.0;
                System.out.printf("%d ітерація\nЛінійне зондування: %.9f сек\n", i, time);
                linearRes += time;
            }
            linearRes /= runs;
            System.out.printf("Результат для Лінійного зондування: %.9f сек\n\n", linearRes);
        }
    }
}
