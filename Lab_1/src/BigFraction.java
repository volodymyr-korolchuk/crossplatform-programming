import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigFraction {
    private BigDecimal numerator;
    private BigDecimal denominator;

    public BigFraction() {
        this.numerator = BigDecimal.ZERO;
        this.denominator = BigDecimal.ONE;
    }

    public BigFraction(BigDecimal numerator, BigDecimal denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public BigFraction(BigFraction other) {
        this.numerator = other.getNumerator();
        this.denominator = other.getDenominator();
    }

    private BigDecimal findLCD(BigDecimal first, BigDecimal second) {
        BigDecimal addition = first;

        while (first.remainder(second).compareTo(BigDecimal.ZERO) != 0) {
            first = first.add(addition);
        }
        return first;
    }

    public BigDecimal findGCD(BigDecimal first, BigDecimal second) {
        BigDecimal addition = second;

        while (second.compareTo(BigDecimal.ZERO) != 0) {
            addition = second;
            second = first.remainder(second);
            first = addition;
        }

        return first;
    }

    public BigFraction add(BigFraction second) {
        if (this.denominator.equals(BigDecimal.ZERO) ||
                second.denominator.equals(BigDecimal.ZERO)) {
            throw new IllegalArgumentException("Invalid denominator value");
        }

        // least common denominator
        BigDecimal lcd = findLCD(this.denominator, second.denominator);

        BigDecimal firstNumerator = this.numerator.multiply(lcd).divide(
                this.denominator,
                0,
                RoundingMode.HALF_DOWN
        );
        BigDecimal secondNumerator = second.numerator.multiply(lcd).divide(
                second.denominator,
                0,
                RoundingMode.HALF_DOWN
        );

        BigDecimal resultNumerator = firstNumerator.add(secondNumerator);
        return new BigFraction(resultNumerator, lcd);
    }

    public void reduce() {
        BigDecimal gcd = findGCD(numerator, denominator);
        numerator = numerator.divide(gcd, 0, RoundingMode.HALF_DOWN);
        denominator = denominator.divide(gcd, 0, RoundingMode.HALF_DOWN);
    }

    public BigDecimal getNumerator() {
        return numerator;
    }

    public BigDecimal getDenominator() {
        return denominator;
    }

}