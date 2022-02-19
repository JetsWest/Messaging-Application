package experiments;

import cse332.datastructures.containers.Item;
import cse332.interfaces.misc.DeletelessDictionary;
import cse332.interfaces.misc.SimpleIterator;

import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * TODO: Replace this comment with your own as appropriate.
 * 1. The list is typically not sorted.
 * 2. Add new items to the front of the list.
 * 3. Whenever find is called on an item, move it to the front of the 
 *    list. This means you remove the node from its current position 
 *    and make it the first node in the list.
 * 4. You need to implement an iterator. The iterator SHOULD NOT move
 *    elements to the front.  The iterator should return elements in
 *    the order they are stored in the list, starting with the first
 *    element in the list. When implementing your iterator, you should 
 *    NOT copy every item to another dictionary/list and return that 
 *    dictionary/list's iterator. 
 */
public class MoveToFrontList<K, V> extends DeletelessDictionary<K, V> {
    //@Override

    public static int MTFLsteps = 0;


    private ListItemNode front;

    private class ListItemNode{

        private Item<K, V> data;
        private ListItemNode next;

        public ListItemNode() {
            this(null, null);
        }

        public ListItemNode(Item<K,V> data) {
            this(data, null);
        }

        public ListItemNode(Item<K, V> data, ListItemNode next) {
            this.data = data;
            this.next = next;
            MTFLsteps++;
        }

    }

    public MoveToFrontList() {
        this(null);
        this.size = 0;
    }

    public MoveToFrontList(Item<K, V> data) {
        MTFLsteps++;
        this.front = new ListItemNode(data);
        this.size = 1;
    }

    public int getSteps() {
        return MTFLsteps;
    }

    public V insert(K key, V value) {
        MTFLsteps++;
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }
        V previous = find(key);
        if (previous != null) {
            this.front.data.value = value;
            return previous;
        } else {
            ListItemNode newNode = new ListItemNode(new Item<K, V>(key, value));
            newNode.next = this.front;
            this.front = newNode;
            this.size++;
            return null;
        }
    }

    @Override
    public V find(K key) {
        MTFLsteps++;
        if (key == null) {
            throw new IllegalArgumentException();
        }
        V res = null;
        ListItemNode current = this.front;
        ListItemNode previous = this.front;
        if (current.data != null) {
           if (current.data.key.equals(key)) {
              return current.data.value;
           }
            while (current.data != null) {
                MTFLsteps++;
                if (current.data.key.equals(key)) {
                    res = current.data.value;
                    previous.next = current.next;
                    current.next = this.front;
                    this.front = current;
                }
                previous = current;
                current = current.next;
            }
       }
       return res;
    }

    @Override
    public Iterator<Item<K, V>> iterator() {
        return new MTFLIterator();
    }

    private class MTFLIterator extends SimpleIterator<Item<K,V>> {
        private ListItemNode current;

        public MTFLIterator(){
            MTFLsteps++;
            if (MoveToFrontList.this.front != null) {
                this.current = MoveToFrontList.this.front;
            }
        }

        public boolean hasNext(){
            MTFLsteps++;
            return this.current != null && this.current.next != null;
        }

        public Item<K,V> next() {
            MTFLsteps++;
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item<K, V> currentData = current.data;
            current = current.next;
            return currentData;
        }


    }
}
