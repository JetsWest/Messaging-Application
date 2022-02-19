package experiments;

import cse332.datastructures.containers.Item;
import cse332.interfaces.misc.BString;
import cse332.interfaces.misc.Dictionary;
import cse332.interfaces.trie.TrieMap;
import datastructures.worklists.ArrayStack;

import java.util.AbstractMap.SimpleEntry;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 * See cse332/interfaces/trie/TrieMap.java
 * and cse332/interfaces/misc/Dictionary.java
 * for method specifications.
 */
public class HashTrieMap<A extends Comparable<A>, K extends BString<A>, V> extends TrieMap<A, K, V> {

    public static int HTMsteps = 0;


    public class HashTrieNode extends TrieNode<Dictionary<A, HashTrieNode>, HashTrieNode> {
        public HashTrieNode() {
            this(null);
        }

        public HashTrieNode(V value) {
            HTMsteps++;
            this.pointers = new ChainingHashTableCopy<>(MoveToFrontList::new);
            this.value = value;
        }

        @Override
        public Iterator<Entry<A, HashTrieNode>> iterator() {
            HTMsteps++;
            ArrayStack<Entry<A, HashTrieNode>> arrayEntry = new ArrayStack<>();

            for (Item<A, HashTrieNode> item : this.pointers ) {
                HTMsteps++;
                arrayEntry.add(new SimpleEntry(item.key, item.value));
            }

            return arrayEntry.iterator();
        }
    }

    public HashTrieMap(Class<K> KClass) {
        super(KClass);
        this.root = new HashTrieNode();
        HTMsteps++;
    }

    @Override
    public V insert(K key, V value) {
        HTMsteps++;
        if(key == null || value == null) {
            throw new IllegalArgumentException();
        }
        HashTrieNode current = (HashTrieNode)this.root;
        for(A character : key) {
            HTMsteps++;
            if(current.pointers.find(character) == null) {
                current.pointers.insert(character, new HashTrieNode());
                HTMsteps += ((ChainingHashTableCopy) current.pointers).CHTsteps;
            }
            current = current.pointers.find(character);
            HTMsteps += ((ChainingHashTableCopy) current.pointers).CHTsteps;
            resetCount(current);
        }
        if(current.value == null) {
            size++;
        }
        V old = current.value;
        current.value = value;
        return old;
    }

    @Override
    public V find(K key) {
        HTMsteps++;
        HashTrieNode current = findHelper(key);
        if (current == null) {
            return null;
        }
        return current.value;
    }

    @Override
    public boolean findPrefix(K key) {
        HTMsteps++;
        return (findHelper(key) != null);
    }

    public void resetCount(HashTrieNode current) {
        ((ChainingHashTableCopy) current.pointers).CHTsteps = 0;
    }

    //This method is a helper method for find() and findPrefix(). It returns a HashTrieNode in order to
    //account for needing to search for a valid prefix or a valid key/value pair. The method returns null
    //if the node does not exist in the HashTrieMap.
    private HashTrieNode findHelper(K key) {
        HTMsteps++;
        if (key == null) {
            throw new IllegalArgumentException();
        }
        HashTrieNode current = (HashTrieNode) this.root;
        for (A character : key) {
            HTMsteps++;
            current = current.pointers.find(character);
            if (current == null) {
                return null;
            }
            HTMsteps += ((ChainingHashTableCopy) current.pointers).CHTsteps;
            resetCount(current);
        }
        return current;
    }

    @Override
    public void delete(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }
}
