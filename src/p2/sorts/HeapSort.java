package p2.sorts;

import java.util.Comparator;
import cse332.exceptions.NotYetImplementedException;
import datastructures.worklists.MinFourHeap;

public class HeapSort {
    public static <E extends Comparable<E>> void sort(E[] array) {
        sort(array, (x, y) -> x.compareTo(y));
    }

    public static <E> void sort(E[] array, Comparator<E> comparator) {
        MinFourHeap<E> minHeap = new MinFourHeap<>(comparator);
        for (int i = 0; i < array.length; i++) {
            minHeap.add(array[i]);
        }
        for (int j = 0; j < array.length; j++) {
            array[j] = minHeap.next();
        }
    }
}
