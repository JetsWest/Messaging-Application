package experiments;
import datastructures.dictionaries.AVLTree;
import cse332.datastructures.trees.BinarySearchTree;

public class BSTvsAVL {

    public static void main(String[] args) {
        int[] testNs = {1000, 5000, 10000, 25000, 50000, 75000, 100000, 200000};
        for(int N : testNs) {
            double[] worstCaseData = worstCase(N);
            System.out.println("N = " + N);
            System.out.println("Average runtime for BST Insert: " + worstCaseData[0]);
            System.out.println("Average runtime for BST Find: " + worstCaseData[1]);
            System.out.println("Average runtime for AVL Insert: " + worstCaseData[2]);
            System.out.println("Average runtime for AVL Find: " + worstCaseData[3]);
        }

    }

    public static double[] worstCase(int N) {
        int numTrials = 15;
        int warmUpTrials = 5;
        // Index 0 is BST insert, index 1 is BST find, index 2 is AVL insert, index 3 is AVL find
        double[] results = new double[4];
        double BSTInserttotalTime = 0;
        double BSTFindtotalTime = 0;
        for(int i = 0; i < numTrials; i++) {
            BinarySearchTree<Integer, Integer> bst = new BinarySearchTree<Integer, Integer>();
            //Insert Test
            long BSTstartTime = System.currentTimeMillis();
            for(int j = 1; j <= N; j++) {
                bst.insert(j, 1);
            }
            long BSTendTime = System.currentTimeMillis();
            if(i >= warmUpTrials) {
                BSTInserttotalTime += (BSTendTime - BSTstartTime);
            }
            //Find test
            BSTstartTime = System.currentTimeMillis();
            for(int j = 1; j <= N; j++) {
                bst.find(j);
            }
            BSTendTime = System.currentTimeMillis();
            if(i >= warmUpTrials) {
                BSTFindtotalTime += (BSTendTime - BSTstartTime);
            }
        }
        results[0] = BSTInserttotalTime / (numTrials - warmUpTrials);
        results[1] = BSTFindtotalTime / (numTrials - warmUpTrials);

        double AVLInserttotalTime = 0;
        double AVLFindtotalTime = 0;
        for(int i = 0; i < numTrials; i++) {
            AVLTree<Integer, Integer> avl = new AVLTree<Integer, Integer>();
            //Insert Test
            long AVLstartTime = System.currentTimeMillis();
            for(int j = 1; j <= N; j++) {
                avl.insert(j, 1);
            }
            long AVLendTime = System.currentTimeMillis();
            if(i >= warmUpTrials) {
                AVLInserttotalTime += (AVLendTime - AVLstartTime);
            }
            //Find test
            AVLstartTime = System.currentTimeMillis();
            for(int j = 1; j <= N; j++) {
                avl.find(j);
            }
            AVLendTime = System.currentTimeMillis();
            if(i >= warmUpTrials) {
                AVLFindtotalTime += (AVLendTime - AVLstartTime);
            }
        }
        results[2] = AVLInserttotalTime / (numTrials - warmUpTrials);
        results[3] = AVLFindtotalTime / (numTrials - warmUpTrials);

        return results;
    }

}
