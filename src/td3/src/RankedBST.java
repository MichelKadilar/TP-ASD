package td3.src;

public class RankedBST<T extends Comparable<? super T>> implements RankedBSTInterface<T>{

    private T data;
    private BinarySearchTree<T> left;
    private BinarySearchTree<T> right;

    @Override
    public int rank(T e) {
        return 0;
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public void insert(T x) {

    }

    @Override
    public T elementInRank(int r) {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void makeEmpty() {

    }

    @Override
    public boolean contains(T i) {
        return false;
    }

    @Override
    public void remove(T i) {

    }
}
