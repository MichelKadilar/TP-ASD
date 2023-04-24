package exemplesCode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExempleIterable<T> implements Iterable<T>{

    List<T> questions;
    public ExempleIterable(List<T> questions) {
        this.questions = questions;
    }
    public ExempleIterable() {
        this.questions = new ArrayList<>();
    }

    // we can add an add to the list method

    // here


    @Override
    public Iterator<T> iterator() {
        return questions.iterator();
    }

}
