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

import io.github.nianna.TestUtil;
import io.github.nianna.api.HyphenatorProperties;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HyphenIndexFinderTest {

    HyphenatorProperties hyphenatorProperties = new HyphenatorProperties(1, 2);

    @Test
    void shouldReturnEmptyListIfNoPatternsProvided() {
        HyphenIndexFinder finder = new HyphenIndexFinder(List.of(), hyphenatorProperties);
        List<Integer> result = finder.findIndexes("aligator");
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldHyphenateTokenAccordingToProvidedPatterns() {
        HyphenIndexFinder finder = new HyphenIndexFinder(TestUtil.loadPlPatterns(), hyphenatorProperties);
        List<Integer> result = finder.findIndexes("aligator");
        assertEquals(List.of(1,3,5), result);
    }

    @Test
    void shouldHyphenateIgnoringCase() {
        HyphenIndexFinder finder = new HyphenIndexFinder(TestUtil.loadPlPatterns(), hyphenatorProperties);
        List<Integer> result = finder.findIndexes("ALIgaTOR");
        assertEquals(List.of(1,3,5), result);
    }

    @Test
    void shouldRespectMinPrefixAndSuffixLengths() {
        HyphenatorProperties customProperties = new HyphenatorProperties(2, 4);
        HyphenIndexFinder finder = new HyphenIndexFinder(TestUtil.loadPlPatterns(), customProperties);
        List<Integer> result = finder.findIndexes("aligator");
        assertEquals(List.of(3), result);
    }

    @Test
    void shouldHyphenateEvenWithLeadingAndTrailingNonAlphabeticCharacters() {
        HyphenIndexFinder finder = new HyphenIndexFinder(TestUtil.loadPlPatterns(), hyphenatorProperties);
        List<Integer> result = finder.findIndexes("#$%(ALIgaTOR?!)");
        assertEquals(List.of(5,7,9), result);
    }

    @Test
    void shouldHyphenateReallyLongWordsInOrder() {
        HyphenIndexFinder finder = new HyphenIndexFinder(TestUtil.loadDaPatterns(), hyphenatorProperties);
        List<Integer> result = finder.findIndexes("Trafiksikkerhedskampagneplakat");
        assertEquals(List.of(3, 6, 9, 12, 16, 22, 24, 27), result);
        // Tra-fik-sik-ker-heds-kampag-ne-pla-kat
    }

    @Test
    void shouldReturnEmptyListIfWordIsNotAlphabetic() {
        HyphenIndexFinder finder = new HyphenIndexFinder(List.of(), hyphenatorProperties);
        List<Integer> result = finder.findIndexes("(456)");
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnEmptyListIfWordHasNonAlphabeticCharactersInTheMiddle() {
        HyphenIndexFinder finder = new HyphenIndexFinder(List.of(), hyphenatorProperties);
        List<Integer> result = finder.findIndexes("ali!gator");
        assertTrue(result.isEmpty());
    }

}
