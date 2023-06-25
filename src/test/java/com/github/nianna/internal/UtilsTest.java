package com.github.nianna.internal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UtilsTest {

    @Test
    void shouldTreatOddNumbersAsOdd() {
        assertTrue(Utils.isOdd(-1));
        assertTrue(Utils.isOdd(3));
    }

    @Test
    void shouldNotTreatEvenNumbersAsOdd() {
        assertFalse(Utils.isOdd(-2));
        assertFalse(Utils.isOdd(0));
        assertFalse(Utils.isOdd(4));
    }

    @Test
    void shouldRecognizeAlphabeticText() {
        assertTrue(Utils.isAlphabetic("abcDEF"));
    }

    @Test
    void shouldRecognizeEmptyTextAsAlphabetic() {
        assertTrue(Utils.isAlphabetic(""));
    }

    @Test
    void shouldRecognizeNotAlphabeticText() {
        assertFalse(Utils.isAlphabetic("a b"));
        assertFalse(Utils.isAlphabetic("a-b"));
        assertFalse(Utils.isAlphabetic("ab12"));
    }

}