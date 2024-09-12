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

package io.github.nianna.internal;

import io.github.nianna.api.HyphenatorProperties;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.isNull;

/**
 * Determines the indexes at which hyphens should be placed in the given token.
 *
 * Hyphenation is based on patterns with priorities as described in Liang's thesis.
 */
public class HyphenIndexFinder {

    private final PatternCollection patternCollection;

    private final HyphenatorProperties hyphenatorProperties;

    public HyphenIndexFinder(List<String> patterns, HyphenatorProperties hyphenatorProperties) {
        this.patternCollection = new PatternCollection(patterns);
        this.hyphenatorProperties = hyphenatorProperties;
    }

    /**
     * @param token single token to be hyphenated
     * @return list of positions in which hyphens should be placed in the original token
     */
    public List<Integer> findIndexes(String token) {
        int firstLetterIndex = getFirstLetterIndex(token);
        int lastLetterIndex = getLastLetterIndex(token, firstLetterIndex);
        String actualToken = token.substring(firstLetterIndex, lastLetterIndex + 1);
        if (actualToken.isBlank() | !Utils.isAlphabetic(actualToken)) {
            return List.of();
        }
        return doFindIndexes(actualToken)
                .map(index -> index + firstLetterIndex)
                .toList();
    }

    private Stream<Integer> doFindIndexes(String token) {
        String normalizedToken = token.toLowerCase(Locale.ROOT);
        int maxPatternLength = patternCollection.getMaxPatternLength();
        Map<Integer, List<String>> matchedPatternsAtIndexes = matchedPatternsAtIndexes(normalizedToken, maxPatternLength);
        Map<Integer, Integer> maxPrioritiesAtIndexes = mergePriorities(matchedPatternsAtIndexes);
        return getIndexesWithOddPriorities(token.length(), maxPrioritiesAtIndexes);
    }

    private int getFirstLetterIndex(String word) {
        int firstLetterIndex = 0;
        while (firstLetterIndex < word.length() && !Character.isLetter(word.charAt(firstLetterIndex))) {
            firstLetterIndex++;
        }
        return firstLetterIndex;
    }

    private int getLastLetterIndex(String word, int firstLetterIndex) {
        int lastLetterIndex = word.length() - 1;
        while (lastLetterIndex >= firstLetterIndex && !Character.isLetter(word.charAt(lastLetterIndex))) {
            lastLetterIndex--;
        }
        return lastLetterIndex;
    }

    /**
     * Computes the map of all matched patterns for each character index of the token.
     * Skips indexes for which no patterns were matched.
     *
     * @param token token to be hyphenated
     * @param maxPatternLength maximum length of patterns in the dictionary
     * @return map with list of matched patterns for each index in the token
     */
    private Map<Integer, List<String>> matchedPatternsAtIndexes(String token, int maxPatternLength) {
        Map<Integer, List<String>> result = new HashMap<>();
        for (int i = 0; i < token.length(); i++) {
            for (int j = Math.min(i + maxPatternLength, token.length() - 1); j >= i; j--) {
                String identifier = token.substring(i, j + 1);
                if (patternCollection.hasPattern(identifier)) {
                    result.compute(i, (key, value) -> append(value, identifier));
                }
                if (i == 0 && patternCollection.hasPattern("." + identifier)) {
                    result.compute(i, (key, value) -> append(value, "." + identifier));
                }
                if (j == token.length() - 1 && patternCollection.hasPattern(identifier + ".")) {
                    result.compute(i, (key, value) -> append(value, identifier + "."));
                }
            }
        }
        return result;
    }

    /**
     * Takes the map of all matched patterns beginning at the given index for each character of the token.
     * Converts it into a map containing the max priority from all patterns that matched this part of token.
     *
     * @param matchedPatternsAtIndexes map with list of matched patterns for each index in the token
     * @return map of max matched priority for each index
     */
    private Map<Integer, Integer> mergePriorities(Map<Integer, List<String>> matchedPatternsAtIndexes) {
        return matchedPatternsAtIndexes.entrySet().stream()
                .flatMap(entry ->
                        entry.getValue().stream()
                                .flatMap(patternCollection::priorities)
                                .map(priority -> Map.entry(entry.getKey() + priority.index(), priority.value()))
                ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Math::max));
    }


    /**
     * Computes the stream of indexes after which hyphens should be placed in the original token.
     *
     * As per algorithm filters out indexes with even priorities and indexes that are too close to the start or end of the token.
     *
     * @param tokenLength length of the token that is being hyphenated
     * @param maxPrioritiesAtIndexes map of max matched priority for each index
     * @return stream of indexes at which hyphens should be placed
     */
    private Stream<Integer> getIndexesWithOddPriorities(int tokenLength, Map<Integer, Integer> maxPrioritiesAtIndexes) {
        return maxPrioritiesAtIndexes.entrySet().stream()
                .filter(entry -> Utils.isOdd(entry.getValue()))
                .map(Map.Entry::getKey)
                .filter(index -> index <= tokenLength - hyphenatorProperties.minTrailingLength())
                .filter(index -> index >= hyphenatorProperties.minLeadingLength())
                .sorted();
    }

    private List<String> append(List<String> collector, String newValue) {
        collector = isNull(collector) ? new ArrayList<>() : collector;
        collector.add(newValue);
        return collector;
    }
}
