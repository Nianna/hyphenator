package com.github.nianna.internal;

import com.github.nianna.TestUtil;
import com.github.nianna.api.HyphenatorProperties;
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
