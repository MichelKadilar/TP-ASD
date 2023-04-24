package ads.poo2.lab3.bst2;

import java.util.*;

/**
 * A class for Binary Search Trees
 */
public class BinarySearchTree<T extends Comparable<? super T>> implements Iterable<T> {

    private T element;
    private BinarySearchTree<T> left;
    private BinarySearchTree<T> right;

    public T getElement() {
        return element;
    }
    protected void setElement(T data) {
        element = data;
    }

    public BinarySearchTree<T> getLeft() {
        return left;
    }

    public void setLeft(BinarySearchTree<T> left) {
        this.left = left;
    }

    public BinarySearchTree<T> getRight() {
        return right;
    }

    public void setRight(BinarySearchTree<T>right) {
        this.right = right;
    }

    /**
     * Construct the tree.
     */
    public BinarySearchTree(T data, BinarySearchTree<T> left, BinarySearchTree<T> right) {
        this.element = data;
        this.left = left;
        this.right = right;
    }

    public BinarySearchTree(T data) {
        this(data, null, null);
    }

    public BinarySearchTree() {
        this(null, null, null);
    }


    public boolean isEmpty() {
        return element == null;
    }

    public void makeEmpty() {
        element = null;
        left = null;
        right = null;
    }

    /////////////// getSize

    public int getSize() {
        if (isEmpty())
            return 0;
        if (isLeaf())
            return 1;
        if (left == null)
            return 1 + right.getSize();
        if (right == null)
            return 1 + left.getSize();
        return 1 + left.getSize() + right.getSize();
    }


    public boolean isLeaf() {
        return ((left == null || left.isEmpty()) &&
                (right == null || right.isEmpty()));
    }


    /////////////// contains

    /**
     * Find an item in the tree.
     *
     * @param x the item to search for.
     * @return true if not found.
     */

    public boolean contains(T x) {
        if (x == null || isEmpty())
            return false;
        int compareResult = x.compareTo(this.element);

        if (compareResult == 0)
            return true;
        if (compareResult < 0 && left != null)
            return left.contains(x);
        else if (compareResult > 0 && right != null)
            return right.contains(x);
        else
            return false; // Not found
    }

    /////////////// insert

    public BinarySearchTree<T> createTree(T x) {
        return new BinarySearchTree<>(x);
    }

    /**
     * Insert into the tree; duplicates are ignored.
     *
     * @param x the item to insert.
     */

    public void insert(T x) {
        if (isEmpty()) {
            element = x;
            return;
        }
        int compareResult = x.compareTo(element);

        if (compareResult < 0) {
            if (left == null)
                left = createTree(x);
            else
                left.insert(x);
        } else if (compareResult > 0) {
            if (right == null)
                right = createTree(x);
            else
                right.insert(x);
        }
        // Duplicate; do nothing

    }

    /////////////// findMin

    /**
     * Find the smallest item in the tree.
     *
     * @return smallest item or null if empty.
     */
    public T findMin() {
        if (left == null || left.isEmpty())
            return element;
        return left.findMin();
    }

    /////////////// findMax

    /**
     * Find the largest item in the tree.
     *
     * @return the largest item of null if empty.
     */
    public T findMax() {
        if (right == null || right.isEmpty())
            return element;
        return right.findMax();
    }

    protected int getHeight() {
        if (isEmpty())
            return 0;
        if (isLeaf())
            return 1;
        if (left == null)
            return 1 + right.getHeight();
        if (right == null)
            return 1 + left.getHeight();
        return 1 + Math.max(left.getHeight(), right.getHeight());
    }


    /////////////// remove

    /**
     * Remove from the tree. Nothing is done if x is not found.
     *
     * @param x the item to remove.
     */

    public void remove(T x) {
        int compareResult = x.compareTo(element);
        if (compareResult == 0) {
            removeRoot();
        } else if (compareResult < 0 && left != null)
            left.remove(x);
        else if (compareResult > 0 && right != null)
            right.remove(x);
    }

    //If we want to remove the root of the tree
    //take care we now can have empty node in the tree !!
    private void removeRoot() {
        if (isLeaf()) {
            makeEmpty();
            return;
        }
        //the element has only one child
        if (left == null) {
            element = right.element;
            left = right.left;
            right = right.right;
            return;
        }
        //the element has only one child
        if (right == null) {
            element = left.element;
            right = left.right;
            left = left.left;
            return;
        }
        //the element has two children
        T max = left.findMax();//we can also use right.findMin()
        if (max == null) {
            assert false : "Error in removeRoot no max found";
            makeEmpty();
            return;
        }
        element = max;
        // Could be improved avoiding to search again : try with a findMaxAndRemove that returns the node and remove it
        left.remove(max);//or right.remove(min);
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
        // If the root is less than min, we remove the root and all the left subtree
        if (element.compareTo(min) < 0) {
            element = right.element;
            left = right.left;
            right = right.right;
            removeLessThan(min);
            return;
        }
        if (left == null)
            return;
        // If the root is greater than min, we remove all the elements less than min in the left subtree
        left.removeLessThan(min);
    }

    /////////////// removeGreaterThan

    /**
     * Remove from the tree all the elements
     * greater than max
     *
     * @param max the maximum value left in the tree
     */
    public void removeGreaterThan(T max) {
        if (isEmpty())
            return;

        // If the root is greater than max, we remove the root and all the right subtree
        if (element.compareTo(max) > 0) {
            right = null;
            removeRoot();
            removeGreaterThan(max);
            return;
        }
        // If the root is less than max, we remove all the elements greater than max in the right subtree
        if (right == null)
            return;
        right.removeGreaterThan(max);
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
        return this.toSortedList(list);
    }

    private List<T> toSortedList(List<T> list) {
        if (isEmpty())
            return list;
        if (left != null) {
            list = left.toSortedList(list);
        }
        list.add(element);
        if (right != null) {
            list = right.toSortedList(list);
        }
        return list;
    }

    /////////////// sorted list to binary search tree

    /**
     * Build a binary search tree with all the
     * elements of the list
     *
     * @param list a sorted (increasing) list of elements
     */
    public BinarySearchTree(List<T> list) {
        BinarySearchTree<T> bst = makeTree(list, 0, list.size() - 1);
        this.element = bst.element;
        this.left = bst.left;
        this.right = bst.right;
    }

    private BinarySearchTree<T> makeTree(List<T> list, int i, int j) {
        if (i > j)
            return null;
        int m = (i + j) / 2;
        BinarySearchTree<T> t = new BinarySearchTree<>(list.get(m));
        t.left = makeTree(list, i, m - 1);
        t.right = makeTree(list, m + 1, j);
        return t;
    }

    /////////////// primeIterator on binary search tree

    /**
     * Return an primeIterator over the elements of the tree.
     * The elements are enumerated in increasing order.
     */
    @Override
    public Iterator<T> iterator() {
        return new BSTiterator(this);
    }


    /**
     * Inner class to build primeIterator over the elements of
     * a tree
     */
    private class BSTiterator implements Iterator<T> {

        // we must push some binary nodes on the stack
        Deque<BinarySearchTree<T>> stack;

        /**
         * Build an primeIterator over the binary node n.
         * The elements are enumerated in increasing order.
         */
        BSTiterator(BinarySearchTree<T> n) {
            stack = new ArrayDeque<>();
            while (n != null && !n.isEmpty()) {
                stack.push(n);
                n = n.left;
            }
        }

        /**
         * Check if there are more elements in the
         * tree
         */
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        /**
         * Return and remove the next element
         */
        public T next() {
            try {
                BinarySearchTree<T> bst = stack.pop();
                T value = bst.element;
                bst = bst.right;
                while (bst != null) {
                    stack.push(bst);
                    bst = bst.left;
                }
                return value;
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
        display(this, "", "");
    }

    private void display(BinarySearchTree<T> t, String r, String p) {
        if (t == null || t.isEmpty()) {
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
    public static void main(String[] args) {
        List<Integer> list = read("50 30 70 20 40 80 60");
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        for (Integer n : list)
            bst.insert(n);
        bst.display();
        //From the lesson
        list = Arrays.asList(12, 17, 21, 19, 14, 13, 16, 9, 11, 10, 5, 8);
        System.out.println("---- From the lesson one by one");
        bst = new BinarySearchTree<>();
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
        bst = new BinarySearchTree<>(list);
        bst.display();
        bst.insert(3);
        bst.display();
        bst.remove(17);
        bst.display();
    }
}
