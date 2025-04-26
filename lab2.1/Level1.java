import java.util.function.DoubleUnaryOperator;

public class Level1 {
    protected final DoubleUnaryOperator integrand = (x) -> {
        double denominator = Math.sqrt(1 + x * x + x);
        if (denominator == 0) {
            System.err.printf("Ділення на нуль в підінтегральній функції при x = %.4f\n", x);
            return Double.NaN;
        }
        if (1 + x * x + x < 0) {
            System.err.printf("Від'ємне значення під коренем в підінтегральній функції при x = %.4f\n", x);
            return Double.NaN;
        }
        return Math.exp(x) / denominator;
    };

    public Level1() {
    }

    public double rectangleMethodL(double a, double b, int n, DoubleUnaryOperator func) {
        double sum = 0.0;
        double h = (b - a) / n;
        for (int i = 0; i < n; i++) { sum += func.applyAsDouble(a + i * h); }
        return sum * h;
    }

    public double rectangleMethodR(double a, double b, int n, DoubleUnaryOperator func) {
        double sum = 0.0;
        double h = (b - a) / n;
        for (int i = 1; i <= n; i++) { sum += func.applyAsDouble(a + i * h); }
        return sum * h;
    }

    public double rectangleMethodMidpoint(double a, double b, int n, DoubleUnaryOperator func) {
        double sum = 0.0;
        double h = (b - a) / n;
        for (int i = 0; i < n; i++) { sum += func.applyAsDouble(a + (i + 0.5) * h); }
        return sum * h;
    }

    public double trapezoidMethod(double a, double b, int n, DoubleUnaryOperator func) {
        double h = (b - a) / n;
        double sum = (func.applyAsDouble(a) + func.applyAsDouble(b)) / 2.0;
        for (int i = 1; i < n; i++) { sum += func.applyAsDouble(a + i * h); }
        return sum * h;
    }

    public double simpsonsMethod(double a, double b, int n, DoubleUnaryOperator func) {
        if (n < 2) { System.out.println("Сімпсону потрібно n >= 2."); return Double.NaN; }
        double h = (b - a) / n;
        double sum = func.applyAsDouble(a) + func.applyAsDouble(b);
        for (int i = 1; i < n; i += 2) { sum += 4 * func.applyAsDouble(a + i * h); }
        for (int i = 2; i < n; i += 2) { sum += 2 * func.applyAsDouble(a + i * h); }
        return sum * h / 3.0;
    }

    public void runLevel1WithFixedParams() {
        System.out.println("\nЧисельне інтегрування");
        double a = 1.0;
        double b = 2.0;
        double h = 0.2;
        int n = (int) Math.round((b - a) / h);
        if (n <= 0) {
            System.out.print("Кількість кроків має бути більше 0");
            return;
        }
        System.out.printf("Використовуються фіксовані параметри: a=%.2f, b=%.2f, h=%.2f\n", a, b, h);
        System.out.printf("Розрахована кількість кроків: n=%d\n", n);
        double rectL = Double.NaN, rectR = Double.NaN, rectM = Double.NaN, trap = Double.NaN, simp = Double.NaN;
        try {
            rectL = rectangleMethodL(a, b, n, integrand);
            rectR = rectangleMethodR(a, b, n, integrand);
            rectM = rectangleMethodMidpoint(a, b, n, integrand);
            trap = trapezoidMethod(a, b, n, integrand);
            simp = simpsonsMethod(a, b, n, integrand);
        } catch (Exception e) { System.err.println(e.getMessage()); }
        System.out.printf("\nРезультати інтегрування для [%.2f, %.2f] з n=%d:\n", a, b, n);
        System.out.printf("  Метод лівих прямокутників:   %.8f\n", rectL);
        System.out.printf("  Метод правих прямокутників:  %.8f\n", rectR);
        System.out.printf("  Метод середніх прямокутників: %.8f\n", rectM);
        System.out.printf("  Метод трапецій:              %.8f\n", trap);
        System.out.printf("  Метод Сімпсона:              %.8f\n", simp);
    }

    public static void main(String[] args) {
        Level1 level1Solver = new Level1();
        try {
            level1Solver.runLevel1WithFixedParams();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
