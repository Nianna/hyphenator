package io.github.nianna.api;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HyphenatedTextTest {

    @Test
    void shouldReadEmptyTextIfNoTokensPresent() {
        HyphenatedText text = new HyphenatedText(List.of());
        assertEquals("", text.read());
    }

    @Test
    void shouldReadHyphenatedTextWithDefaultSeparators() {
        HyphenatedToken token1 = new HyphenatedToken("ABCDEF", List.of(1,3));
        HyphenatedToken token2 = new HyphenatedToken("ghij", List.of(1));
        HyphenatedText text = new HyphenatedText(List.of(token1, token2));
        assertEquals("A-BC-DEF g-hij", text.read());
    }

    @Test
    void shouldReadHyphenatedTextWithCustomSeparators() {
        HyphenatedToken token1 = new HyphenatedToken("ABCDEF", List.of(1,3));
        HyphenatedToken token2 = new HyphenatedToken("ghij", List.of(1));
        HyphenatedText text = new HyphenatedText(List.of(token1, token2));
        assertEquals("A_BC_DEF|g_hij", text.read("|", "_"));
    }

}
