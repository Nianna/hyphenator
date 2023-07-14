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

class HyphenatedTextTest {

    @Test
    void shouldReadEmptyTextIfNoTokensPresent() {
        HyphenatedText text = new HyphenatedText(List.of());
        assertEquals("", text.read());
    }

    @Test
    void shouldReadHyphenatedTextWithDefaultSeparators() {
        HyphenatedToken token1 = new HyphenatedToken("ABCDEF", List.of(1,3));
        HyphenatedToken token2 = new HyphenatedToken("ghij", List.of(1));
        HyphenatedText text = new HyphenatedText(List.of(token1, token2));
        assertEquals("A-BC-DEF g-hij", text.read());
    }

    @Test
    void shouldReadHyphenatedTextWithCustomSeparators() {
        HyphenatedToken token1 = new HyphenatedToken("ABCDEF", List.of(1,3));
        HyphenatedToken token2 = new HyphenatedToken("ghij", List.of(1));
        HyphenatedText text = new HyphenatedText(List.of(token1, token2));
        assertEquals("A_BC_DEF|g_hij", text.read("|", "_"));
    }

}
