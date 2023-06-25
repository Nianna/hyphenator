package com.github.nianna.api;

import java.util.List;
import java.util.stream.Collectors;

public record HyphenatedText(List<HyphenatedToken> hyphenatedTokens) {

    public String read() {
        return read(" ", "-");
    }

    public String read(String tokenSeparator, String syllableSeparator) {
        return hyphenatedTokens.stream()
                .map(token -> token.read(syllableSeparator))
                .collect(Collectors.joining(tokenSeparator));
    }

}
