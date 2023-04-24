package poo2.lab5.scrabble;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

class ShakespeareTest {

    Logger log = LoggerFactory.getLogger(ShakespeareTest.class);
    Shakespeare shakespeare = new Shakespeare();

    ShakespeareTest() throws IOException {
    }

    long testAndTime(Supplier<?> f)  {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            f.get();
        }
        long end = System.currentTimeMillis();
        long diff = end - start;
        log.info(()->"Time : " + diff + "ms");
        return diff;
    }

    private void testComputeScoresOfWords(Supplier<Map<Integer,List<String>>> f) {
        Map<Integer, List<String>> res = f.get();
        log.debug(() -> "res = " + res);
        assertEquals(2, res.get(40).size());
        assertEquals(1, res.get(36).size());
    }

    @Test
    void testComputeScoresOfWords()  {
        testComputeScoresOfWords(shakespeare::computeScoresOfWords);
    }

    @Test
    void testComputeScoresOfWordsInParrallel() {
        testComputeScoresOfWords(shakespeare::computeScoresOfWordsInParrallel);
    }
    @Test
    void comparePerformance() throws IOException {
        Shakespeare shakespeare = new Shakespeare();
        long time1 = testAndTime(shakespeare::computeScoresOfWords);
        long time2 = testAndTime(shakespeare::computeScoresOfWordsInParrallel);
        assertTrue(time2 < time1);
    }


    @Test
    void testCountWordsByScore()  {
        Map<Integer, Long> res = shakespeare.countWordsByScore();
        assertEquals(2, res.get(40).longValue());
        assertEquals(1, res.get(36).longValue());
    }
    @Test
    void testwordsWhoseScoreGreaterThan() {
        List<Shakespeare.Pair> res = shakespeare.wordsWhoseScoreGreaterThan(36);
        log.debug(() -> "res = " + res);
        assertTrue(res.contains(new Shakespeare.Pair("Nebuchadnezzar", 40)));
        assertTrue(res.contains(new Shakespeare.Pair("honorificabilitudinitatibus", 40)));
        List<Shakespeare.Pair> res2 = shakespeare.wordsWhoseScoreGreaterThan(30);
        log.debug(() -> "res = " + res2);
        assertTrue(res2.contains(new Shakespeare.Pair("Nebuchadnezzar", 40)));
        assertTrue(res2.contains(new Shakespeare.Pair("exchequers", 31)));
    }

    @Test
    void testwordsWhoseScoreGreaterThanAndAllowed() {
        List<Shakespeare.Pair> res = shakespeare.wordsWhoseScoreGreaterThanAndAllowed(26);
        log.debug(() -> "res = " + res);
        assertFalse(res.contains(new Shakespeare.Pair("Nebuchadnezzar", 40)));
        assertFalse(res.contains(new Shakespeare.Pair("honorificabilitudinitatibus", 40)));
        assertTrue(res.contains(new Shakespeare.Pair("buzzards", 29)));
    }

    @Test
    void testCountNonAvailableWords() {
        int res = shakespeare.countNonAvailableWords();
        assertEquals(1107, res);
        List<String> list = shakespeare.nonAvailableWords();
        assertTrue(list.contains("buzzards"));
        assertFalse(list.contains("Jezebel"));
        assertEquals(1107,list.size());
    }

    @Test
    void testCountAvailableWords() {
        int nb = shakespeare.countAvailableWords();
        assertEquals(28059, nb);
        List<String> list = shakespeare.availableWords();
        assertFalse(list.contains("buzzards"));
        assertTrue(list.contains("Jezebel"));
        assertEquals(28059,list.size());
    }

    @Test
    void testAllowedWords() {
        assertEquals(18417, shakespeare.allowedWords().size());
        assertEquals(18417, shakespeare.countAllowedWords());
    }

    @Test
    void testAvailableAndAllowedWords() {
        int usable = shakespeare.availableAndAllowedWords().size();
        assertEquals(17817, usable);
    }

    @Test
    void testScoreOfShakespeareWords() {
        List<String> res = shakespeare.availableAndAllowedWordWithGreaterScore(24);
        assertEquals(5, res.size());
        res = shakespeare.availableAndAllowedWordWithGreaterScore(24);
        assertEquals(5, res.size());
        assertTrue(res.contains("Jezebel"));
        assertTrue(res.contains("squeezes"));
        res = shakespeare.availableAndAllowedWordWithGreaterScore(18);
        assertEquals(183, res.size());
        assertTrue(res.contains("buzzards"));
        assertFalse(res.contains("exchequers"));
        assertFalse(res.contains("honorificabilitudinitatibus"));
        assertTrue(res.contains("Jezebel"));
        assertTrue(res.contains("squeezes"));
    }
}