package com.github.nianna.api;

import com.github.nianna.internal.HyphenIndexFinder;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static com.github.nianna.internal.Utils.checkArgument;
import static com.github.nianna.internal.Utils.isNotEmpty;
import static java.util.Objects.nonNull;

public class Hyphenator {

    public static final String DEFAULT_TOKEN_SEPARATOR = " ";

    private final HyphenIndexFinder hyphenIndexFinder;

    private final String tokenSeparatorPattern;

    public Hyphenator(List<String> patterns) {
        this(patterns, new HyphenatorProperties());
    }

    public Hyphenator(List<String> patterns, HyphenatorProperties hyphenatorProperties) {
        this(patterns, hyphenatorProperties, DEFAULT_TOKEN_SEPARATOR);
    }

    public Hyphenator(List<String> patterns, HyphenatorProperties hyphenatorProperties, String tokenSeparator) {
        checkArgument(nonNull(hyphenatorProperties), "Properties can not be null");
        hyphenIndexFinder = new HyphenIndexFinder(patterns, hyphenatorProperties);
        checkArgument(isNotEmpty(tokenSeparator), "Token separator can not be empty");
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
