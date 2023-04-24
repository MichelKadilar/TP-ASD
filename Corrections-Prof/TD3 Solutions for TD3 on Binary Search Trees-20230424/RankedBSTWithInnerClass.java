package ads.poo2.lab3.bstWithInnerClass;

import ads.poo2.lab3.RankedBSTInterface;

import java.util.*;

/**
 * A class for binary search tree with rank
 */
public class RankedBSTWithInnerClass<T extends Comparable<? super T>>  implements RankedBSTInterface<T> {

    // The tree root
	private BinaryNode<T> root;

    /**
     * Construct the tree.
     */
    public RankedBSTWithInnerClass( ) {
        root = null;
    }

    /////////////// isEmpty

    /**
     * Test if the tree is logically empty.
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty( ) {
        return root == null;
    }

    /////////////// makeEmpty

    /**
     * Make the tree logically empty.
     */
    public void makeEmpty( ) {
        root = null;
    }

    /////////////// size

    public int getSize() {
    	return size(root);
    }

    private int size(BinaryNode<T> t) {
    	if ( t == null )
    		return 0;
    	return 1 + t.sizeOfLeft + size(t.right);
    }
    /////////////// rank

    /**
     * Return the rank of x in the tree
     * @param x the element
     * @return the rank of x in the tree
     * if x is in the tree, 0 otherwise
     */
    public int rank(T x) {
    	return rank(root,x);
    }


    private int rank(BinaryNode<T> t, T x) {
    	if ( t == null )
    		return 0;
    	if ( x.compareTo(t.element) == 0 )
    		return t.sizeOfLeft + 1;
    	if ( x.compareTo(t.element) < 0 )
    		return rank(t.left,x);
    	int r = rank(t.right,x);
    	if ( r == 0 )
    		return 0;
    	return t.sizeOfLeft + 1 + r;
    }

    /////////////// element

    /**
     * Return the element of rank r in the tree
     * @param r the rank
     * @return the element of rank r in the tree
     * if such element exists, null otherwise
     */
    @Override
    public T elementInRank(int r) {
        System.out.println("elementInRank" + r);
        this.display();
         return element(root,r);
    }
    private T element(BinaryNode<T> t, int r) {
    	if ( t == null )
    		return null;
    	if ( t.sizeOfLeft + 1 == r )
    		return t.element;
    	if ( r <= t.sizeOfLeft )
    		return element(t.left,r);
    	return element(t.right,r - t.sizeOfLeft - 1);
    }

    /////////////// contains

    /**
     * Find an item in the tree.
     * @param x the item to search for.
     * @return true if not found.
     */
    public boolean contains( T x ) {
        return contains( x, root );
    }

    /**
     * Internal method to find an item in a subtree.
     * @param x is item to search for.
     * @param t the node that roots the subtree.
     * @return node containing the matched item.
     */
    private boolean contains(T x, BinaryNode<T> t ) {
        if( t == null )
            return false;

        int compareResult = x.compareTo( t.element );

        if( compareResult < 0 )
            return contains( x, t.left );
        else if( compareResult > 0 )
            return contains( x, t.right );
        else
            return true; // Match
    }

    /////////////// insert

    /**
     * Insert into the tree; duplicates are ignored.
     * @param x the item to insert.
     */
    public void insert( T x ) {
    	if ( ! contains(x) )
    		root = insert( x, root );
    }



    /**
     * Internal method to insert into a subtree.
     * @param x the item to insert.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private BinaryNode<T> insert(T x, BinaryNode<T> t ) {
        if( t == null )
            return new BinaryNode<>( x );

        int compareResult = x.compareTo( t.element );

        if( compareResult < 0 ) {
            t.left = insert( x, t.left );
            t.sizeOfLeft++;
        }
        else // WE KNOW THAT x IS NOT IN t
            t.right = insert( x, t.right );
        return t;
    }

    /////////////// remove

    /**
     * Remove from the tree. Nothing is done if x is not found.
     * @param x the item to remove.
     */
    public void remove( T x ) {
    	if ( contains(x) )
    		root = remove( x, root );
    }

    /**
     * Internal method to remove from a subtree.
     * @param x the item to remove.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private BinaryNode<T> remove(T x, BinaryNode<T> t ) {
    	// WE KNOW THAT x IS IN t
    	// if( t == null )
    	//   return t;   // Item not found; do nothing

        int compareResult = x.compareTo( t.element );

        if( compareResult < 0 ) {
            t.left = remove( x, t.left );
            t.sizeOfLeft--; // update the size of the left sub-tree
        }
        else if( compareResult > 0 )
            t.right = remove( x, t.right );
        else if( t.left != null && t.right != null ) // Two children
        {
            t.element = findMin( t.right ).element;
            t.right = remove( t.element, t.right );
        }
        else
            t = ( t.left != null ) ? t.left : t.right;
        return t;
    }

    /**
     * Internal method to find the smallest item in a subtree.
     * @param t the node that roots the subtree.
     * @return node containing the smallest item.
     */
    private BinaryNode<T> findMin(BinaryNode<T> t ) {
        if( t == null )
            return null;
        else if( t.left == null )
            return t;
        return findMin( t.left );
    }

	////////////////////////////////////////////////////
	// Convenience method to print a tree
	////////////////////////////////////////////////////

    public void display() {
    	display(root,"","");
    }

    private void display(BinaryNode<T> t, String r, String p) {
        if ( t == null ) {
            System.out.println(r);
        }
        else {
            String rs = t.element.toString();
            rs = "(" + t.sizeOfLeft + ")" + rs;
            System.out.println(r + rs);
            if ( t.left != null || t.right != null ) {
                String rr = p + '|' + makeString('_',rs.length()) + ' ';
                display(t.right,rr, p + '|' + makeString(' ',rs.length() + 1));
                System.out.println(p + '|');
                display(t.left,rr, p + makeString(' ',rs.length() + 2));
            }
        }
    }

    private String makeString(char c, int k) {
        String s = "";
        for ( int i = 0; i < k; i++ ) {
            s += c;
        }
        return s;
    }

	////////////////////////////////////////////////////
    // Inner class BinaryNode<T>
	////////////////////////////////////////////////////

    // Basic node stored in unbalanced binary search trees
    private static class BinaryNode<A> {
            // Constructors
        BinaryNode( A theElement ) {
            element  = theElement;
            left     = null;
            right    = null;
            sizeOfLeft = 0;
        }

        A element;            // The getData in the node
        BinaryNode<A> left;   // Left child
        BinaryNode<A> right;  // Right child

        int sizeOfLeft; // The size of the left subtree
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
    public static void main( String [ ] args ) {
    	List<Integer> list = read("50 30 70 20 40 80 60");
    	RankedBSTWithInnerClass<Integer> bst = new RankedBSTWithInnerClass<>();
    	for ( Integer n : list )
    		bst.insert(n);
    	bst.display();
    	System.out.println("Rank of 60: " + bst.rank(60));
    	System.out.println("Element of rank 6: " + bst.elementInRank(6));

        bst = new RankedBSTWithInnerClass<>();
        //From the lesson
        list = Arrays.asList(12, 17, 21, 19, 14, 13, 16, 9, 11, 10, 5, 8);
        System.out.println("---- From the lesson one by one");
        bst = new RankedBSTWithInnerClass<>();
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
