package com.github.nianna.internal;

import static java.util.Objects.nonNull;

public class Utils {

    private Utils() {
    }

    static boolean isAlphabetic(String input) {
        return input.codePoints().allMatch(Character::isAlphabetic);
    }

    static boolean isOdd(Integer value) {
        return value % 2 != 0;
    }

    public static void checkArgument(boolean expression, String errorMessage) {
        if (!expression) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    public static boolean isNotEmpty(String string) {
        return nonNull(string) && !string.isEmpty();
    }

}
