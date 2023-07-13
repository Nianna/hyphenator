package com.github.nianna.internal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    @Test
    void shouldThrowIllegalArgumentExceptionIfConditionFails() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> Utils.checkArgument(false, "error message")
        );
        assertEquals("error message", exception.getMessage());
    }

    @Test
    void shouldNotThrowAnyExceptionsIfConditionPasses() {
        assertDoesNotThrow(
                () -> Utils.checkArgument(true, "error message")
        );
    }

    @Test
    void shouldRecognizeNullStringAsEmpty() {
        assertFalse(Utils.isNotEmpty(null));
    }

    @Test
    void shouldRecognizeEmptyStringAsEmpty() {
        assertFalse(Utils.isNotEmpty(""));
    }

    @Test
    void shouldRecognizeNotEmptyStringAsNotEmpty() {
        assertTrue(Utils.isNotEmpty("a"));
    }

    @Test
    void shouldRecognizeBlankStringAsNotEmpty() {
        assertTrue(Utils.isNotEmpty(" "));
    }

}
