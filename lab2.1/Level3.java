import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

@FunctionalInterface
interface OdeFunctionInterface {
    double apply(double x, double y);
}

public class Level3 extends Level2 {

    private final OdeFunctionInterface odeDerivative = (x, y) -> {
        if (Math.abs(y) < 1e-12) {
            System.err.printf("Ділення на близький до нуля y в ДР при x=%.4f\n", x);
            return Double.NaN;
        }
        return (1.0 - 2.0 * x) / (y * y);
    };

    public Level3() {
        super();
    }

    protected static class OdeInputs {
        double x0, y0, xn_target, h; int numSteps;
        OdeInputs(double x0, double y0, double xn_target, double h, int numSteps) {
            this.x0 = x0; this.y0 = y0; this.xn_target = xn_target; this.h = h; this.numSteps = numSteps;
        }
    }

    protected static class OdeResult {
        List<Double> xValues; List<Double> yValues; String message;
        OdeResult(List<Double> x, List<Double> y, String message) {
            this.xValues = x; this.yValues = y; this.message = message;
        }
    }

    protected OdeInputs getOdeInputs() {
        double x0 = 0, y0 = 0, xn_target = 0, h = 0; int n = 0;
        boolean validInput = false;
        System.out.println("\nВведіть параметри для розв'язання ДР: dy/dx = (1 - 2x) / y^2");
        while (!validInput) {
            try {
                System.out.print("Початкове x (x0): "); x0 = scanner.nextDouble();
                System.out.print("Початкове y (y0, не 0): "); y0 = scanner.nextDouble();
                if (Math.abs(y0) < 1e-12) { System.out.println("y0 не може бути 0."); continue; }
                System.out.print("Кінцеве x (xn): "); xn_target = scanner.nextDouble();
                System.out.print("Крок (h): "); h = scanner.nextDouble();
                if (h == 0) { System.out.println("h не може бути 0."); continue; }
                if (Math.abs(xn_target - x0) < 1e-12){ n = 0; System.out.println("Попередження: xn = x0."); }
                else if ((xn_target > x0 && h < 0) || (xn_target < x0 && h > 0)) { System.out.println("Напрямок h конфліктує з [x0, xn]."); continue; }
                else {
                    n = (int) Math.round(Math.abs(xn_target - x0) / Math.abs(h));
                    if (n == 0) { System.out.printf("Крок h=%.4f занадто великий для [%.4f, %.4f].\n", h, x0, xn_target); continue; }
                    System.out.printf("Початкова умова (x0=%.4f, y0=%.4f), xn=%.4f, h=%.4f, n=%d\n", x0, y0, xn_target, h, n);
                }
                validInput = true;
            } catch (InputMismatchException e) { System.out.println("Невірний ввід."); scanner.next();
            } catch (Exception e) { System.out.println(e.getMessage()); scanner.nextLine(); }
        }
        return new OdeInputs(x0, y0, xn_target, h, n);
    }

    public OdeResult rungeKutta4(double x0, double y0, double xn_target, double h, int n) {
        List<Double> xVal = new ArrayList<>(); List<Double> yVal = new ArrayList<>();
        String message = "Обчислення завершено.";
        xVal.add(x0); yVal.add(y0);
        double x = x0; double y = y0; boolean error = false;

        for (int i = 0; i < n; i++) {
            double current_h = h;
            if ((h > 0 && x + h > xn_target + 1e-9) || (h < 0 && x + h < xn_target - 1e-9)) { current_h = xn_target - x; }
            if (Math.abs(current_h) < 1e-12) break;
            try {
                double k1 = current_h * odeDerivative.apply(x, y); if(Double.isNaN(k1)) throw new ArithmeticException("k1 NaN");
                double k2 = current_h * odeDerivative.apply(x + 0.5 * current_h, y + 0.5 * k1); if(Double.isNaN(k2)) throw new ArithmeticException("k2 NaN");
                double k3 = current_h * odeDerivative.apply(x + 0.5 * current_h, y + 0.5 * k2); if(Double.isNaN(k3)) throw new ArithmeticException("k3 NaN");
                double k4 = current_h * odeDerivative.apply(x + current_h, y + k3); if(Double.isNaN(k4)) throw new ArithmeticException("k4 NaN");
                y = y + (k1 + 2.0*k2 + 2.0*k3 + k4) / 6.0; x = x + current_h;
                xVal.add(x); yVal.add(y);
                if (Math.abs(x - xn_target) < 1e-12) break;
            } catch (ArithmeticException e) { message = "Помилка на кроці " + (i+1) + ": " + e.getMessage(); error = true; break; }
        }
        if (!error && xVal.size() > 0 && Math.abs(xVal.get(xVal.size()-1) - xn_target) > 1e-9) {
            message += String.format(" Обчислення зупинено на x=%.4f.", xVal.get(xVal.size()-1));
        }
        return new OdeResult(xVal, yVal, message);
    }

    public void runLevel3() {
        System.out.println("\nРозв'язання ДР");
        OdeInputs inputs = getOdeInputs();
        if (inputs.numSteps <= 0 && Math.abs(inputs.x0 - inputs.xn_target) >= 1e-12) {
            System.out.println("Неможливо виконати розв'язання ДР з n=" + inputs.numSteps + " кроками.");
        } else if (Math.abs(inputs.x0 - inputs.xn_target) < 1e-12) {
            System.out.println("Початкове і кінцеве x збігаються.");
            printOdeTable(new OdeResult(List.of(inputs.x0), List.of(inputs.y0), "Початкова умова."));
        }
        else {
            OdeResult results = rungeKutta4(inputs.x0, inputs.y0, inputs.xn_target, inputs.h, inputs.numSteps);
            printOdeTable(results);
        }
        closeScanner();
    }

    private void printOdeTable(OdeResult results) {
        System.out.println("\nТаблиця результатів (x, y):");
        System.out.println("+------------+----------------+");
        System.out.printf("| %10s | %14s |\n", "x", "y");
        System.out.println("+------------+----------------+");
        if (results.xValues == null || results.xValues.isEmpty()) {
            System.out.println("|          (Немає даних)          |");
        } else {
            for (int i = 0; i < results.xValues.size(); i++) {
                System.out.printf("| %10.4f | %14.6f |\n", results.xValues.get(i), results.yValues.get(i));
            }
        }
        System.out.println(results.message);
    }


    public static void main(String[] args) {
        Level3 level3Solver = new Level3();
        try {
            level3Solver.runLevel3();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            level3Solver.closeScanner();
        }
    }
}
