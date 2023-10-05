import java.util.Scanner;

public class InputHelper {
    private static void getResult(int nValue) {
        if (nValue > 15) {
            BigFraction result = SeriesCalculator.calculateSeriesBigDecimal(nValue);

            System.out.println("Result: " +
                    result.getNumerator() + "/" +
                    result.getDenominator() + " [ BigDecimal ]\n"
            );
        } else {
            Fraction result = SeriesCalculator.calculateSeriesRegular(nValue);

            System.out.println("Result: " +
                    result.getNumerator() + "/" +
                    result.getDenominator() + " [ int ]\n"
            );
        }
    }

    public static void start() {
        Scanner input = new Scanner(System.in);
        System.out.println("\n -- Harmonic Series Calculation -- \n");

        boolean exit = false;
        while (true) {
            System.out.print("Number of iterations: ");
            String nString = input.nextLine().trim();

            if (nString.isEmpty()) continue;

            if (nString.equals("exit")) break;

            try {
                int nValue = Integer.parseInt(nString);
                getResult(nValue);
            } catch (Exception exception) {
                System.out.println("Exception occurred: " + exception.getMessage());
                return;
            }
        }
    }
}
