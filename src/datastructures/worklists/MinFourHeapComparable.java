package datastructures.worklists;

import cse332.interfaces.worklists.PriorityWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/PriorityWorkList.java
 * for method specifications.
 */
public class MinFourHeapComparable<E extends Comparable<E>> extends PriorityWorkList<E> {
    /* Do not change the name of this field; the tests rely on it to work correctly. */
    private E[] data;
    private int size;

    public MinFourHeapComparable() {
        this.data = (E[]) new Comparable[10];
        this.size = 0;
    }

    @Override
    public boolean hasWork() {
        return super.hasWork();
    }

    @Override
    public void add(E work) {
        if (this.size == this.data.length) {
            resize();
        }
        this.data[this.size] = work;
        if (this.size > 0) {
            percolateUp(this.size);
        }
        this.size++;
    }

    private void percolateUp(int index) {
        while (index > 0 && this.data[index].compareTo(this.data[(index-1)/4]) < 0) {
            E tmp = this.data[index];
            this.data[index] = this.data[(index-1)/4];
            this.data[(index-1)/4] = tmp;
            index = (index-1)/4;
        }
    }

    private void resize() {
        E[] newArr = (E[]) new Comparable[this.size*2];
        for (int i = 0; i < this.size; i++) {
            newArr[i] = this.data[i];
        }
        this.data = newArr;
    }

    @Override
    public E peek() {
        if (!this.hasWork()) {
            throw new NoSuchElementException();
        }
        return this.data[0];
    }

    private void percolateDown(int parent, int child) {
        while (child != -1 && this.data[parent].compareTo(this.data[child]) > 0) {
            E temp = this.data[child];
            this.data[child] = this.data[parent];
            this.data[parent] = temp;
            parent = child;
            int firstChild = 4*parent+1;
            child = smallestChild(firstChild);
        }
    }

    private int smallestChild(int child) {
        int min;
        if (child > this.size) {
            return -1;
        }
        min = child;
        for (int i = child; i< child + 4; i++) {
            if (i < this.size && this.data[i].compareTo(this.data[min]) < 0) {
                min = i;
            }
        }
        return min;
    }

    @Override
    public E next() {
        if (!this.hasWork()) {
            throw new NoSuchElementException();
        }
        E item = this.data[0];
        this.data[0] = this.data[this.size-1];
        size--;
        percolateDown(0, smallestChild(1));
        return item;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void clear() {
        this.data = (E[]) new Comparable[10];
        this.size = 0;
    }
}
