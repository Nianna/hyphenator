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

import io.github.nianna.internal.Utils;

public class HyphenatorProperties {

    public static int DEFAULT_MIN_LEADING_LENGTH = 2;

    public static int DEFAULT_MIN_TRAILING_LENGTH = 2;

    private final int minLeadingLength;

    private final int minTrailingLength;

    public HyphenatorProperties(int minLeadingLength, int minTrailingLength) {
        Utils.checkArgument(minLeadingLength > 0, "Min leading length must be at least 1");
        Utils.checkArgument(minTrailingLength > 0, "Min trailing length must be at least 1");
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
