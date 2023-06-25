package com.github.nianna.api;

import java.util.List;

public record HyphenatedToken(String token, List<Integer> hyphenIndexes) {

    public String read(String syllableSeparator) {
        StringBuilder builder = new StringBuilder();
        int lastIndex = 0;
        for (Integer index : hyphenIndexes) {
            builder.append(token, lastIndex, index);
            builder.append(syllableSeparator);
            lastIndex = index;
        }
        if (lastIndex < token.length()) {
            builder.append(token.substring(lastIndex));
        }
        return builder.toString();
    }

}
