package sebastianbraun.queue;

import java.util.LinkedList;
/**
 * Source code example for "A Practical Introduction to Data Structures and
 * Algorithm Analysis, 3rd Edition (Java)" by Clifford A. Shaffer Copyright
 * 2008-2011 by Clifford A. Shaffer
 */
/**
 * Linked queue implementation
 * @param <E> Element stored in queue
 */
public class ListQueue<E> implements Queue<E> {

    private E front;    // Pointer to front queue node
    private E rear;     // Pointer to rear queuenode
    int size;	        // Number of elements in queue
    LinkedList list;

    /**
     * Constructors
     */
    public ListQueue() {
        init();
    }

    public ListQueue(int size) {
        init();  // Ignore size
    }

    /**
     * Initialize queue
     */
    private void init() {
        this.list = new LinkedList();
    }

    /**
     * Reinitialize queue
     */
    @Override
    public void clear() {
        this.list.clear();
    }

    /**
     * Put element on rear
     */
    @Override
    public void enqueue(E it) {
        this.list.add(it);
    }

    /**
     * Remove and return element from front
     */
    @Override
    public E dequeue() {
        E x = (E) this.list.getFirst();
        this.list.remove();
        return x;
    }

    /**
     * @return Front element
     */
    @Override
    public E frontValue() {
        return (E) this.list.getFirst(); 
    }

    /**
     * @return Queue size 
     */
    @Override
    public int length() {
        return this.list.size(); //Remove this!! Just to fix compiler error!
    }

// Extra stuff not printed in the book.
    /**
     * Generate a human-readable representation of this queue's contents that
     * looks something like this (Front is left-most): < 1 2 3 >. This method uses toString() on the
     * individual elements.
     *
     * @return The string representation of this queue
     */
    @Override
    public String toString() {
        String output;
        output = this.list.toString().replace("[", "<");
        output = output.replace("]", ">");
        output = output.replace(",", "");
        return output;
    }
}
