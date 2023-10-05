package Helpers;

public class EnumPrintHelper {
    public String getFormattedEnumName(String name) {
        String[] words = name.split("_");
        StringBuilder formattedString = new StringBuilder();

        for (var word: words) {
            if (!word.isEmpty()) {
                formattedString
                    .append(Character.toUpperCase(word.charAt(0)))
                    .append(word.substring(1).toLowerCase());

                if (!word.equals(words[words.length - 1]))
                    formattedString.append(" ");
            }
        }

        return formattedString.toString();
    }
}
