package datastructures.worklists;

//import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.FIFOWorkList;

/**
 * See cse332/interfaces/worklists/FIFOWorkList.java
 * for method specifications.
 */
public class ListFIFOQueue<E> extends FIFOWorkList<E> {

    private int size;
    private Node<E> head;
    private Node<E> tail;

    private static class Node<E>{

        public E work;
        public Node<E> next;

        public Node(E w, Node<E> n){
            this.work = w;
            this.next = n;
        }

        public Node(E w){
            this.work = w;
        }
    }

    public ListFIFOQueue() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    @Override
    public void add(E work) {
        if (this.hasWork()){
            this.tail.next = new Node<E>(work);
            this.tail = this.tail.next;
        }else{
            this.head = new Node<E>(work);
            this.tail = this.head;
        }
        this.size++;
    }

    @Override
    public E peek() {
        if (!this.hasWork()){
            throw new java.util.NoSuchElementException();
        }
        return this.head.work;
    }

    @Override
    public E next() {
        if (!this.hasWork()) {
            throw new java.util.NoSuchElementException();
        }
        E work = this.head.work;
        if (this.head == this.tail){
            this.tail = null;
        }

        this.head = this.head.next;
        this.size--;
        return work;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void clear() {
        doClear();
    }

    private void doClear() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }
}
