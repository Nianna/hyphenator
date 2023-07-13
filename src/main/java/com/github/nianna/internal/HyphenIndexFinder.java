package com.github.nianna.internal;

import com.github.nianna.api.HyphenatorProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.github.nianna.internal.Utils.isAlphabetic;
import static com.github.nianna.internal.Utils.isOdd;
import static java.util.Objects.isNull;

public class HyphenIndexFinder {

    private final PatternCollection patternCollection;

    private final HyphenatorProperties hyphenatorProperties;

    public HyphenIndexFinder(List<String> patterns, HyphenatorProperties hyphenatorProperties) {
        this.patternCollection = new PatternCollection(patterns);
        this.hyphenatorProperties = hyphenatorProperties;
    }

    public List<Integer> findIndexes(String token) {
        int firstLetterIndex = getFirstLetterIndex(token);
        int lastLetterIndex = getLastLetterIndex(token, firstLetterIndex);
        String actualToken = token.substring(firstLetterIndex, lastLetterIndex + 1);
        if (actualToken.isBlank() | !isAlphabetic(actualToken)) {
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
        return getIndexesWithOddPriorities(token, maxPrioritiesAtIndexes);
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

    private Map<Integer, Integer> mergePriorities(Map<Integer, List<String>> matchedPatternsAtIndexes) {
        return matchedPatternsAtIndexes.entrySet().stream()
                .flatMap(entry ->
                        entry.getValue().stream()
                                .flatMap(patternCollection::priorities)
                                .map(priority -> Map.entry(entry.getKey() + priority.index(), priority.value()))
                ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Math::max));
    }

    private Stream<Integer> getIndexesWithOddPriorities(String token, Map<Integer, Integer> maxPrioritiesAtIndexes) {
        return maxPrioritiesAtIndexes.entrySet().stream()
                .filter(entry -> isOdd(entry.getValue()))
                .map(Map.Entry::getKey)
                .filter(index -> index <= token.length() - hyphenatorProperties.getMinTrailingLength())
                .filter(index -> index >= hyphenatorProperties.getMinLeadingLength());
    }

    private List<String> append(List<String> collector, String newValue) {
        collector = isNull(collector) ? new ArrayList<>() : collector;
        collector.add(newValue);
        return collector;
    }
}
