package com.github.nianna.api;

import static com.github.nianna.internal.Utils.checkArgument;

public class HyphenatorProperties {

    public static int DEFAULT_MIN_PREFIX_LENGTH = 2;

    public static int DEFAULT_MIN_SUFFIX_LENGTH = 2;

    private final int minPrefixLength;

    private final int minSuffixLength;

    public HyphenatorProperties(int minPrefixLength, int minSuffixLength) {
        checkArgument(minPrefixLength > 0, "Prefix must be at least 1 character long");
        checkArgument(minSuffixLength > 0, "Suffix must be at least 1 character long");
        this.minPrefixLength = minPrefixLength;
        this.minSuffixLength = minSuffixLength;
    }

    public HyphenatorProperties() {
        this(DEFAULT_MIN_PREFIX_LENGTH, DEFAULT_MIN_SUFFIX_LENGTH);
    }

    public int getMinPrefixLength() {
        return minPrefixLength;
    }

    public int getMinSuffixLength() {
        return minSuffixLength;
    }

}
