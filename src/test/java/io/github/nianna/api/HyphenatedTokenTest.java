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

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HyphenatedTokenTest {

    @Test
    void shouldReturnSameTokenIfNoHyphenIndexesProvided() {
        HyphenatedToken token = new HyphenatedToken("AbCDeF", List.of());
        assertEquals("AbCDeF", token.read("-"));
    }

    @Test
    void shouldReturnSameOneLetterTokenIfNoIndexProvided() {
        HyphenatedToken token = new HyphenatedToken("A", List.of());
        assertEquals("A", token.read("$"));
    }

    @Test
    void shouldPlaceSeparatorAtGivenIndexes() {
        HyphenatedToken token = new HyphenatedToken("ABCDEF", List.of(1,3));
        assertEquals("A-BC-DEF", token.read("-"));
    }

}
