package experiments;
import datastructures.dictionaries.MoveToFrontList;

import java.util.ArrayList;
import java.util.Random;

public class HashFunctionsTests {

    public static void main(String[] args) {
        int[] testNs = {1000, 5000, 10000, 25000, 50000, 75000, 100000, 200000, 300000, 500000, 1000000};
        for(int N : testNs) {
            double[] hashCodeData = testHashCode(N);
            System.out.println("N = " + N);
            System.out.println("Average insert runtime: " + hashCodeData[0]);
            System.out.println("Average find runtime: " + hashCodeData[1]);
        }

    }

    public static double[] testHashCode(int N) {
        //Index 0 is insert average runtime, index 1 is find average runtime
        double[] results = new double[2];
        int numTrials = 15;
        int warmUpTrials = 5;
        Random rand = new Random();
        double totalInsertTime = 0;
        double totalFindTime = 0;
        ArrayList<AlphabeticString> randomKeys = new ArrayList<AlphabeticString>();
        char[] symbols = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        for (char a : symbols) {
            for (char b : symbols) {
                for (char c : symbols) {
                    Character[] word = new Character[]{a, b, c};
                    randomKeys.add(new AlphabeticString(word));
                }
            }
        }
        for(int i = 0; i < numTrials; i++) {
            ChainingHashTable<AlphabeticString, Integer> hashTable = new ChainingHashTable<AlphabeticString, Integer>(MoveToFrontList::new);
            ArrayList<AlphabeticString> keys = new ArrayList<AlphabeticString>();
            for(int j = 0; j < N; j++) {
                AlphabeticString keyToInsert = randomKeys.get(rand.nextInt(randomKeys.size()));
                keys.add(keyToInsert);
            }
            long insertStartTime = System.currentTimeMillis();
            for(AlphabeticString keyToInsert : keys) {
                hashTable.insert(keyToInsert, 1);
            }
            long insertEndTime = System.currentTimeMillis();
            if(i >= warmUpTrials) {
                totalInsertTime += (insertEndTime - insertStartTime);
            }

            long findStartTime = System.currentTimeMillis();
            for(AlphabeticString key : keys) {
                hashTable.find(key);
            }
            long findEndTime = System.currentTimeMillis();
            if(i >= warmUpTrials) {
                totalFindTime += (findEndTime - findStartTime);
            }
        }
        results[0] = totalInsertTime / (numTrials - warmUpTrials);
        results[1] = totalFindTime / (numTrials - warmUpTrials);

        return results;
    }
}
