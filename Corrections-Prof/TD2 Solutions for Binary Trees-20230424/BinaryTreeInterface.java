package ads.poo2.lab2.binaryTrees;

public interface BinaryTreeInterface<T> {


    T getData();

    BinaryTreeInterface<T> left();

    BinaryTreeInterface<T> right();

    void setLeftBT(BinaryTreeInterface<T> node);

    void setRightBT(BinaryTreeInterface<T> node);

    boolean isLeaf();

    int height();

    int lowness();

    int size();

    int leaves();

    boolean isomorphic(BinaryTreeInterface<T> t);

    boolean balanced1();

    boolean balanced2();

    boolean shapely1();


}
