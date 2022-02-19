package datastructures.worklists;

//import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.LIFOWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/LIFOWorkList.java
 * for method specifications.
 */
public class ArrayStack<E> extends LIFOWorkList<E> {
    private int capacity;
    private E[] array;
    private int top;

    public ArrayStack() {
        capacity = 10;
        array = (E[])new Object[capacity];
        top = -1;
    }

    @Override
    public void add(E work) {
        top++;
        if(top == capacity - 1) {
            capacity *= 2;
            E newArray[] = (E[])new Object[capacity];
            for (int i =0; i < capacity / 2; i++) {
                newArray[i] = array[i];
            }
            array = newArray;
        }
        array[top] = work;
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
    public E next() {
        if(!hasWork()) {
            throw new NoSuchElementException();
        } else {
            E next = array[top];
            array[top] = null;
            top--;
            return next;
        }
    }

    @Override
    public int size() {
        return top + 1;
    }

    @Override
    public void clear() {
        capacity = 10;
        array = (E[])new Object[capacity];
        top = -1;
    }
}
