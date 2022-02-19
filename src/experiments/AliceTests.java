package experiments;

import cse332.interfaces.misc.Dictionary;
import cse332.misc.WordReader;
import cse332.types.AlphabeticString;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class AliceTests {

    public static String file = "alice.txt";

    public static final int N = 27139;
    public static void main(String[] args) throws IOException {
        ChainingHashTableCopy<String, Integer> chainingHashTable = new ChainingHashTableCopy<>(MoveToFrontList::new);
        BinarySearchTree<String, Integer> binarySearchTree = new BinarySearchTree<>();
        AVLTree<String, Integer> avlTree = new AVLTree<>();
        HashTrieMap<Character, AlphabeticString, Integer> hashTrieMap = new HashTrieMap<>(AlphabeticString.class);

        CHTTest(chainingHashTable);
        BSTTest(binarySearchTree);
        AVLTest(avlTree);
        HTMTest(hashTrieMap);

    }

    private static void CHTTest(ChainingHashTableCopy<String, Integer> list) throws FileNotFoundException {
        WordReader reader = new WordReader(new FileReader(file));
        for (int i = 0; i < N; i++) {
            String value = reader.next();
            incCount(list, value);
        }
        System.out.println("CHT = " + list.CHTsteps);
    }

    private static void BSTTest(BinarySearchTree<String, Integer> list) throws FileNotFoundException {
        WordReader reader = new WordReader(new FileReader(file));
        for (int i = 0; i < N; i++) {
            String value = reader.next();
            incCount(list, value);
        }
        System.out.println("BST = " + list.BSTsteps);
    }

    private static void AVLTest(AVLTree<String, Integer> list) throws FileNotFoundException {
        WordReader reader = new WordReader(new FileReader(file));
        for (int i = 0; i < N; i++) {
            String value = reader.next();
            incCount(list, value);
        }
        System.out.println("AVL = " + list.AVLsteps);
    }

    private static void HTMTest(HashTrieMap<Character, AlphabeticString, Integer> map) throws FileNotFoundException {
        WordReader reader = new WordReader(new FileReader(file));
        for (int i = 0; i < N; i++) {
            String value = reader.next();
            specialInc(map, value);
        }
        System.out.println("HTM = " + map.HTMsteps);
    }

    private static void specialInc(HashTrieMap<Character, AlphabeticString, Integer> map, String key) {
        AlphabeticString rep = new AlphabeticString(key);
        Integer find = map.find(rep);
        if (find == null) {
            map.insert(rep, 1);
        } else {
            map.insert(rep, 1 + find);
        }
    }

    private static void incCount(Dictionary<String, Integer> list, String key) {
        Integer find = list.find(key);
        if (find == null) {
            list.insert(key, 1);
        } else {
            list.insert(key, 1 + find);
        }
    }

}
