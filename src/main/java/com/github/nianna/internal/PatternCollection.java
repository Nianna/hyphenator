package com.github.nianna.internal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class PatternCollection {

    private final Map<String, List<Priority>> parsedPatterns;

    private final int maxPatternLength;

    PatternCollection(List<String> patterns) {
        parsedPatterns = patterns.stream()
                .map(this::parsePattern)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        this.maxPatternLength = parsedPatterns.keySet().stream()
                .map(String::length)
                .max(Comparator.naturalOrder())
                .orElse(0);
    }

    int getMaxPatternLength() {
        return maxPatternLength;
    }

    List<Priority> getPriorities(String identifier) {
        return parsedPatterns.get(identifier);
    }

    Stream<Priority> priorities(String identifier) {
        return Stream.ofNullable(parsedPatterns.get(identifier))
                .flatMap(List::stream);
    }

    boolean hasPattern(String identifier) {
        return parsedPatterns.containsKey(identifier);
    }

    private Map.Entry<String, List<Priority>> parsePattern(String pattern) {
        boolean isLeadPattern = pattern.startsWith(".");
        List<Priority> patternPriorities = new ArrayList<>();
        for (int i = 0; i < pattern.length(); i++) {
            if (Character.isDigit(pattern.charAt(i))) {
                int index = i - patternPriorities.size() - (isLeadPattern ? 1 : 0);
                patternPriorities.add(new Priority(index, Character.getNumericValue(pattern.charAt(i))));
            }
        }
        String identifier = pattern.replaceAll("[0-9]", "");
        return Map.entry(identifier, patternPriorities);
    }

}
