package experiments;

import cse332.datastructures.containers.Item;
import cse332.interfaces.misc.DeletelessDictionary;
import cse332.interfaces.misc.Dictionary;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

/**
 * TODO: Replace this comment with your own as appropriate.
 * 1. You must implement a generic chaining hashtable. You may not
 *    restrict the size of the input domain (i.e., it must accept 
 *    any key) or the number of inputs (i.e., it must grow as necessary).
 * 3. Your HashTable should rehash as appropriate (use load factor as
 *    shown in class!). 
 * 5. HashTable should be able to resize its capacity to prime numbers for more 
 *    than 200,000 elements. After more than 200,000 elements, it should 
 *    continue to resize using some other mechanism.
 * 6. We suggest you hard code some prime numbers. You can use this
 *    list: http://primes.utm.edu/lists/small/100000.txt 
 *    NOTE: Do NOT copy the whole list!
 * 7. When implementing your iterator, you should NOT copy every item to another
 *    dictionary/list and return that dictionary/list's iterator. 

 */
public class ChainingHashTable<K, V> extends DeletelessDictionary<K, V> {
    private Supplier<Dictionary<K, V>> newChain;
    private Dictionary<K, V>[] array;
    private double loadFactor;
    private int sizeTracker; //Index of the sizes array
    private final int sizes[] = {23, 47, 97, 197, 397, 797, 1597, 3203, 6421, 12853, 25717, 51437, 102877, 205759};

    public ChainingHashTable(Supplier<Dictionary<K, V>> newChain) {
        this.newChain = newChain;
        this.array = new Dictionary[11]; //I used 11 here because that is the default HashTable size that is used in Java's implementation
        this.loadFactor = 0.5;
        this.sizeTracker = 0;
    }

    @Override
    public V insert(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }
        if (((double)size / array.length) >= loadFactor) {
            this.array = rehash();
        }
        int index = hash(key, array.length);
        if (array[index] == null) {
            array[index] = newChain.get();
        }
        V result = this.array[index].insert(key, value);
        if (result == null) {
            this.size++;
        }
        return result;
    }

    public int size() {
        return this.size;
    }

    public int hash(K key, int size) {
        int hashedKey = key.hashCode();
        return Math.abs(hashedKey % size);
    }

    @Override
    public V find(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        int index = hash(key, array.length);
        if (array[index] == null) {
            return null;
        } else {
            return array[index].find(key);
        }
    }

    private Dictionary<K, V>[] rehash() {
        int newSize;
        if (sizeTracker < sizes.length) {
            newSize = sizes[sizeTracker++];
        } else {
            newSize = array.length * 2;
        }
        Dictionary<K, V>[] newArr = new Dictionary[newSize];
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null) {
                for (Item<K, V> item : array[i]) {
                    int newhash = hash(item.key, newArr.length);
                        if (newArr[newhash] == null) {
                            newArr[newhash] = newChain.get();
                        }
                        newArr[newhash].insert(item.key, item.value);
                    }
                }
            }
            return newArr;
    }

    @Override
    public Iterator<Item<K, V>> iterator() {

        if (array[0] == null) {
            array[0] = newChain.get();
        }

        Iterator<Item<K,V>> it = new Iterator<>() {

            private int index = 0;

            Iterator<Item<K, V>> iterator = array[0].iterator();

            @Override
            public boolean hasNext() {
                if (index < array.length-1 && !iterator.hasNext()) {
                    if (array[index + 1] == null) {
                        index++;
                        while (array[index] == null) {
                            index++;
                            if (index >= array.length) {
                                return false;
                            }
                        }
                    } else {
                        index++;
                    }
                    if (index < array.length) {
                        iterator = array[index].iterator();
                    }
                }
               return iterator.hasNext();
            }

            @Override
            public Item<K, V> next() {
                if(!hasNext()) {
                    throw new NoSuchElementException();
                }
                return iterator.next();
            }
        };
        return it;
    }
}
