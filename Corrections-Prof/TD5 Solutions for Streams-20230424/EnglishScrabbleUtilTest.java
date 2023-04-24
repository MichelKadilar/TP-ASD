package poo2.lab5.scrabble;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnglishScrabbleUtilTest {

    @org.junit.jupiter.api.Test
    void testComputeScoreOfWord() {
        assertEquals(0, EnglishScrabbleUtil.computeScoreOfWord(""));
        assertEquals(0, EnglishScrabbleUtil.computeScoreOfWord(" "));
        assertEquals(8, EnglishScrabbleUtil.computeScoreOfWord("Hello"));
        assertEquals(29, EnglishScrabbleUtil.computeScoreOfWord("Buzzards"));
        assertEquals(87, EnglishScrabbleUtil.computeScoreOfWord("abcdefghijklmnopqrstuvwxyz"));

    }

    @org.junit.jupiter.api.Test
    void testIsWordAllowed() {
        assertTrue(EnglishScrabbleUtil.isWordAllowed("Hello"));
        assertTrue(EnglishScrabbleUtil.isWordAllowed("inspirit"));
        assertTrue(EnglishScrabbleUtil.isWordAllowed("Buzzards"));
        assertFalse(EnglishScrabbleUtil.isWordAllowed("abcdefghijklmnopqrstuvwxyz"));
    }

    @Test
    void testIsWordAvailable() {
        assertTrue(EnglishScrabbleUtil.isWordAvailable("abcdefghijklmnopqrstuvwxyz"));
        assertTrue(EnglishScrabbleUtil.isWordAvailable("hello"));
        assertFalse(EnglishScrabbleUtil.isWordAvailable("Buzzards"));
    }

    @Test
    void testNBBlankNeeded() {
        assertEquals(0, EnglishScrabbleUtil.nbBlankNeeded("abcdefghijklmnopqrstuvwxyz"));
        assertEquals(0, EnglishScrabbleUtil.nbBlankNeeded("hello"));
        assertEquals(1, EnglishScrabbleUtil.nbBlankNeeded("Buzzards"));
        assertEquals(3, EnglishScrabbleUtil.nbBlankNeeded("zazzbz"));
        assertEquals(1, EnglishScrabbleUtil.nbBlankNeeded("whizzing"));
    }

    @Test
    void testComputeScoreOfWordWithBlank() {
        assertEquals(0, EnglishScrabbleUtil.computeScoreOfWordWithBlank(""));
        assertEquals(0, EnglishScrabbleUtil.computeScoreOfWordWithBlank(" "));
        assertEquals(8, EnglishScrabbleUtil.computeScoreOfWordWithBlank("Hello"));
        assertEquals(19, EnglishScrabbleUtil.computeScoreOfWordWithBlank("Buzzards"));
        assertEquals(0, EnglishScrabbleUtil.computeScoreOfWordWithBlank("abcdefghijklmnopqrstuvwxyz"));
        assertEquals(0, EnglishScrabbleUtil.computeScoreOfWordWithBlank("zazzbz"));
        assertEquals(23, EnglishScrabbleUtil.computeScoreOfWordWithBlank("whizzing"));
    }
}