package sebastianbraun.queue;

import java.util.Arrays;

/**
 * Source code example for "A Practical Introduction to Data Structures and
 * Algorithm Analysis, 3rd Edition (Java)" by Clifford A. Shaffer Copyright
 * 2008-2011 by Clifford A. Shaffer
 */
/**
 * Array-based queue implementation
 *
 * @param <E> Element type stored in Queue
 */
public class ArrayQueue<E> implements Queue<E> {

    private static final int DEFAULT_SIZE = 10;
    private int maxSize;         // Maximum size of queue
    private int front;           // Index of front element
    private int rear;            // Index of rear element
    private int currentSize = 0;     // Number of elements in queue
    private E[] listArray;       // Array holding queue elements

    /**
     * Constructors
     */
    public ArrayQueue() {
        this(DEFAULT_SIZE);
    }

    @SuppressWarnings("unchecked")  // For generic array
    public ArrayQueue(int size) {
        this.front = 0;
        this.rear = -1;
        this.maxSize = size;
        this.listArray = (E[]) new Object[this.maxSize];  // Create listArray
    }

    /**
     * Reinitialize
     */
    @Override
    public void clear() {
        this.front = 0;
        this.rear = -1;
        this.currentSize = 0;
    }

    /**
     * Put "it" in queue
     */
    @Override
    public void enqueue(E it) {
        if (this.currentSize == this.maxSize) {
            throw new IllegalStateException("Queue is full");
        }

        this.currentSize++;

        rear = (rear + 1) % listArray.length;
        listArray[rear] = it;
    }

    /**
     * Remove and return front value
     */
    @Override
    public E dequeue() {
        if (this.currentSize == 0) {
            throw new IllegalStateException("Queue is empty");
        }

        this.currentSize--;

        // If removing the last element, reset the queue
        if (front == rear) {
            clear();
        }

        E returnVal = listArray[front];
        front = (front + 1) % listArray.length;
        return returnVal;
    }

    /**
     * @return Front value
     */
    @Override
    public E frontValue() {
        if (this.currentSize == 0) {
            return null;
        }
        return listArray[front];
    }

    /**
     * @return Queue size
     */
    @Override
    public int length() {
        return this.currentSize;
    }

// Extra stuff not printed in the book.
    /**
     * Generate a human-readable representation of this queue's contents that
     * looks something like this (Front is left-most): < 1 2 3 >. This method
     * uses toString() on the individual elements.
     *
     * @return The string representation of this queue
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<");
        
        for (int i = 0; i < currentSize - 1; i++) {
            int index = (front + i) % maxSize;
            sb.append(listArray[index]).append(" ");
        }
        if (currentSize != 0) {
            int index = (front + length() - 1) % maxSize;
            sb.append(listArray[index]);
        }
        sb.append(">");

        return sb.toString();
    }
}
