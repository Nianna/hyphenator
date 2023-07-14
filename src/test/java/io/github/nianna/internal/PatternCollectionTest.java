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

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PatternCollectionTest {

    @Test
    void shouldParseLeadPatterns() {
        PatternCollection collection = new PatternCollection(List.of(".am5at"));
        assertTrue(collection.hasPattern(".amat"));
        assertEquals(".amat".length(), collection.getMaxPatternLength());
        List<Priority> priorities = collection.getPriorities(".amat");
        assertEquals(1, priorities.size());
        assertEquals(new Priority(2, 5), priorities.get(0));
    }

    @Test
    void shouldParseMiddlePatterns() {
        PatternCollection collection = new PatternCollection(List.of("a4m5ato"));
        assertTrue(collection.hasPattern("amato"));
        assertEquals("amato".length(), collection.getMaxPatternLength());
        List<Priority> amatoPriorities = collection.getPriorities("amato");
        assertEquals(2, amatoPriorities.size());
        assertEquals(new Priority(1, 4), amatoPriorities.get(0));
        assertEquals(new Priority(2, 5), amatoPriorities.get(1));
    }

    @Test
    void shouldParseMultiplePatterns() {
        PatternCollection collection = new PatternCollection(List.of("a4m5ato", ".am5at", ".ćwie2r2ć3"));
        assertEquals(7, collection.getMaxPatternLength());
        assertTrue(collection.hasPattern("amato"));
        assertTrue(collection.hasPattern(".amat"));
        assertTrue(collection.hasPattern(".ćwierć"));
    }

}
