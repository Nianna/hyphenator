package com.github.nianna.api;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HyphenatedTokenTest {

    @Test
    void shouldReturnSameTokenIfNoHyphenIndexesProvided() {
        HyphenatedToken token = new HyphenatedToken("AbCDeF", List.of());
        assertEquals("AbCDeF", token.read("-"));
    }

    @Test
    void shouldReturnSameOneLetterTokenIfNoIndexProvided() {
        HyphenatedToken token = new HyphenatedToken("A", List.of());
        assertEquals("A", token.read("$"));
    }

    @Test
    void shouldPlaceSeparatorAtGivenIndexes() {
        HyphenatedToken token = new HyphenatedToken("ABCDEF", List.of(1,3));
        assertEquals("A-BC-DEF", token.read("-"));
    }

}