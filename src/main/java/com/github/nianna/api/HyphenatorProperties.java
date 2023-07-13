package com.github.nianna.api;

import static com.github.nianna.internal.Utils.checkArgument;

public class HyphenatorProperties {

    public static int DEFAULT_MIN_LEADING_LENGTH = 2;

    public static int DEFAULT_MIN_TRAILING_LENGTH = 2;

    private final int minLeadingLength;

    private final int minTrailingLength;

    public HyphenatorProperties(int minLeadingLength, int minTrailingLength) {
        checkArgument(minLeadingLength > 0, "Min leading length must be at least 1");
        checkArgument(minTrailingLength > 0, "Min trailing length must be at least 1");
        this.minLeadingLength = minLeadingLength;
        this.minTrailingLength = minTrailingLength;
    }

    public HyphenatorProperties() {
        this(DEFAULT_MIN_LEADING_LENGTH, DEFAULT_MIN_TRAILING_LENGTH);
    }

    public int getMinLeadingLength() {
        return minLeadingLength;
    }

    public int getMinTrailingLength() {
        return minTrailingLength;
    }

}
