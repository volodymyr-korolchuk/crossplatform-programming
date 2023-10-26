package Helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class TokenizeHelper {
    public List<String> tokenizeString(String str, String delim) {
        StringTokenizer tokenizer = new StringTokenizer(str, delim);
        List<String> tokenList = new ArrayList<>();

        while (tokenizer.hasMoreTokens()) {
            tokenList.add(tokenizer.nextToken());
        }

        return tokenList;
    }
}
