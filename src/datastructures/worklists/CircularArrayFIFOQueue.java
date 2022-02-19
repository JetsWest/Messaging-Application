package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.FixedSizeFIFOWorkList;
import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/FixedSizeFIFOWorkList.java
 * for method specifications.
 */
public class CircularArrayFIFOQueue<E extends Comparable<E>> extends FixedSizeFIFOWorkList<E> {
    private E[] array;
    private int top;
    private int bottom;
    private int size;

    public CircularArrayFIFOQueue(int capacity) {
        super(capacity);
        array = (E[])new Comparable[capacity];
        top = 0;
        bottom = 0;
        size = 0;
    }

    @Override
    public void add(E work) {
        if(isFull()) {
            throw new IllegalStateException();
        } else {
            size++;
            array[bottom] = work;
            bottom = (bottom + 1) % capacity();
        }
    }

    @Override
    public E peek() {
        if(!hasWork()) {
            throw new NoSuchElementException();
        } else {
            return array[top];
        }
    }

    @Override
    public E peek(int i) {
        if(!hasWork()) {
            throw new NoSuchElementException();
        } else if(i < 0 || i >= size()) {
            throw new IndexOutOfBoundsException();
        } else {
            return array[(top + i) % capacity()];
        }
    }

    @Override
    public E next() {
        if(!hasWork()) {
            throw new NoSuchElementException();
        } else {
            size--;
            E next = array[top];
            array[top] = null;
            top = (top + 1) % capacity();
            return next;
        }
    }

    @Override
    public void update(int i, E value) {
        if(!hasWork()) {
            throw new NoSuchElementException();
        } else if(i < 0 || i >= size()) {
            throw new IndexOutOfBoundsException();
        } else {
            array[(top + i) % capacity()] = value;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        array = (E[])new Comparable[capacity()];
        top = 0;
        bottom = 0;
        size = 0;
    }

    @Override
    public int compareTo(FixedSizeFIFOWorkList<E> other) {
        // You will implement this method in project 2. Leave this method unchanged for project 1.
        int minSize = Math.min(this.size(), other.size());
        int comparison = 0;
        for (int i = 0; i < minSize; i++) {
            comparison = this.peek(i).compareTo(other.peek(i));
            if (comparison != 0) {
                return comparison;
            }
        }
        return this.size()-other.size();
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        // You will finish implementing this method in project 2. Leave this method unchanged for project 1.
        if (this == obj) {
            return true;
        }
        else if (!(obj instanceof FixedSizeFIFOWorkList<?>)) {
            return false;
        }
        else {
            // Uncomment the line below for p2 when you implement equals
            FixedSizeFIFOWorkList<E> other = (FixedSizeFIFOWorkList<E>) obj;

            // Your code goes here
            if (other.size() != this.size()) {
                return false;
            } else {
                return (this.compareTo(other) == 0);
            }
        }
    }

    @Override
    public int hashCode() {
        int hashvalue = 17;
        for (int i = 0; i < this.size; i++) {
            hashvalue = hashvalue * 23 + this.array[i].hashCode();
        }
        return hashvalue;
    }
}
