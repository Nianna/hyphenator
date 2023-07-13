package com.github.nianna.api;

import java.util.List;
import java.util.stream.Collectors;

public record HyphenatedText(List<HyphenatedToken> hyphenatedTokens) {

    public static final String DEFAULT_TOKEN_SEPARATOR = " ";

    public static final String DEFAULT_SYLLABLE_SEPARATOR = "-";

    public String read() {
        return read(DEFAULT_TOKEN_SEPARATOR, DEFAULT_SYLLABLE_SEPARATOR);
    }

    public String read(String tokenSeparator, String syllableSeparator) {
        return hyphenatedTokens.stream()
                .map(token -> token.read(syllableSeparator))
                .collect(Collectors.joining(tokenSeparator));
    }

}
