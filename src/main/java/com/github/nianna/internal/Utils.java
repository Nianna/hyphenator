package com.github.nianna.internal;

class Utils {

    private Utils() {
    }

    static boolean isAlphabetic(String input) {
        return input.codePoints().allMatch(Character::isAlphabetic);
    }

    static boolean isOdd(Integer value) {
         return value % 2 != 0;
    }
}
