package poo2.lab5.scrabble;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;


/**
 * EnglishScrabbleUtil is a utility class to compute the score of a word in scrabble game
 * Only checking part of the game is implemented
 * Only English version is implemented
 * Improve it to support other languages
 * Remove static fields and methods to make it more flexible
 */

public class EnglishScrabbleUtil {
    record Letter(int score, int distribution) {
    }

    // score of each letter in scrabble english game
    static final int[] scrabbleENScore = {
            //	a,	b,	c,	d,	e,	f,	g,	h,	i,	j,	k,	l,	m,	n,	o,	p,	q,	r,	s,	t,	u,	v,	w,	x,	y,	z
            1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10};

    // enable occurences of English distribution of scrabble coins
    private static final int[] scrabbleENDistrib = {
            //	a,	b,	c,	d,	e,	f,	g,	h,	i,	j,	k,	l,	m,	n,	o,	p,	q,	r,	s,	t,	u,	v,	w,	x,	y,	z
            9, 2, 2, 1, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1};

    public static final Letter[] LETTERS = new Letter[26];

    private static final String ALLOWED_WORDS_FILE = System.getProperty("user.dir")
            + System.getProperty("file.separator") + "data"
            + System.getProperty("file.separator") + "ospd.txt";

    private static final List<String> ALLOWED_WORDS;

    static {
        for (int i = 0; i < 26; i++) {
            LETTERS[i] = new Letter(scrabbleENScore[i], scrabbleENDistrib[i]);
        }
        try {
            ALLOWED_WORDS = Files.readAllLines(Paths.get(ALLOWED_WORDS_FILE));
        } catch (IOException e) {
            //too bad... @todo
            throw new RuntimeException(e);
        }
    }

    public static int distributionOf(char letter) {
        return LETTERS[letter - 'a'].distribution();
    }

    public static int scoreOf(char letter) {
        if (letter >= 'a' && letter <= 'z')
            return LETTERS[letter - 'a'].score();
        else return 0;
    }

    public static final ToIntFunction<String> computeScore =
            word -> word.toLowerCase()
                    .chars() //stream of word letters
                    .map(c -> scoreOf((char) c))
                    .sum(); //sum of all letter scores


    //Equivalent to computeScore, but not using a lambda

    public static int computeScoreOfWord(String word) {
        return
                word.toLowerCase()
                        .chars() //stream of word letters
                        //Another version
                        .map(letter -> scoreOf((char) letter))
                        .sum(); //sum of all letter scores
    }

    public static boolean isWordAllowed(String word) {
        return ALLOWED_WORDS.contains(word.toLowerCase());
    }

    public static boolean isWordAvailable(String word) {
        return  word.toLowerCase()
                .chars() //stream of word letters
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(Function.identity(),
                        Collectors.counting()))
                .entrySet().parallelStream()
                .allMatch(entry -> entry.getValue() <= distributionOf(entry.getKey()));
    }

    public static boolean isWordAllowedAndAvailable(String word){
        return isWordAllowed(word) && isWordAvailable(word);
    }


    /*------------------Part 3 : computing Score with Blanks ------------------ */
    public static Map<Character, Long> countingLetters(String word){
        return word.toLowerCase()
                .chars() //stream of word letters
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(Function.identity(),
                        (Collectors.counting())));
    }
    public static int nbBlankNeeded(String word){
        return countNbBlankNeeded(countingLetters(word));
    }
    private static final int countNbBlankNeeded(Map<Character, Long> nbLetters){
        return nbLetters.entrySet().parallelStream()
                .mapToInt(entry ->
                        (int) Math.max(entry.getValue()-distributionOf(entry.getKey()),0))
                .sum();
    }


    public static int computeScoreOfWordWithBlank(String word){
        if (!isWordAllowed(word))
            return 0;
        Map<Character, Long> nbLetters = countingLetters(word);
        if (countNbBlankNeeded(nbLetters)==0)
            return computeScoreOfWord(word);
        return nbLetters
                .entrySet().parallelStream()
                .mapToInt( entry ->
                                scoreOf(entry.getKey()) * Math.min(entry.getValue().intValue(), EnglishScrabbleUtil.distributionOf(entry.getKey()))
                                )
                .sum();
    }


    /* ------------------Test ------------------ */
    private static void printScoreOfWord(String s , int value){
        System.out.println("Score of " + s + " : " + computeScore.applyAsInt(s) + " : expected " + value);
        System.out.println("Score of " + s + " : " + computeScoreOfWord(s) + " : expected " + value);
    }
    public static void main(String[] args) {
        printScoreOfWord("Hello", 8);
        printScoreOfWord("Buzzards", 29);
        printScoreOfWord("abcdefghijklmnopqrstuvwxyz", 87);

        System.out.println(isWordAvailable("abcdefghijklmnopqrstuvwxyz"));
        System.out.println(isWordAvailable("hello"));
        System.out.println(isWordAvailable("Buzzards"));

    }

}
