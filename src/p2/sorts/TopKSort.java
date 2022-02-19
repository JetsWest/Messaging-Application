package p2.sorts;

import java.util.Comparator;
import datastructures.worklists.MinFourHeap;

public class TopKSort {
    public static <E extends Comparable<E>> void sort(E[] array, int k) {
        sort(array, k, (x, y) -> x.compareTo(y));
    }

    public static <E> void sort(E[] array, int k, Comparator<E> comparator) {
        MinFourHeap<E> heap = new MinFourHeap<>(comparator);

        if (k > array.length) {
            k = array.length;
        }

        for (int i = 0; i < k; i++) {
            heap.add(array[i]);
        }

        for (int start = k; start < array.length; start++) {
            if (comparator.compare(array[start], heap.peek()) > 0) {
                heap.next();
                heap.add(array[start]);
            }
            array[start]=null;
        }

        for (int i = 0; i < k; i++) {
            array[i] = heap.next();
        }


    }
}
