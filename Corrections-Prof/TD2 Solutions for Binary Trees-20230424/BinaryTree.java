package ads.poo2.lab2.binaryTrees;

import java.util.Scanner;

/**
 * A class for simple binary nodes
 */

public class BinaryTree<T> implements BinaryTreeInterface<T> {

	private final  T data;
	private BinaryTreeInterface<T> leftBT;
	private BinaryTreeInterface<T> rightBT;


	//////////////// constructors
	/**
	 * Build a binary node which is
	 * a leaf holding the value 'getData'
	 */
	public BinaryTree(T data) {
		this(data,null,null);
	}

	/**
	 * Build a binary node holding the value 'getData' with
	 * 'leftBT' as the leftBT subtree and 'rightBT' as the rightBT subtree
	 */
	public BinaryTree(T data, BinaryTreeInterface<T> left, BinaryTreeInterface<T> right) {
		this.data = data;
		this.leftBT = left;
		this.rightBT = right;
	}
	
	//////////////// accessors
	
	public T getData() {
		return data;
	}
	
	public BinaryTreeInterface<T> left() {
		return leftBT;
	}
	
	public BinaryTreeInterface<T> right() {
		return rightBT;
	}
	
	//////////////// utility methods
	
	public boolean isLeaf() {
		return leftBT == null && rightBT == null;
	}
	
	//////////////// the example of the height: 
	//////////////// apply the same scheme to the other methods
	
	public int height() {
		return height(this);
	}
	
	private int height(BinaryTreeInterface<T> bt) {
		if ( bt == null ) {
			return -1;
		}
		return 1 + Math.max(height(bt.left()), height(bt.right()));
	}

	//////////////// methods you have to complete ////////////////
	
	//////////////// lowness ////////////////
	// The lowness of a BN is the length of one
	// shortest path from the root to a leaf
	
	public int lowness() {
		return lowness(this);
	}

	public int lowness(BinaryTreeInterface<T> t) {
		if ( t.left() == null && t.right() == null ) {
			return 0;
		}
		if ( t.left() == null ) {
			return 1 + lowness(t.right());
		}
		if ( t.right() == null ) {
			return 1 + lowness(t.left());
		}
		return 1 + Math.min(lowness(t.left()), lowness(t.right()));
	}
	
	//////////////// size ////////////////
	// The size of a BN is its number of
	// non-null nodes
	
	public int size() {
		return size(this);
	}
	
	private int size(BinaryTreeInterface<T> t) {
		if ( t == null )
			return 0;
		return 1 + size(t.left()) + size(t.right());
	}
	
	//////////////// leaves ////////////////
	// The leaves method returns the number
	// of leaves of the BN
	
	public int leaves() {
		return leaves(this);
	}
	
	private int leaves(final BinaryTreeInterface<T> t) {
		if ( t == null )
			return 0;
		if ( t.left() == null && t.right() == null )
			return 1;
		return leaves(t.left()) + leaves(t.right());
	}
	
	//////////////// isomorphic ////////////////
	// Two BN are isomorphic if they have exactly
	// the same structure, no matter the getData they
	// store
	@Override
	public boolean isomorphic(final BinaryTreeInterface<T> bt) {
		return isomorphic(this,bt);
	}
	
	private boolean isomorphic(BinaryTreeInterface<T> t1, BinaryTreeInterface<T> t2) {
		if ( t1 == null )
			return t2 == null;
		if ( t2 == null )
			return false;
		return isomorphic(t1.left(),t2.left()) && isomorphic(t1.right(),t2.right());
	}

	//////////////// balanced1 ////////////////
	// A BN is said balanced if for each node
	// (including the root node) the absolute
	// value of the difference between the height
	// of the leftBT node and the height of the
	// rightBT node is at most 1
	
	// First version: you are to use the height method

	private static final int MAX_DIFFERENCE = 1;
	public boolean balanced1() {
		return balanced1(this);
	}
	
	private boolean balanced1(BinaryTreeInterface<T> t) {
		if ( t == null )
			return true;
		return balanced1(t.left()) && balanced1(t.right()) &&
			   Math.abs(height(t.left())-height(t.right())) <= MAX_DIFFERENCE;
	}

	//////////////// balanced2 ////////////////
	// In this version, the complexity must be O(n)
	// where n is the size of the BN (the number of
	// non-null nodes)
	
	private static final int NOT_BALANCED = -2; // or whatever value < -1
	
	public boolean balanced2() {
		int r = balanced2(this);
		return r != NOT_BALANCED;
	}
	
	private int balanced2(BinaryTreeInterface<T> bt) {
		if ( bt == null )
			return -1;
		int l = balanced2(bt.left());
		if ( l == NOT_BALANCED )
			return NOT_BALANCED;
		int r = balanced2(bt.right());
		if ( r == NOT_BALANCED )
			return NOT_BALANCED;
		if ( Math.abs(l-r) > MAX_DIFFERENCE )
			return NOT_BALANCED;
		return 1 + Math.max(l, r);
	}
	
	//////////////// shapely1 ////////////////
	// A BN is said to be shapely if its height
	// is less or equal to the double of its lowness
	//And it’s true on all the subtrees


	// First version: you are to use the height and the lowness methods
	
	public boolean shapely1() {
		return shapely1(this);
	}
	
	private boolean shapely1(BinaryTreeInterface<T> t) {
		if ( t == null )
			return true;
		int height = height(t);
		int lowness = lowness(t);
		return shapely1( t.left() ) && shapely1( t.right() ) && height <= 2*lowness;
	}

	public void setLeftBT(BinaryTreeInterface<T> node2) {
		leftBT =node2;
	}

	public void setRightBT(BinaryTreeInterface<T> node3) {
		rightBT =node3;
	}

	//////////////// shapely2 ////////////////
	// In this version, the complexity must be O(n)
	// where n is the size of the BN (the number of
	// non-null nodes)
	
	private record Pair (int height, int lowness){
		public Pair() {
			this(0,0);
		}
	}
	
	private static final Pair LEAF_PAIR = new Pair();
	
	public boolean shapely2() {
		Pair p = shapely2(this);
		return p != null;
	}
	
	private Pair shapely2(BinaryTreeInterface<T> t) {
		if ( t.left() == null && t.right() == null )
			return LEAF_PAIR;
		int lowness;
		int height;
		if ( t.right() == null ) {
			Pair l = shapely2(t.left());
			if ( l == null )
				return null;
			lowness = 1 + l.lowness;
			height = 1 + l.height;
		}
		else if ( t.left() == null ) {
			Pair r = shapely2(t.right());
			if ( r == null )
				return null;
			lowness = 1 + r.lowness;
			height = 1 + r.height;
		}
		else {
			Pair l = shapely2(t.left());
			if ( l == null )
				return null;
			Pair r = shapely2(t.right());
			if ( r == null )
				return null;	
			height = 1 + Math.max(l.height, r.height);
			lowness = 1 + Math.min(l.lowness, r.lowness);
		}
		if ( height <= 2*lowness )
			return new Pair(height,lowness);
		return null;
	}
	


	//////////////// toString ////////////////

	public String toString(){
		String result;
		//Apply
		String str = this.data + "\n";
		result = str + traversePreOrderToString(this.left(),"|", makeString('_',str.length()-1) );
		result  += traversePreOrderToString(this.right(),"", "|"+makeString('_',str.length()-1) );

		return result;
	}

		private String traversePreOrderToString(final BinaryTreeInterface<T> root, String height, String branch) {
		String str;
		String strBranch = height + branch;
		if (root == null) {
			return strBranch + "\n";
		}
		int addedChar = root.getData().toString().length();
		str = strBranch + " " + root.getData() + "\n";
		if (root.isLeaf())
			return str ;
		String newHeight = height + makeString(' ',branch.length()+1 )+"|";
		String newBranch = makeString('_',addedChar);
		str+= traversePreOrderToString(root.left(),newHeight,newBranch);
		str += newHeight + "\n";
		//pour le 2e fils, on ne veut plus descendre la branche du parent
		newHeight = height + makeString(' ',branch.length()+1 );
		str += traversePreOrderToString(root.right(),newHeight,"|"+newBranch);
		return str;
	}

	protected String makeString(char c, int k) {
		return  String.valueOf(c).repeat(k);
	}

    
    ///////////////////READING a Binary Tree<String> /////////////////
    
    /**
     * Build a BN of strings from its linear prefix representation
     * "root leftBT rightBT". We use the '$' sign to mark leaves and/or
     * null subtree:
     * - X$ means that X is a leaf
     * - $  is the empty tree
     */
    public static BinaryTree<String> read(String inputString) {
		try (Scanner input = new Scanner(inputString)) {
			return read(input);
		}
	}
    
    private static BinaryTree<String> read(Scanner input) {
    	if ( ! input.hasNext() )
    		return null;
    	String s = input.next();
    	if ( "$".equals(s) )
    		return null;
    	if ( s.endsWith("$") )
    		return new BinaryTree<>(s.substring(0,s.length()-1));
    	return new BinaryTree<>(s,read(input),read(input));
    }

    /**
     * A short main for quick testing
     */
	public static void main(String[] args) {
		BinaryTree<String> bt = read("A B D X 1 10$ 20$ $ Y$ E V$ W$ C F$ G$");
		System.out.println(bt);
		if ( bt != null && bt.shapely2() )
			System.out.println("shapely");
		else
			System.out.println("not shapely");
	}
}
