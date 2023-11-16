package RegexTask;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextProcessor {
    public String uppercaseLastLetter(Pattern pattern, String input, String matchGroupName) {
        Matcher mt = pattern.matcher(input);
        return mt.replaceAll(matchResult -> matchResult.group(matchGroupName).toUpperCase());
    }
}
