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

package io.github.nianna.api;

import io.github.nianna.internal.HyphenIndexFinder;
import io.github.nianna.internal.Utils;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.util.Objects.nonNull;

/**
 * Hyphenates text using TeX algorithm described in Liang's thesis.
 */
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

    /**
     * @param patterns list of TeX patterns to be used for hyphenation
     * @param hyphenatorProperties hyphenator configuration
     * @param tokenSeparator separator that should be used to split text into tokens
     */
    public Hyphenator(List<String> patterns, HyphenatorProperties hyphenatorProperties, String tokenSeparator) {
        Utils.checkArgument(nonNull(hyphenatorProperties), "Properties can not be null");
        hyphenIndexFinder = new HyphenIndexFinder(patterns, hyphenatorProperties);
        Utils.checkArgument(Utils.isNotEmpty(tokenSeparator), "Token separator can not be empty");
        this.tokenSeparatorPattern = Pattern.quote(tokenSeparator);
    }

    /**
     * Splits the given text into tokens and hyphenates it.
     */
    public HyphenatedText hyphenateText(String text) {
        List<HyphenatedToken> hyphenatedTokens = tokenize(text)
                .map(this::hyphenateToken)
                .toList();
        return new HyphenatedText(hyphenatedTokens);
    }

    /**
     * Hyphenates the given token as it is.
     */
    public HyphenatedToken hyphenateToken(String token) {
        List<Integer> hyphenationIndexes = hyphenIndexFinder.findIndexes(token);
        return new HyphenatedToken(token, hyphenationIndexes);
    }

    private Stream<String> tokenize(String text) {
        return Stream.of(text.split(tokenSeparatorPattern));
    }

}
