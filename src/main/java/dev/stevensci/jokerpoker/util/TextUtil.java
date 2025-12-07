package dev.stevensci.jokerpoker.util;

import java.util.ArrayList;
import java.util.List;

public class TextUtil {

    public static String[] wrap(String text, int maxLength) {
        if (text == null || text.isEmpty()) return new String[0];

        List<String> lines = new ArrayList<>();
        StringBuilder current = new StringBuilder();

        for (String word : text.split("\\s+")) {
            if (current.length() + word.length() > maxLength) {
                lines.add(current.toString());
                current.setLength(0);
            }

            if (!current.isEmpty()) current.append(" ");

            current.append(word);
        }

        if (!current.isEmpty()) lines.add(current.toString());

        return lines.toArray(new String[0]);
    }

}
