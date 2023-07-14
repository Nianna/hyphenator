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

import java.util.List;
import java.util.stream.Collectors;

/**
 * Representation of the hyphenated text.
 *
 * Contains list of {@link HyphenatedToken}
 */
public record HyphenatedText(List<HyphenatedToken> hyphenatedTokens) {

    /**
     * Default separator used between tokens
     */
    public static final String DEFAULT_TOKEN_SEPARATOR = " ";

    /**
     * Default separator used between syllables of a single token
     */
    public static final String DEFAULT_SYLLABLE_SEPARATOR = "-";

    /**
     * @return String representation of hyphenated text. Tokens are separated by space, syllables by hyphen.
     */
    public String read() {
        return read(DEFAULT_TOKEN_SEPARATOR, DEFAULT_SYLLABLE_SEPARATOR);
    }

    /**
     * @param tokenSeparator separator to be used between tokens
     * @param syllableSeparator  separator to be used between syllables
     * @return String representation of hyphenated text using given separators
     */
    public String read(String tokenSeparator, String syllableSeparator) {
        return hyphenatedTokens.stream()
                .map(token -> token.read(syllableSeparator))
                .collect(Collectors.joining(tokenSeparator));
    }

}
