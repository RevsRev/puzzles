package com.rev.puzzles.utils.arr;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SearchArrayTest {

    @Test
    public void testWordSearch() {

        final Character[][] grid = new Character[][]{
                {'A', 'B', 'C', 'D'},
                {'E', 'F', 'G', 'H'},
                {'I', 'J', 'K', 'L'},
                {'M', 'N', 'O', 'P'}
        };

        final SearchArray<Character> search = new SearchArray<>(grid);

        assertTrue(search.wordSearch(new Character[]{'A', 'B'}));
        assertTrue(search.wordSearch(new Character[]{'B', 'A'}));
        assertTrue(search.wordSearch(new Character[]{'A', 'B', 'C', 'D'}));
        assertTrue(search.wordSearch(new Character[]{'D', 'C', 'B', 'A'}));
        assertTrue(search.wordSearch(new Character[]{'J', 'K', 'L'}));
        assertTrue(search.wordSearch(new Character[]{'L', 'K', 'J'}));

        assertTrue(search.wordSearch(new Character[]{'A', 'E'}));
        assertTrue(search.wordSearch(new Character[]{'E', 'A'}));
        assertTrue(search.wordSearch(new Character[]{'A', 'E', 'I', 'M'}));
        assertTrue(search.wordSearch(new Character[]{'M', 'I', 'E', 'A'}));
        assertTrue(search.wordSearch(new Character[]{'H', 'L'}));
        assertTrue(search.wordSearch(new Character[]{'L', 'H'}));

        assertTrue(search.wordSearch(new Character[]{'A', 'F'}));
        assertTrue(search.wordSearch(new Character[]{'F', 'A'}));
        assertTrue(search.wordSearch(new Character[]{'A', 'F', 'K', 'P'}));
        assertTrue(search.wordSearch(new Character[]{'P', 'K', 'F', 'A'}));
        assertTrue(search.wordSearch(new Character[]{'C', 'H'}));
        assertTrue(search.wordSearch(new Character[]{'H', 'C'}));

        assertTrue(search.wordSearch(new Character[]{'E', 'B'}));
        assertTrue(search.wordSearch(new Character[]{'B', 'E'}));
        assertTrue(search.wordSearch(new Character[]{'M', 'J', 'G', 'D'}));
        assertTrue(search.wordSearch(new Character[]{'D', 'G', 'J', 'M'}));
        assertTrue(search.wordSearch(new Character[]{'K', 'H'}));
        assertTrue(search.wordSearch(new Character[]{'H', 'K'}));
    }

}