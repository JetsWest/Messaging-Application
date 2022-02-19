package p2.sorts;

import java.util.Comparator;

import cse332.exceptions.NotYetImplementedException;

public class QuickSort {
    public static <E extends Comparable<E>> void sort(E[] array) {
        QuickSort.sort(array, (x, y) -> x.compareTo(y));
    }

    public static <E> void sort(E[] array, Comparator<E> comparator) {
        quickSort(array, comparator, 0, array.length-1);
    }

    private static <E> void quickSort(E[] array, Comparator<E> comparator, int lo, int hi) {
        if (lo < hi) {
            int pi = partition(array, comparator, lo, hi);

            quickSort(array, comparator, lo, pi - 1);
            quickSort(array, comparator, pi + 1, hi);
        }
    }

    private static <E> int partition(E[] array, Comparator<E> comparator, int lo, int hi) {
        E pivot = array[hi];
        int i = lo - 1;
        for (int j = lo; j <= hi - 1; j++) {
            if (comparator.compare(array[j], pivot) < 0) {
                i++;
                E temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
        E temp = array[i+1];
        array[i+1] = array[hi];
        array[hi] = temp;
        return i + 1;
    }

}
