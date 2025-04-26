import java.util.InputMismatchException;
import java.util.Scanner;

public class Level2 extends Level1 {
    private static final double DEFAULT_TOLERANCE = 1e-6;
    private static final int DEFAULT_MAX_ITERATIONS = 100;
    protected Scanner scanner = new Scanner(System.in);

    private double rootFunction(double x) {
        try {
            if (2 * x > 700) return Double.POSITIVE_INFINITY;
            if (x < 0 && Math.exp(2*x) == 0) return -4.0;
            return x * Math.exp(2 * x) - 4;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return Double.NaN;
        }
    }
    private double rootFunctionDerivative(double x) {
        try {
            if (2 * x > 700) {
                if (1 + 2*x > 0) return Double.POSITIVE_INFINITY;
                if (1 + 2*x < 0) return Double.NEGATIVE_INFINITY;
                return Double.NaN;
            }
            if (x < -350 && (1 + 2*x) != 0 ) return 0.0;
            return Math.exp(2 * x) * (1 + 2 * x);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return Double.NaN;
        }
    }

    public Level2() {
        super();
    }
    protected static class RootFindingInputs {
        double a, b, tolerance, x0Newton, x0Secant, x1Secant;
        int maxIterations;
        RootFindingInputs(double a, double b, double tol, int maxIter, double x0n, double x0s, double x1s) {
            this.a = a; this.b = b; this.tolerance = tol; this.maxIterations = maxIter;
            this.x0Newton = x0n; this.x0Secant = x0s; this.x1Secant = x1s;
        }
    }
    protected static class MethodResult {
        Double root; Double valueAtRoot; int iterations; boolean converged; String message;
        MethodResult(Double root, Double val, int iterations, boolean converged, String message) {
            this.root = root; this.valueAtRoot = val; this.iterations = iterations; this.converged = converged; this.message = message;
        }
    }
    protected RootFindingInputs getRootFindingInputs() {
        double a = Double.NaN, b = Double.NaN;
        boolean validInput = false;

        System.out.println("\nПошук коренів рівняння x*e^(2x) - 4 = 0");
        System.out.println("Стандартні параметри:");
        System.out.printf("Точність: %.1e\n", DEFAULT_TOLERANCE);
        System.out.printf("Макс. ітерацій: %d\n", DEFAULT_MAX_ITERATIONS);

        while (!validInput) {
            try {
                System.out.print("Введіть початок інтервалу (a): ");
                a = scanner.nextDouble();
                System.out.print("Введіть кінець інтервалу (b): ");
                b = scanner.nextDouble();

                if (b <= a) {
                    System.out.println("Кінець інтервалу b повинен бути більшим за початок a.");
                    continue;
                }

                double fa = rootFunction(a);
                double fb = rootFunction(b);
                if (Double.isNaN(fa) || Double.isNaN(fb)) {
                    System.out.println("Неможливо обчислити функцію на межах введеного інтервалу.");
                    continue;
                }
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Введіть числове значення для меж інтервалу.");
                scanner.next();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                if (scanner.hasNextLine()) scanner.nextLine();
            }
        }

        double tolerance = DEFAULT_TOLERANCE;
        int maxIterations = DEFAULT_MAX_ITERATIONS;
        double x0Newton = (a + b) / 2.0;
        double x0Secant = a;
        double x1Secant = b;
        System.out.printf("Наближення для Ньютона: %.4f\n", x0Newton);
        System.out.printf("Наближення для Хорд: x0=%.4f, x1=%.4f\n", x0Secant, x1Secant);
        return new RootFindingInputs(a, b, tolerance, maxIterations, x0Newton, x0Secant, x1Secant);
    }

    public MethodResult bisectionMethod(double a, double b, double tol, int maxIter) {
        double fa = rootFunction(a); double fb = rootFunction(b);
        if (Double.isNaN(fa) || Double.isNaN(fb)) return new MethodResult(null, null, 0, false, "Не вдалося обчислити f на межах.");
        if (fa * fb >= 0) return new MethodResult(null, null, 0, false, "Однаковий знак на кінцях інтервалу.");
        int iter = 0; double c = a;
        while ((b - a) / 2.0 > tol && iter < maxIter) {
            iter++; c = a + (b - a) / 2.0; double fc = rootFunction(c);
            if (Double.isNaN(fc)) return new MethodResult(null, null, iter, false, "Помилка обчислення f(c).");
            if (Math.abs(fc) < 1e-15 || (b - a) / 2.0 < tol ) return new MethodResult(c, fc, iter, true, "Збіжність досягнуто.");
            if (fa * fc < 0) { b = c; } else { a = c; fa = fc; }
        }
        double final_c = a + (b-a)/2.0;
        String message = (iter == maxIter) ? "Досягнуто макс. ітерацій." : "Збіжність (за шириною інтервалу).";
        return new MethodResult(final_c, rootFunction(final_c), iter, iter < maxIter, message);
    }

    public MethodResult newtonMethod(double x0, double tol, int maxIter) {
        double xn = x0; int iter = 0;
        for (int i = 0; i < maxIter; i++) {
            iter = i + 1; double fxn = rootFunction(xn); double fpxn = rootFunctionDerivative(xn);
            if (Double.isNaN(fxn) || Double.isNaN(fpxn)) return new MethodResult(xn, null, iter, false, "Помилка f або f'.");
            if (Math.abs(fpxn) < 1e-12) return new MethodResult(xn, fxn, iter, false, "Похідна близька до 0.");
            double deltaX = fxn / fpxn;
            if (Math.abs(deltaX) > 1e10 * Math.abs(xn) && Math.abs(xn) > 1e-6) return new MethodResult(xn, fxn, iter, false, "Великий крок.");
            xn = xn - deltaX;
            double current_fxn = rootFunction(xn);
            if (Double.isNaN(current_fxn)) current_fxn = 0;
            if (Math.abs(deltaX) < tol || Math.abs(current_fxn) < tol ) return new MethodResult(xn, current_fxn, iter, true, "Збіжність досягнуто.");
        }
        double final_fxn_iter = rootFunction(xn); if(Double.isNaN(final_fxn_iter)) final_fxn_iter = Double.NaN;
        return new MethodResult(xn, final_fxn_iter, iter, false, "Досягнуто макс. ітерацій.");
    }

    public MethodResult secantMethod(double x_prev, double x_curr, double tol, int maxIter) {
        int iter = 0;
        double fx_prev = rootFunction(x_prev); if (Double.isNaN(fx_prev)) return new MethodResult(null, null, 0, false, "Помилка f(x_prev).");
        for (int i = 0; i < maxIter; i++) {
            iter = i + 1; double fx_curr = rootFunction(x_curr); if (Double.isNaN(fx_curr)) return new MethodResult(x_curr, null, iter, false, "Помилка f(x_curr).");
            double denominator = fx_curr - fx_prev;
            if (Math.abs(denominator) < 1e-12) {
                if (Math.abs(fx_curr) < tol) return new MethodResult(x_curr, fx_curr, iter, true, "Збіжність (знаменник=0, f=0).");
                else return new MethodResult(x_curr, fx_curr, iter, false, "Знаменник близький до 0, f!=0.");
            }
            double x_next = x_curr - fx_curr * (x_curr - x_prev) / denominator;
            double f_next = rootFunction(x_next); if(Double.isNaN(f_next)) f_next = 0;
            if (Math.abs(x_next - x_curr) < tol || Math.abs(f_next) < tol) return new MethodResult(x_next, f_next, iter, true, "Збіжність досягнуто.");
            x_prev = x_curr; fx_prev = fx_curr; x_curr = x_next;
        }
        double final_fxn_iter = rootFunction(x_curr); if(Double.isNaN(final_fxn_iter)) final_fxn_iter = Double.NaN;
        return new MethodResult(x_curr, final_fxn_iter, iter, false, "Досягнуто макс. ітерацій.");
    }

    public void runLevel2() {
        RootFindingInputs inputs = getRootFindingInputs();
        System.out.println("\nМетод Половинного Ділення:");
        MethodResult resB = bisectionMethod(inputs.a, inputs.b, inputs.tolerance, inputs.maxIterations);
        printResult(resB);
        System.out.println("\nМетод Ньютона (Дотичних):");
        MethodResult resN = newtonMethod(inputs.x0Newton, inputs.tolerance, inputs.maxIterations);
        printResult(resN);
        System.out.println("\nМетод Хорд (Січних):");
        MethodResult resS = secantMethod(inputs.x0Secant, inputs.x1Secant, inputs.tolerance, inputs.maxIterations);
        printResult(resS);
    }

    private void printResult(MethodResult result) {
        if (result.root != null && !Double.isNaN(result.root)) {
            System.out.printf("Корінь ≈ %.8f за %d ітерацій.\n", result.root, result.iterations);
            if (result.valueAtRoot != null && !Double.isNaN(result.valueAtRoot)) {
                System.out.printf("f(корінь) ≈ %.2e. Збіжність: %s. (%s)\n", result.valueAtRoot, result.converged, result.message);
            } else {
                System.out.printf("Не вдалося обчислити корінь. Збіжність: %s. (%s)\n", result.converged, result.message);
            }
        } else {
            System.out.printf(result.message);
        }
    }

    protected void closeScanner() {
        if (scanner != null) {
            try { scanner.close(); } catch (Exception e) { System.err.println(e.getMessage()); }
        }
    }

    public static void main(String[] args) {
        Level2 level2Solver = new Level2();
        try {
            level2Solver.runLevel2();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        } finally {
            level2Solver.closeScanner();
        }
    }
}
