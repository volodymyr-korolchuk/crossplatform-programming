import java.math.BigDecimal;

public class SeriesCalculator {
    public static Fraction calculateSeriesRegular(int n) {
        Fraction sum = new Fraction();

        for (int i = 1; i <= n; i++) {
            sum = sum.add(new Fraction(1, i));
        }

        sum.reduce();
        return sum;
    }

    public static BigFraction calculateSeriesBigDecimal(int n) {
        BigFraction sum = new BigFraction();

        for (int i = 1; i <= n; i++) {
            sum = sum.add(new BigFraction(BigDecimal.ONE, BigDecimal.valueOf(i)));
        }

        sum.reduce();
        return sum;
    }
}
