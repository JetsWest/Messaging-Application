package experiments;

import java.util.ArrayList;
import java.util.Random;

public class HashTableTests {

    public static int N = 1000000;

    public static void main(String[] args) {
        ChainingHashTable<Integer, Integer> AVLHashTable = new ChainingHashTable<>(AVLTree::new);
        ChainingHashTable<Integer, Integer> BSTHashTable = new ChainingHashTable<>(BinarySearchTree::new);
        ChainingHashTable<Integer, Integer> MTFLHashTable = new ChainingHashTable<>(MoveToFrontList::new);

        testing(AVLHashTable, BSTHashTable, MTFLHashTable);

    }
    
    public static void testing(ChainingHashTable<Integer, Integer> AVL, ChainingHashTable<Integer, Integer> BST, ChainingHashTable<Integer, Integer> MTFL) {
        double startTime;
        double findAVL = 0;
        double findBST = 0;
        double findMTFL = 0;
        double insertAVL = 0;
        double insertBST = 0;
        double insertMTFL = 0;
        int numTrials = 15;
        int warmupTrials = 5;

        Random rand = new Random();

        for (int t = 0; t < numTrials; t++) {

            AVL = new ChainingHashTable<>(AVLTree::new);
            BST = new ChainingHashTable<>(BinarySearchTree::new);
            MTFL = new ChainingHashTable<>(MoveToFrontList::new);

            ArrayList<Integer> AVLList = new ArrayList<>();
            ArrayList<Integer> BSTList = new ArrayList<>();
            ArrayList<Integer> MTFLList = new ArrayList<>();

            startTime = System.currentTimeMillis();
            for (int i = 0; i < N; i++) {
                int random = rand.nextInt();
                AVLList.add(random);
                AVL.insert(random, i);
            }
            if (t >= warmupTrials) {
                insertAVL += System.currentTimeMillis() - startTime;
            }

            startTime = System.currentTimeMillis();
            for (int i = 0; i < N; i++) {
                int random = rand.nextInt();
                BSTList.add(random);
                BST.insert(random, i);
            }
            if (t >= warmupTrials) {
                insertBST += System.currentTimeMillis() - startTime;
            }

            startTime = System.currentTimeMillis();
            for (int i = 0; i < N; i++) {
                int random = rand.nextInt();
                MTFLList.add(random);
                MTFL.insert(random, i);
            }
            if (t >= warmupTrials) {
                insertMTFL += System.currentTimeMillis() - startTime;
            }

            startTime = System.currentTimeMillis();
            for (int f : AVLList) {
                AVL.find(f);
            }
            if (t >= warmupTrials) {
                findAVL += System.currentTimeMillis() - startTime;
            }

            startTime = System.currentTimeMillis();
            for (int f : BSTList) {
                BST.find(f);
            }
            if (t >= warmupTrials) {
                findBST += System.currentTimeMillis() - startTime;
            }

            startTime = System.currentTimeMillis();
            for (int f : MTFLList) {
                MTFL.find(f);
            }
            if (t >= warmupTrials) {
                findMTFL += System.currentTimeMillis() - startTime;
            }
        }
        System.out.println("Insert AVL = " + insertAVL / (numTrials - warmupTrials));
        System.out.println("Insert BST = " + insertBST / (numTrials - warmupTrials));
        System.out.println("Insert MTFL = " + insertMTFL / (numTrials - warmupTrials));

        System.out.println("Find AVL = " + findAVL / (numTrials - warmupTrials));
        System.out.println("Find BST = " + findBST/ (numTrials - warmupTrials));
        System.out.println("Find MTFL = " + findMTFL/ (numTrials - warmupTrials));

    }



}
