package com.sivasuryap.miniTwitter.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ParseHelper {

    private static final String DATE_TIME_PATTERN = "dd-MM-yyyy HH:mm";
    private static final String EMPTY_PLACEHOLDER = "-";

    private ParseHelper() {
    }

    public static String formatDateTime(long millis) {
        return new SimpleDateFormat(DATE_TIME_PATTERN, Locale.ROOT).format(new Date(millis));
    }

    public static Integer parsePositiveInt(String input) {
        if (input == null) return null;
        String trimmed = input.trim();
        if (trimmed.isEmpty()) return null;
        long result = 0L;
        for (int i = 0; i < trimmed.length(); i++) {
            char c = trimmed.charAt(i);
            if (c < '0' || c > '9') return null;
            result = result * 10 + (c - '0');
            if (result > Integer.MAX_VALUE) return null;
        }
        int val = (int) result;
        return val > 0 ? val : null;
    }

    public static String orDash(String value) {
        if (value == null || value.trim().isEmpty()) return EMPTY_PLACEHOLDER;
        return value.trim();
    }
}
