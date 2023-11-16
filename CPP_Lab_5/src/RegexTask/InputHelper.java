package RegexTask;

import java.util.Scanner;
import java.util.regex.Pattern;

public class InputHelper {
    public void start() {
        Scanner scanner = new Scanner(System.in);
        Pattern pt = Pattern.compile("(?<letter>[a-zA-Z])\\b(?=[ .,:;'\"!?()]|($))");
        TextProcessor processor = new TextProcessor();

        while(true) {
            System.out.print("\nInput: ");

            var input = scanner.nextLine().trim();
            if (input.equals("exit")) break;

            String res = processor.uppercaseLastLetter(pt, input, "letter");
            System.out.printf(
                "%n%s: %n%s %n",
                "Processed text",
                !res.isEmpty() ? res : "[ input was empty ]"
            );
        }
    }


}

