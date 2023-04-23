package td3.src;

import java.util.*;

/**
 * A class for Binary Search Trees
 */
public class BinarySearchTree<T extends Comparable<? super T>> implements Iterable<T> {

    private T data;
    private BinarySearchTree<T> LT;
    private BinarySearchTree<T> RT;


    //return the value of the root of the tree
    public T getElement() {
        //to do
        return this.data;
    }


    protected BinarySearchTree<T> getLeft() {
        //to do
        return this.LT;
    }

    protected void setLeft(BinarySearchTree<T> left) {
        this.LT = left;
    }

    protected BinarySearchTree<T> getRight() {
        //to do
        return this.RT;
    }

    protected void setRight(BinarySearchTree<T> right) {
        this.RT = right;
    }


    /**
     * Construct the tree.
     */
    public BinarySearchTree(T data, BinarySearchTree<T> left, BinarySearchTree<T> right) {
        this.data = data;
        this.LT = left;
        this.RT = right;
    }

    public BinarySearchTree(T data) {
        this(data, null, null);
    }

    public BinarySearchTree() {
        this(null, null, null);
    }

    /////////////// isEmpty

    /**
     * Test if the tree is logically empty.
     *
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty() {
        return data == null && LT == null && RT == null;
    }

    /////////////// makeEmpty  

    /**
     * Make the tree logically empty.
     */
    public void makeEmpty() {
        this.data = null;
        this.LT = null;
        this.RT = null;
    }

    /////////////// contains

    /**
     * Find an item in the tree.
     *
     * @param x the item to search for.
     * @return true if not found.
     */
    public boolean contains(T x) {
        if (data == null) return false;
        int cmp = data.compareTo(x);
        if (cmp == 0) return true;
        if (!isLeaf()) {
            if (cmp < 0) return RT.contains(x);
            else return LT.contains(x);
        } else return false;
    }

    //////////////// size ////////////////
    // The size of a BN is its number of
    // non-null nodes
    public int getSize() {
        if (this.isEmpty()) return 0;
        if (LT != null && RT != null) {
            return 1 + LT.getSize() + RT.getSize();
        } else if (LT != null) {
            return 1 + LT.getSize();
        } else if (RT != null) {
            return 1 + RT.getSize();
        } else return 1;
    }

    public boolean isLeaf() {
        return ((this.LT == null || this.LT.isEmpty()) && (this.RT == null || this.RT.isEmpty()));
    }

    /////////////// insert

    /**
     * Insert into the tree; duplicates are ignored.
     *
     * @param x the item to insert.
     */
    public void insert(T x) {
        if (x == null) return;
        if (data == null) data = x;
        else {
            int cmp = data.compareTo(x);
            if (cmp == 0) return;
            if (cmp < 0) {
                if (RT != null) {
                    RT.insert(x);
                } else {
                    RT = new BinarySearchTree<>(x);
                }
            } else {
                if (LT != null) {
                    LT.insert(x);
                } else {
                    LT = new BinarySearchTree<>(x);
                }
            }
        }
    }

    /**
     * Find the smallest item in the tree.
     *
     * @return smallest item or null if empty.
     */
    public T findMin() {
        // if (isEmpty()) throw new BufferUnderflowException(); On devrait avoir ça selon la consigne...
        if (isEmpty()) return null;
        else if (this.getLeft() == null || this.getLeft().isEmpty()) {
            return data;
        } else {
            return getLeft().findMin();
        }
    }

    /////////////// findMax

    /**
     * Find the largest item in the tree.
     *
     * @return the largest item or null if empty.
     */
    public T findMax() {
        //if (isEmpty()) throw new BufferUnderflowException(); on devrait avoir ça selon la consigne...
        if (this.isEmpty()) return null;
        else if (this.getRight() == null || this.getRight().isEmpty()) {
            return data;
        } else {
            return getRight().findMax();
        }
    }

    /////////////// remove

    /**
     * Remove from the tree. Nothing is done if x is not found.
     * In the test, we replace the removed element by the largest element of the left subtree
     *
     * @param x the item to remove.
     */
    public void remove(T x) {
        //Tip : deal with removing the root of the tree in a special way
        if (x == null || this.isEmpty()) return;
        int cmp = data.compareTo(x);
        if (cmp == 0) {
            if (this.getLeft() != null && !this.getLeft().isEmpty()) {
                // on va chercher l'ancien noeud où on avait la valeur max de la branche gauche de la racine.
                data = this.getLeft().findMax();
                this.getLeft().remove(data);
            } else if (this.getRight() != null && !this.getRight().isEmpty()) {
                data = this.getRight().findMin();
                this.getRight().remove(data); // on va chercher l'ancien noeud où on avait la valeur max de la branche
                // gauche de la racine.
            } else this.makeEmpty();
        } else if (cmp < 0 && this.getRight() != null) {
            this.getRight().remove(x);
        } else if (cmp > 0 && this.getLeft() != null) {
            this.getLeft().remove(x);
        } else return;
    }

    /////////////// removeLessThan

    /**
     * Remove from the tree all the elements
     * less than min
     *
     * @param min the minimum value left in the tree
     */
    public void removeLessThan(T min) {
        if (min == null || this.isEmpty()) return;
        int cmp = data.compareTo(min);
        if (cmp == 0) {
            this.setLeft(null);
        } else if (cmp < 0) {
            if (this.getLeft() != null && this.getRight() != null) {
                this.data = this.getRight().getElement();
                this.setLeft(this.getRight().getLeft());
                this.setRight(this.getRight().getRight()); // ceci doit être fait en dernier afin de ne pas impacter le
                // "right" des autres instructions ci-dessus
                this.removeLessThan(min);
            } else if (isLeaf()) {
                this.data = null;
            } else return;
        } else {
            if (this.getLeft() != null) {
                this.getLeft().removeLessThan(min);
            } else return;
        }
    }

    /////////////// removeGreaterThan

    /**
     * Remove from the tree all the elements
     * greater than max
     *
     * @param max the maximum value left in the tree
     */
    public void removeGreaterThan(T max) {
        if (max == null || this.isEmpty()) return;
        int cmp = data.compareTo(max);
        if (cmp == 0) {
            this.setRight(null);
        } else if (cmp < 0) {
            if (this.getRight() != null) {
                this.getRight().removeGreaterThan(max);
            } else return;
        } else {
            if (this.getLeft() != null && this.getRight() != null) {
                this.data = this.getLeft().getElement();
                this.setRight(this.getLeft().getRight());
                this.setLeft(this.getLeft().getLeft()); // ceci doit être fait en dernier afin de ne pas impacter le
                // "left" des autres instructions ci-dessus
                this.removeGreaterThan(max);
            } else if (this.isLeaf()) {
                this.data = null;
            } else return;
        }
    }

    /////////////// toSortedList

    /**
     * Return a sorted list (increasing) of all
     * the elements of the tree
     *
     * @return the sorted list of all the elements of the tree
     */
    public List<T> toSortedList() {
        List<T> l = new ArrayList<>();
        if (this.data == null) return Collections.emptyList();
        if (this.getLeft() != null) {
            l.addAll(this.getLeft().toSortedList());
        }
        l.add(data);
        if (this.getRight() != null) {
            l.addAll(this.getRight().toSortedList());
        }
        return l;
    }

    /////////////// sorted list to binary search tree

    /**
     * Build a binary search tree with all the
     * elements of the list
     *
     * @param list a sorted (increasing) list of elements
     */
    public BinarySearchTree(List<T> list) {
        makeTree(list, 0, list.size() - 1);
    }

    //Usefull method to build a binary search tree  from a sorted list
    //The list is divided in two parts, the first part is used to build
    //the left subtree, the second part is used to build the right subtree
    private BinarySearchTree<T> makeTree(List<T> list, int i, int j) {
        return null;
    }


    /////////////// iterator on binary search tree


    /**
     * Return an iterator over the elements of the tree.
     * The elements are enumerated in increasing order.
     */
    public Iterator<T> iterator() {
        return new BSTiterator(this);
    }


    /**
     * Inner class to build iterator over the elements of
     * a tree
     */
    private class BSTiterator implements Iterator<T> {

        // we must push some binary nodes on the stack

        Deque<BinarySearchTree<T>> stack;


        /**
         * Build an iterator over the binary node n.
         * The elements are enumerated in increasing order.
         */
        BSTiterator(BinarySearchTree<T> n) {
            stack = new ArrayDeque<>();
            // push all the left nodes on the stack
            //TODO
        }

        /**
         * Check if there are more elements in the
         * iterator
         */
        public boolean hasNext() {
            return false;
        }

        /**
         * Return and remove the next element from
         * the iterator
         */
        public T next() {
            return null;
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
            String rs = t.getElement().toString();
            System.out.println(r + rs);
            if (t.getLeft() != null || t.getRight() != null) {
                String rr = p + '|' + makeString('_', rs.length()) + ' ';
                display(t.getLeft(), rr, p + '|' + makeString(' ', rs.length() + 1));
                System.out.println(p + '|');
                display(t.getRight(), rr, p + makeString(' ', rs.length() + 2));
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
