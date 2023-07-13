package io.github.nianna.api;

import io.github.nianna.TestUtil;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HyphenatorTest {

    HyphenatorProperties hyphenatorProperties = new HyphenatorProperties(1, 2);

    Hyphenator hyphenator = new Hyphenator(TestUtil.loadPlPatterns(), hyphenatorProperties);

    @Test
    void shouldHyphenateSingleToken() {
        HyphenatedText result = hyphenator.hyphenateText("aligator");
        assertEquals("a-li-ga-tor", result.read());
        List<HyphenatedToken> tokens = result.hyphenatedTokens();
        assertEquals(1, tokens.size());
        assertEquals(new HyphenatedToken("aligator", List.of(1,3,5)), tokens.get(0));
    }

    @Test
    void shouldHyphenateUsingDefaultSettings() {
        Hyphenator hyphenator = new Hyphenator(TestUtil.loadPlPatterns());
        HyphenatedText result = hyphenator.hyphenateText("aligator");
        assertEquals("ali-ga-tor", result.read());
        List<HyphenatedToken> tokens = result.hyphenatedTokens();
        assertEquals(1, tokens.size());
        assertEquals(new HyphenatedToken("aligator", List.of(3,5)), tokens.get(0));
    }

    @Test
    void shouldHyphenateSingleTokenRespectingMinPrefixAndSuffixLengths() {
        HyphenatorProperties customProperties = new HyphenatorProperties(2, 4);
        Hyphenator customizedHyphenator = new Hyphenator(TestUtil.loadPlPatterns(), customProperties);
        HyphenatedText result = customizedHyphenator.hyphenateText("aligator");
        assertEquals("ali-gator", result.read());
        List<HyphenatedToken> tokens = result.hyphenatedTokens();
        assertEquals(1, tokens.size());
        assertEquals(new HyphenatedToken("aligator", List.of(3)), tokens.get(0));
    }

    @Test
    void shouldHyphenateSingleTokenPreservingCase() {
        HyphenatedText result = hyphenator.hyphenateText("AlIgaTOr");
        assertEquals("A-lI-ga-TOr", result.read());
        List<HyphenatedToken> tokens = result.hyphenatedTokens();
        assertEquals(1, tokens.size());
        assertEquals(new HyphenatedToken("AlIgaTOr", List.of(1,3,5)), tokens.get(0));
    }

    @Test
    void shouldHyphenateMultiTokenText() {
        HyphenatedText result = hyphenator.hyphenateText("Aligator był bardzo głodny i zmęczony");
        assertEquals("A-li-ga-tor był bar-dzo głod-ny i zmę-czo-ny", result.read());
        List<HyphenatedToken> tokens = result.hyphenatedTokens();
        assertEquals(6, tokens.size());
        assertEquals(new HyphenatedToken("Aligator", List.of(1,3,5)), tokens.get(0));
        assertEquals(new HyphenatedToken("był", List.of()), tokens.get(1));
        assertEquals(new HyphenatedToken("bardzo", List.of(3)), tokens.get(2));
        assertEquals(new HyphenatedToken("głodny", List.of(4)), tokens.get(3));
        assertEquals(new HyphenatedToken("i", List.of()), tokens.get(4));
        assertEquals(new HyphenatedToken("zmęczony", List.of(3,6)), tokens.get(5));
    }

    @Test
    void shouldHyphenateMultiTokenTextWithCustomTokenSeparator() {
        Hyphenator hyphenator = new Hyphenator(TestUtil.loadPlPatterns(), hyphenatorProperties, "|");
        HyphenatedText result = hyphenator.hyphenateText("Aligator|był|bardzo|głodny");
        assertEquals("A-li-ga-tor był bar-dzo głod-ny", result.read());
        List<HyphenatedToken> tokens = result.hyphenatedTokens();
        assertEquals(4, tokens.size());
        assertEquals(new HyphenatedToken("Aligator", List.of(1,3,5)), tokens.get(0));
        assertEquals(new HyphenatedToken("był", List.of()), tokens.get(1));
        assertEquals(new HyphenatedToken("bardzo", List.of(3)), tokens.get(2));
        assertEquals(new HyphenatedToken("głodny", List.of(4)), tokens.get(3));
    }

    @Test
    void shouldHyphenateMultiTokenTextWithSpecialCharactersAtTheBeginningsAndEndsOfTokens() {
        HyphenatedText result = hyphenator.hyphenateText("Aligator (Stefan) był bardzo głodny!");
        assertEquals("A-li-ga-tor (Ste-fan) był bar-dzo głod-ny!", result.read());
        List<HyphenatedToken> tokens = result.hyphenatedTokens();
        assertEquals(5, tokens.size());
        assertEquals(new HyphenatedToken("Aligator", List.of(1,3,5)), tokens.get(0));
        assertEquals(new HyphenatedToken("(Stefan)", List.of(4)), tokens.get(1));
        assertEquals(new HyphenatedToken("był", List.of()), tokens.get(2));
        assertEquals(new HyphenatedToken("bardzo", List.of(3)), tokens.get(3));
        assertEquals(new HyphenatedToken("głodny!", List.of(4)), tokens.get(4));
    }

    @Test
    void shouldUseGivenSeparatorsForReadingHyphenatedText() {
        HyphenatedText result = hyphenator.hyphenateText("Dziewczynka znienawidziła chłopczyka");
        assertEquals(
                "Dziew_czyn_ka|znie_na_wi_dzi_ła|chłop_czy_ka",
                result.read("|", "_")
        );
    }

}
