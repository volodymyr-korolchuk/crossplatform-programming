public class Fraction {
    private int numerator;
    private int denominator;

    public Fraction() {
        this.numerator = 0;
        this.denominator = 1;
    }

    public Fraction(int numerator, int denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public Fraction(Fraction other) {
        this.numerator = other.getNumerator();
        this.denominator = other.getDenominator();
    }

    private int findLCD(int first, int second) {
        int addition = first;
        while ((first % second) != 0) {
            first += addition;
        }
        return first;
    }

    public int findGCD(int first, int second) {
        int addition = second;

        while (second != 0) {
            addition = second;
            second = first % second;
            first = addition;
        }

        return first;
    }

    public Fraction add(Fraction second) {
        if (this.denominator == 0 && second.denominator == 0) {
            throw new IllegalArgumentException("Invalid denominator value");
        }

        // find the least common denominator
        int lcd = findLCD(this.denominator, second.denominator);

        long firstNumerator = this.numerator * (long) lcd / this.denominator;
        long secondNumerator = second.numerator * (long) lcd / second.denominator;

        int resultNumerator = (int) firstNumerator + (int) secondNumerator;
        return new Fraction(resultNumerator, lcd);
    }

    public void reduce() {
        int gcd = findGCD(numerator, denominator);
        numerator = numerator / gcd;
        denominator = denominator / gcd;
    }

    public int getNumerator() {
        return numerator;
    }

    public int getDenominator() {
        return denominator;
    }

}