/*
 * hyphenator: an automatic hyphenation tool
 * Copyright (C) 2023. Weronika Pecio
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301
 * USA
 */

package io.github.nianna.internal;

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
