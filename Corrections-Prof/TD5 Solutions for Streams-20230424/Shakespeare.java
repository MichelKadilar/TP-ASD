package poo2.lab5.scrabble;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Shakespeare {


    /**
     * The list of words in the shakespeare file
     */
    private final List<String> shakespeareWords;

    public Shakespeare(String shakespeareFile) throws IOException {
        //this.shakespeareFile = shakespeareFile; //Not used anymore
        shakespeareWords = Files.readAllLines(Paths.get(shakespeareFile));
    }

    public Shakespeare() throws IOException {
        this(System.getProperty("user.dir")
                + System.getProperty("file.separator") + "data"
                + System.getProperty("file.separator") + "shakespeare.txt");
    }

    record Pair(String word, int number) {
    }

    /* ------------------- Cpompute the score of each word in the shakespeare file ------------------- */

    /**
     * Compute the score of each word in the shakespeare file
     * and return a map with the score as key and the list of words as value
     *
     * @return a map with the score as key and the list of words as value
     */
    public Map<Integer, List<String>> computeScoresOfWords() {
        return shakespeareWords.stream()
                .map(word -> new Pair(word, EnglishScrabbleUtil.computeScoreOfWord(word)))
                .collect(Collectors.groupingBy(Pair::number, Collectors.mapping(Pair::word, Collectors.toList())));

    }

    public Map<Integer, List<String>> computeScoresOfWordsInParrallel() {
        return shakespeareWords.parallelStream()
                .map(word -> new Pair(word, EnglishScrabbleUtil.computeScoreOfWord(word)))
                //Another way to do it using the computeScore function
                // .map(word -> new Pair(word, EnglishScrabbleUtil.computeScore.apply(word)))
                .collect(Collectors.groupingBy(Pair::number, Collectors.mapping(Pair::word, Collectors.toList())));
    }


    /* ------------------- Count the number of words by score ------------------- */

    /**
     * Count the number of words by score
     *
     * @return a map with the score as key and the number of words as value
     */
    public Map<Integer, Long> countWordsByScore() {
        return shakespeareWords.parallelStream()
                .map(EnglishScrabbleUtil::computeScoreOfWord)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    /* ------------------- Compute the words whose score is greater than a given score ------------------- */
    public List<Pair> wordsWhoseScoreGreaterThan(int score) {
        return shakespeareWords.parallelStream()
                .map(word -> new Pair(word, EnglishScrabbleUtil.computeScoreOfWord(word)))
                .filter(p -> p.number() > score)
                .toList();
    }

    /*
         Compute the words whose score is greater than a given score
         AND that are allowed
    */
    public List<Pair> wordsWhoseScoreGreaterThanAndAllowed(int score) {
        return shakespeareWords.parallelStream()
                .map(word -> new Pair(word, EnglishScrabbleUtil.computeScoreOfWord(word)))
                .filter(p -> p.number() > score)
                .filter(p -> EnglishScrabbleUtil.isWordAllowed(p.word()))
                .toList();
    }








    /* ------------------- Compute the words which are not available ------------------- */

    public Stream<String> nonAvailableWordsStream() {
        return shakespeareWords.stream()
                //.filter(word -> canBeWritten(word))
                .filter(w -> !EnglishScrabbleUtil.isWordAvailable(w));
    }

    public int countNonAvailableWords() {
        return (int) nonAvailableWordsStream().count();
    }

    public List<String> nonAvailableWords() {
        return nonAvailableWordsStream().toList();
    }

    /* ------------------- Compute the words which are available and/or Allowed------------------- */
    public Stream<String> availableWordsStream() {
        return shakespeareWords.parallelStream()
                .filter(EnglishScrabbleUtil::isWordAvailable);
    }

    public Stream<String> allowedWordsStream() {
        return shakespeareWords.parallelStream()
                .filter(EnglishScrabbleUtil::isWordAllowed);
    }

    public List<String> availableWords() {
        return availableWordsStream().toList();
    }

    public int countAvailableWords() {
        return (int) availableWordsStream().count();
    }

    public List<String> allowedWords() {
        return allowedWordsStream().toList();
    }

    public int countAllowedWords() {
        return (int) allowedWordsStream().count();
    }

    public List<String> availableAndAllowedWords() {
        return shakespeareWords.parallelStream()
                .filter(EnglishScrabbleUtil::isWordAllowed)
                .filter(EnglishScrabbleUtil::isWordAvailable)
                .toList();
    }

    /* ------------------- Compute the words which are available and/or Allowed and have a score > 20 eventually using blank------------------- */
    public List<String> availableAndAllowedWordWithGreaterScore(int score) {
        return allowedWords()
                .parallelStream()
                .filter(word -> EnglishScrabbleUtil.computeScoreOfWordWithBlank(word) > score)
                .toList();
    }


    public static void main(String[] args) throws IOException {
        Shakespeare shakespeare = new Shakespeare();

        /* ------------------ Count the number of words per score in Shakespeare's work ------------------ */
        long start = System.currentTimeMillis();
        //With 1000 iterations, the time is 2578ms with the stream and  1561ms with the parallel stream
        //for (int i = 0; i < 1000; i++) {
        shakespeare.computeScoresOfWords();
        //}
        long end = System.currentTimeMillis();
        System.out.println("Time : " + (end - start) + "ms");

        start = System.currentTimeMillis();
        //for (int i = 0; i < 1000; i++) {
        shakespeare.computeScoresOfWordsInParrallel();
        //}
        end = System.currentTimeMillis();
        System.out.println("Time with parrallel stream : " + (end - start) + "ms");



        /*-----------   Count Words by score   ------------------ */
        Map<Integer, Long> countByScore = shakespeare.countWordsByScore();
        System.out.println(countByScore);


        /* ------------------ Analyze Shakespeare vocabulary ------------------ */
        System.out.println("------------------ Analyze Shakespeare vocabulary ------------------");
        System.out.println("Non available words : ");
        //System.out.println(shakespeare.nonAvailableWords());
        int nbNonAvailable = shakespeare.countNonAvailableWords();
        System.out.println("Number of non available words : " + shakespeare.countNonAvailableWords());

        System.out.println("Available words : ");
        //System.out.println(shakespeare.availableWords());
        int nb = shakespeare.countAvailableWords();
        System.out.println("Number of available words : " + shakespeare.countAvailableWords());
        System.out.println((nb + nbNonAvailable) + "== " + shakespeare.shakespeareWords.size());

        System.out.println("Allowed words : ");
        //System.out.println(shakespeare.allowedWords());
        System.out.println("Number of allowed words : " + shakespeare.countAllowedWords());
        System.out.println("Number of non allowed words : " + (shakespeare.shakespeareWords.size() - shakespeare.countAllowedWords()));

        System.out.println("Number of words that are available and allowed : ");
        int usable = shakespeare.availableAndAllowedWords().size();
        System.out.println(usable);
        System.out.println("Number of words you cannot play with: " + (shakespeare.shakespeareWords.size() - usable));

        /* ------------------ Playing More ------------------ */

        System.out.println("Available words with a score greater than 26 : ");
        System.out.println(shakespeare.availableAndAllowedWordWithGreaterScore(26));
        System.out.println("Available words with a score greater than 24 : ");
        System.out.println(shakespeare.availableAndAllowedWordWithGreaterScore(24));
        System.out.println("Available words with a score greater than 18 : ");
        System.out.println(shakespeare.availableAndAllowedWordWithGreaterScore(18));


    }

}