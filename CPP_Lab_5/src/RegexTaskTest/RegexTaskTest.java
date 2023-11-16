package RegexTaskTest;

import RegexTask.TextProcessor;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RegexTaskTest {

    Pattern pattern = Pattern.compile("(?<letter>[a-zA-Z])\\b(?=[ .,:;'\"!?()]|($))");

    @Test
    void processEmptyString() {
        var processor = new TextProcessor();
        assertEquals("", processor.uppercaseLastLetter(pattern, "", "letter"));
    }

    @Test
    void processStringOfSpaces() {
        var processor = new TextProcessor();
        assertEquals("    ", processor.uppercaseLastLetter(pattern, "    ", "letter"));
    }

    @Test
    void processSingleWord() {
        var processor = new TextProcessor();
        assertEquals("worD", processor.uppercaseLastLetter(pattern, "word", "letter"));
    }

    @Test
    void processWordWithApostrophe() {
        var processor = new TextProcessor();
        assertEquals(
            "industry`S",
            processor.uppercaseLastLetter(pattern, "industry`s", "letter")
        );
    }

    @Test
    void processWordWithMultipleApostrophes() {
        var processor = new TextProcessor();
        assertEquals(
            "indust`ry`S",
            processor.uppercaseLastLetter(pattern, "indust`ry`s", "letter")
        );
    }

    @Test
    void processSentence() {
        var processor = new TextProcessor();
        assertEquals(
            "LoreM IpsuM iS simplY dummY texT oF thE printinG anD typesettinG industrY.",
            processor.uppercaseLastLetter(
                pattern,
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
                "letter"
            )
        );
    }

    @Test
    void processQuestion() {
        var processor = new TextProcessor();
        assertEquals(
            "LoreM IpsuM iS simplY dummY texT oF thE printinG anD typesettinG industrY?",
            processor.uppercaseLastLetter(
                pattern,
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry?",
                "letter"
            )
        );
    }

    @Test
    void processSentenceWithMark() {
        var processor = new TextProcessor();
        assertEquals(
            "LoreM IpsuM iS simplY dummY texT oF thE printinG anD typesettinG industrY!",
            processor.uppercaseLastLetter(
                pattern,
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry!",
                "letter"
            )
        );
    }

    @Test
    void processSentenceWithCommas() {
        var processor = new TextProcessor();
        assertEquals(
            "LoreM IpsuM, iS simplY, dummY texT oF, thE printinG, anD typesettinG industrY.",
            processor.uppercaseLastLetter(
                pattern,
                "Lorem Ipsum, is simply, dummy text of, the printing, and typesetting industry.",
                "letter"
            )
        );
    }

    @Test
    void processSentenceWithParenthesis() {
        var processor = new TextProcessor();
        assertEquals(
            "LoreM IpsuM iS (simplY) dummY texT oF thE (printinG) anD typesettinG industrY.",
            processor.uppercaseLastLetter(
                pattern,
                "Lorem Ipsum is (simply) dummy text of the (printing) and typesetting industry.",
                "letter"
            )
        );
    }

    @Test
    void processSentenceWithColonsAndSemicolons() {
        var processor = new TextProcessor();
        assertEquals(
            "LoreM IpsuM; iS simplY dummY: texT oF thE printinG: anD typesettinG; industrY.",
            processor.uppercaseLastLetter(
                pattern,
                "Lorem Ipsum; is simply dummy: text of the printing: and typesetting; industry.",
                "letter"
            )
        );
    }

    @Test
    void processSentenceWithoutDot() {
        var processor = new TextProcessor();
        assertEquals(
            "LoreM IpsuM iS simplY dummY texT oF thE printinG anD typesettinG industrY",
            processor.uppercaseLastLetter(
                pattern,
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry",
                "letter"
            )
        );
    }
}