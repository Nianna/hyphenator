package com.github.nianna.api;

import com.github.nianna.internal.HyphenIndexFinder;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.util.Objects.isNull;

public class Hyphenator {

    private final HyphenIndexFinder hyphenIndexFinder;

    private final String tokenSeparatorPattern;

    public Hyphenator(List<String> patterns) {
        this(patterns, " ");
    }

    public Hyphenator(List<String> patterns, String tokenSeparator) {
        hyphenIndexFinder = new HyphenIndexFinder(patterns);
        if (isNull(tokenSeparator) || tokenSeparator.isEmpty()) {
            throw new IllegalArgumentException(("Token separator can not be empty"));
        }
        this.tokenSeparatorPattern = Pattern.quote(tokenSeparator);
    }

    public HyphenatedText hyphenateText(String text) {
        List<HyphenatedToken> hyphenatedTokens = tokenize(text)
                .map(this::hyphenateToken)
                .toList();
        return new HyphenatedText(hyphenatedTokens);
    }

    public HyphenatedToken hyphenateToken(String token) {
        List<Integer> hyphenationIndexes = hyphenIndexFinder.findIndexes(token);
        return new HyphenatedToken(token, hyphenationIndexes);
    }

    private Stream<String> tokenize(String text) {
        return Stream.of(text.split(tokenSeparatorPattern));
    }

}
