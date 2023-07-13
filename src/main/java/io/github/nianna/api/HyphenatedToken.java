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
