package ads.poo2.lab3.bstWithInnerClass;

import java.util.*;

/**
 * A class for Binary Search Trees
 */
public class BinarySearchTreeWithInnerClass<T extends Comparable<? super T>>
        implements Iterable<T> {

    // The tree root
    private BinaryNode<T> root;

    /////////////// getRoot : to be used for testing only
    protected BinaryNode<T> getRoot() {
        return root;
    }

    protected void setRoot(BinaryNode<T> root) {
        this.root = root;

    }

    /**
     * Construct the tree.
     */
    public BinarySearchTreeWithInnerClass() {
        root = null;
    }

    /////////////// isEmpty

    /**
     * Test if the tree is logically empty.
     *
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty() {
        return root == null;
    }

    /////////////// makeEmpty

    /**
     * Make the tree logically empty.
     */
    public void makeEmpty() {
        root = null;
    }


    /////////////// getSize
    public int getSize() {
        if (root == null)
            return 0;
        return root.getSize();
    }

    /////////////// contains

    /**
     * Find an item in the tree.
     *
     * @param x the item to search for.
     * @return true if not found.
     */
    public boolean contains(T x) {
        return contains(x, root);
    }

    /**
     * Internal method to find an item in a subtree.
     *
     * @param x is item to search for.
     * @param t the node that roots the subtree.
     * @return node containing the matched item.
     */
    private boolean contains(T x, BinaryNode<T> t) {
        if (t == null)
            return false;

        int compareResult = x.compareTo(t.element);

        if (compareResult < 0)
            return contains(x, t.left);
        else if (compareResult > 0)
            return contains(x, t.right);
        else
            return true; // Match
    }

    /////////////// insert

    /**
     * Insert into the tree; duplicates are ignored.
     *
     * @param x the item to insert.
     */
    public void insert(T x) {
        root = insert(x, root);
    }

    /**
     * Internal method to insert into a subtree.
     *
     * @param x the item to insert.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    protected BinaryNode<T> insert(T x, BinaryNode<T> t) {
        if (t == null)
            return new BinaryNode<>(x);

        int compareResult = x.compareTo(t.element);

        if (compareResult < 0)
            t.left = insert(x, t.left);
        else if (compareResult > 0)
            t.right = insert(x, t.right);

        return t;
    }
    /////////////// findMin

    /**
     * Find the smallest item in the tree.
     *
     * @return smallest item or null if empty.
     */
    public T findMin()  {
        if (isEmpty())
           return null;
        return findMin(root).element;
    }

    /**
     * Internal method to find the smallest item in a subtree.
     *
     * @param t the node that roots the subtree.
     * @return node containing the smallest item.
     */
    private BinaryNode<T> findMin(BinaryNode<T> t) {
        if (t.left == null)
            return t;
        return findMin(t.left);
    }

    /////////////// findMax

    /**
     * Find the largest item in the tree.
     *
     * @return the largest item of null if empty.
     */
    public T findMax() {
        if (isEmpty())
            return null;
        return findMax(root).element;
    }

    /**
     * Internal method to find the largest item in a subtree.
     *
     * @param t the node that roots the subtree.
     * @return node containing the largest item.
     */
    private BinaryNode<T> findMax(BinaryNode<T> t) {
        while (t.right != null)
            t = t.right;

        return t;
    }

    /////////////// remove

    /**
     * Remove from the tree. Nothing is done if x is not found.
     *
     * @param x the item to remove.
     */
    public void remove(T x) {
        root = remove(x, root);
    }

    /**
     * Internal method to remove from a subtree.
     *
     * @param x  the item to remove.
     * @param bt the node that roots the subtree.
     * @return the new root of the subtree.
     */
    BinaryNode<T> remove(T x, BinaryNode<T> bt) {
        if (bt == null)
            return null;   // Item not found; do nothing

        int compareResult = x.compareTo(bt.element);

        if (compareResult < 0)
            bt.left = remove(x, bt.left);
        else if (compareResult > 0)
            bt.right = remove(x, bt.right);
        else //the element is found
            if (bt.left != null && bt.right != null) // Two children
            {//replace the element with the largest element in the left subtree
                bt.element = findMax(bt.left).element;
                bt.left = remove(bt.element, bt.left);
            } else {
                bt = (bt.left != null) ? bt.left : bt.right;
            }

        return bt;
    }

    /////////////// removeLessThan

    /**
     * Remove from the tree all the elements
     * less than min
     *
     * @param min the minimum value left in the tree
     */
    public void removeLessThan(T min) {
        if (isEmpty())
            return;
        root = removeLessThan(root, min);
    }

    private BinaryNode<T> removeLessThan(BinaryNode<T> t, T min) {
        if (t == null)
            return null;
        if (t.element.compareTo(min) < 0)
            return removeLessThan(t.right, min);
        t.left = removeLessThan(t.left, min);
        return t;
    }

    /////////////// removeGreaterThan

    /**
     * Remove from the tree all the elements
     * greater than max
     *
     * @param max the maximum value left in the tree
     */
    public void removeGreaterThan(T max) {
        root = removeGreaterThan(root, max);
    }

    private BinaryNode<T> removeGreaterThan(BinaryNode<T> t, T max) {
        if (t == null)
            return null;
        if (t.element.compareTo(max) > 0)
            return removeGreaterThan(t.left, max);
        t.right = removeGreaterThan(t.right, max);
        return t;
    }

    /////////////// toSortedList

    /**
     * Return a sorted list (increasing) of all
     * the elements of the tree
     *
     * @return the sorted list of all the elements of the tree
     */
    public List<T> toSortedList() {
        List<T> list = new LinkedList<>();
        toSortedList(root, list);
        return list;
    }

    private void toSortedList(BinaryNode<T> t, List<T> list) {
        if (t != null) {
            toSortedList(t.left, list);
            list.add(t.element);
            toSortedList(t.right, list);
        }
    }

    /////////////// sorted list to binary search tree

    /**
     * Build a binary search tree with all the
     * elements of the list
     *
     * @param list a sorted (increasing) list of elements
     */
    public BinarySearchTreeWithInnerClass(List<T> list) {
        this();
        root = makeTree(list, 0, list.size() - 1);
    }

    private BinaryNode<T> makeTree(List<T> list, int i, int j) {
        if (i > j)
            return null;
        int m = (i + j) / 2;
        BinaryNode<T> t = new BinaryNode<>(list.get(m));
        t.left = makeTree(list, i, m - 1);
        t.right = makeTree(list, m + 1, j);
        return t;
    }

    /////////////// iterator on binary search tree

    /**
     * Return an iterator over the elements of the tree.
     * The elements are enumerated in increasing order.
     */
    public Iterator<T> iterator() {
        return new BSTiterator(root);
    }

    public T getElement() {
        return root.element;
    }

    public boolean isLeaf() {
        return root.left == null && root.right == null;
    }


    /**
     * Inner class to build iterator over the elements of
     * a tree
     */
    private class BSTiterator implements Iterator<T> {

        // we must push some binary nodes on the stack
        Deque<BinaryNode<T>> stack;

        /**
         * Build an iterator over the binary node n.
         * The elements are enumerated in increasing order.
         */
        BSTiterator(BinaryNode<T> n) {
            stack = new ArrayDeque<>();
            while (n != null) {
                stack.push(n);
                n = n.left;
            }
        }

        /**
         * Check if there are more elements in the
         * iterator
         */
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        /**
         * Return and remove the next element from
         * the iterator
         */
        public T next() {
            try {
                BinaryNode<T> n = stack.pop();
                T element = n.element;
                n = n.right;
                while (n != null) {
                    stack.push(n);
                    n = n.left;
                }
                return element;
            } catch (EmptyStackException e) {
                throw new NoSuchElementException(e);
            }
        }

        /**
         * Unsupported operation
         */

        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove");
        }
    }

    ////////////////////////////////////////////////////
    // Convenience method to print a tree
    ////////////////////////////////////////////////////

    public void display() {
        display(root, "", "");
    }

    private void display(BinaryNode<T> t, String r, String p) {
        if (t == null) {
            System.out.println(r);
        } else {
            String rs = t.element.toString();
            System.out.println(r + rs);
            if (t.left != null || t.right != null) {
                String rr = p + '|' + makeString('_', rs.length()) + ' ';
                display(t.left, rr, p + '|' + makeString(' ', rs.length() + 1));
                System.out.println(p + '|');
                display(t.right, rr, p + makeString(' ', rs.length() + 2));
            }
        }
    }

    private String makeString(char c, int k) {
        return String.valueOf(c).repeat(Math.max(0, k));
    }

    ////////////////////////////////////////////////////
    // Inner class BinaryNode<T>
    ////////////////////////////////////////////////////

    // Basic node stored in unbalanced binary search trees
    // Note that this class is not accessible outside
    // of this package.
    //Protected pour pouvoir être utilisé dans les tests
    protected static class BinaryNode<O> {
        // Constructors
        BinaryNode(O theElement) {
            this(theElement, null, null);
        }

        BinaryNode(O theElement, BinaryNode<O> lt, BinaryNode<O> rt) {
            element = theElement;
            left = lt;
            right = rt;
        }

        O element;            // The getData in the node
        BinaryNode<O> left;   // Left child
        BinaryNode<O> right;  // Right child

        // Accessor methods only for testing can't be accessed from outside
        O getElement() {
            return element;
        }

        protected BinaryNode<O> getLeft() {
            return left;
        }

        protected BinaryNode<O> getRight() {
            return right;
        }

        private int getSize() {
            return getSizeRecursive(this);
        }

        private int getSizeRecursive(BinaryNode<O> current) {
            if (current == null) {
                return 0;
            }
            return 1 + getSizeRecursive(current.left) + getSizeRecursive(current.right);
        }

        protected int getHeight() {
            return getHeightRecursive(this);
        }

        private int getHeightRecursive(BinaryNode<O> oBinaryNode) {
            if (oBinaryNode == null) {
                return 0;
            }
            return 1 + Math.max(getHeightRecursive(oBinaryNode.left), getHeightRecursive(oBinaryNode.right));
        }

        @Override
        public String toString() {
            return "BinaryNode{" +
                    "element=" + element +
                    ", left=" + left +
                    ", right=" + right +
                    '}';
        }

        public boolean isLeaf() {
            return left == null && right == null;
        }
    }

    ////////////////////////////////////////////////////
    // Convenience methods to build a list of integer from a string
    ////////////////////////////////////////////////////

    private static List<Integer> read(String inputString) {
        List<Integer> list = new LinkedList<>();
        Scanner input = new Scanner(inputString);
        while (input.hasNextInt())
            list.add(input.nextInt());
        input.close();
        return list;
    }

    /**
     * A short main for quick testing
     */
    public static void main(String[] args)  {
        List<Integer> list = read("50 30 70 20 40 80 60");
        BinarySearchTreeWithInnerClass<Integer> bst = new BinarySearchTreeWithInnerClass<>();
        for (Integer n : list)
            bst.insert(n);
        bst.display();
        //From the lesson
        list = Arrays.asList(12, 17, 21, 19, 14, 13, 16, 9, 11, 10, 5, 8);
        System.out.println("---- From the lesson one by one");
        bst = new BinarySearchTreeWithInnerClass<>();
        //To control the position
        for (Integer n : list) {
            bst.insert(n);
        }
        System.out.println("Before insert 3");
        bst.display();
        bst.insert(3);
        System.out.println("After insert 3");
        bst.display();
        bst.remove(17);
        System.out.println("After remove 17");
        bst.display();
        System.out.println("---- From the lesson using initialisation with Arrays.asList");
        bst = new BinarySearchTreeWithInnerClass<>(list);
        bst.display();
        bst.insert(3);
        bst.display();
        bst.remove(17);
        bst.display();
    }
}
