package datastructures.dictionaries;

import java.util.*;
import java.util.Map.Entry;

//import cse332.exceptions.NotYetImplementedException;
import cse332.datastructures.containers.Item;
import cse332.interfaces.misc.BString;
import cse332.interfaces.misc.Dictionary;
import cse332.interfaces.trie.TrieMap;
import datastructures.worklists.ArrayStack;
import java.util.AbstractMap.SimpleEntry;

/**
 * See cse332/interfaces/trie/TrieMap.java
 * and cse332/interfaces/misc/Dictionary.java
 * for method specifications.
 */
public class HashTrieMap<A extends Comparable<A>, K extends BString<A>, V> extends TrieMap<A, K, V> {
    public class HashTrieNode extends TrieNode<Dictionary<A, HashTrieNode>, HashTrieNode> {
        public HashTrieNode() {
            this(null);
        }

        public HashTrieNode(V value) {
            this.pointers = new ChainingHashTable<>(MoveToFrontList::new);
            this.value = value;
        }

        @Override
        public Iterator<Entry<A, HashTrieMap<A, K, V>.HashTrieNode>> iterator() {
            ArrayStack<Entry<A, HashTrieNode>> arrayEntry = new ArrayStack<>();

            for (Item<A, HashTrieNode> item : this.pointers ) {
                arrayEntry.add(new SimpleEntry(item.key, item.value));
            }

            return arrayEntry.iterator();
        }
    }

    public HashTrieMap(Class<K> KClass) {
        super(KClass);
        this.root = new HashTrieNode();
    }

    @Override
    public V insert(K key, V value) {
        if(key == null || value == null) {
            throw new IllegalArgumentException();
        }
        HashTrieNode current = (HashTrieNode)this.root;
        for(A character : key) {
            if(current.pointers.find(character) == null) {
                current.pointers.insert(character, new HashTrieNode());
            }
            current = current.pointers.find(character);
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
        HashTrieNode current = findHelper(key);
        if (current == null) {
            return null;
        }
        return current.value;
    }

    @Override
    public boolean findPrefix(K key) {
        return (findHelper(key) != null);
    }

    //This method is a helper method for find() and findPrefix(). It returns a HashTrieNode in order to
    //account for needing to search for a valid prefix or a valid key/value pair. The method returns null
    //if the node does not exist in the HashTrieMap.
    private HashTrieNode findHelper(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        HashTrieNode current = (HashTrieNode) this.root;
        for (A character : key) {
            current = current.pointers.find(character);
            if (current == null) {
                return null;
            }
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
