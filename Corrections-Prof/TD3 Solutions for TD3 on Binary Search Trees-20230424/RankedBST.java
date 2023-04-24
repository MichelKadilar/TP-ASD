package ads.poo2.lab3.bst2;


import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class RankedBST<T extends Comparable<? super T>> extends BinarySearchTree<T> implements ads.poo2.lab3.RankedBSTInterface<T> {
    private int leftSize = 0;

    public RankedBST(T data, RankedBST<T> left, RankedBST<T> right) {
        super(data, left, right);
    }
    public RankedBST(T data) {
        super(data);
    }

    public RankedBST() {
        super();
    }

    /**
     * Return the rank of x in the tree
     * @param e the element
     * @return the rank of x in the tree
     *  if the node is the current one, return the size of the left subtree + 1
     *  if the node is in the left subtree, return the rank of the node in the left subtree
     *  if the node is in the right subtree, return the rank of the node in the right subtree + the size of the left subtree + 1
     */
    @Override
    public int rank(T e) {
        if (isEmpty())
            return 0;
        //we are on the node we are looking for
        if (e.compareTo(getElement()) == 0)
            return leftSize + 1;
        //the node is in the left subtree
        if (e.compareTo(getElement()) < 0)
            if (getLeft() != null)
                return ((RankedBST<T>) getLeft()).rank(e);
            else
                return 0;
        //the node is in the right subtree
        if (getRight() == null)
            return 0;
        int rank = ((RankedBST<T>) getRight()).rank(e);
        if (rank == 0)
            return 0;
        return leftSize + 1 + rank;
    }

    @Override
    public int getSize() {
        if (isEmpty())
            return 0;
        if (isLeaf())
            return 1;
        if (getRight() == null)
            return 1 + leftSize;
        return 1 + leftSize + getRight().getSize();
    }

    @Override
    public RankedBST<T> createTree(T data) {
        return new RankedBST<>(data);
    }

    @Override
    public void insert(T x) {
        if (isEmpty()) {
            setElement(x);
            return;
        }
        int compareResult = x.compareTo(getElement());

        if (compareResult < 0) {
            if (getLeft() == null) {
                setLeft(createTree(x));
            } else {
                getLeft().insert(x);
            }
            leftSize++;
        } else if (compareResult > 0) {
            if (getRight() == null)
                setRight(createTree(x));
            else {
                getRight().insert(x);
            }
        }
        // Duplicate; do nothing

    }


    /**
     * Return the element of rank r in the tree
     *
     * @param r the rank
     * @return the element of rank r in the tree
     * if such element exists, null otherwise
     */
    @Override
    public T elementInRank(int r) {
        if (isEmpty())
            return null;
        if (leftSize + 1 == r)
            return getElement();
        if (r <= leftSize)
            return ((RankedBST<T>) getLeft()).elementInRank(r);
        return ((RankedBST<T>) getRight()).elementInRank(r - leftSize - 1);
    }




    ////////////////////////////////////////////////////
    // Convenience methods to build a list of integer from a string
    ////////////////////////////////////////////////////

    private static List<Integer> read(String inputString) {
        List<Integer> list = new LinkedList<Integer>();
        Scanner input = new Scanner(inputString);
        while ( input.hasNextInt() )
            list.add(input.nextInt());
        input.close();
        return list;
    }

    /**
     * A short main for quick testing
     */
  /**
     * A short main for quick testing
     */
    public static void main( String [ ] args ) {
    	List<Integer> list = read("50 30 70 20 40 80 60");
    	RankedBST<Integer> bst = new RankedBST<>();
    	for ( Integer n : list )
    		bst.insert(n);
    	bst.display();
    	System.out.println("Rank of 60: " + bst.rank(60));
    	System.out.println("Element of rank 6: " + bst.elementInRank(6));

        bst = new RankedBST<>();
        //From the lesson
        list = Arrays.asList(12, 17, 21, 19, 14, 13, 16, 9, 11, 10, 5, 8);
        System.out.println("---- From the lesson one by one");
        bst = new RankedBST<>();
        //To control the position
        for (Integer n : list) {
            bst.insert(n);
        }
        bst.display();
        System.out.println("Rank of 16: " + bst.rank(16) + " Expected: 9");
        assert bst.rank(16) == 9;
        System.out.println("Rank of 5: " + bst.rank(5) + " Expected: 1");
        assert bst.rank(5) == 1;
        System.out.println("Element of rank 12: " + bst.elementInRank(12) + " Expected: 21");
        assert bst.elementInRank(12) == 21;
    }
}
