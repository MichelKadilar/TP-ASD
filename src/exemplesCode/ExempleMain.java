package exemplesCode;

import java.util.Iterator;

public class ExempleMain {
    public static void main(String[] args) {
        // Iterable
        ExempleIterable<ObjetMichel> quiz = new ExempleIterable<>();
        quiz.questions.add(new ObjetMichel());
        for (ObjetMichel q : quiz) {
            System.out.println(q.n);
        }

        // Iterator
        Iterator<Integer> iteratorOnPrimeNumbers = ExempleIterator.primeIterator();
        iteratorOnPrimeNumbers.forEachRemaining(System.out::println);
        iteratorOnPrimeNumbers = ExempleIterator.primeIterator();
        while (iteratorOnPrimeNumbers.hasNext()) {
            System.out.println(iteratorOnPrimeNumbers.next());
        }
    }
}
