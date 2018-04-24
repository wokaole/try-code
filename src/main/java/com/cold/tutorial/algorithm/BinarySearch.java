package com.cold.tutorial.algorithm;

import java.util.Arrays;

/**
 * Created by faker on 2017/2/18.
 */
public class BinarySearch {

    public static void main(String[] args) {
        int i = Arrays.binarySearch(new String[]{"1", "2", "3"}, "2");
        System.out.println(i);

        Integer[] arr = {3, 6, 7, 9, 11, 14, 16, 77};
        System.out.println(Arrays.binarySearch(arr, 16));
        System.out.println(binarySearch(arr, 16));

        System.out.println(binarySearchRecursion(arr, 0, arr.length, 16));
    }

    public static int binarySearch(Object[] array, Object key) {
        return binarySearch(array, 0, array.length, key);
    }

    public static int binarySearch(Object[] array, int from, int to, Object key) {

        int low = from;
        int high = to - 1;

        while(low < high) {
            int mid = (low + high) >>> 1;
            Comparable midVal = (Comparable)array[mid];
            int cmp = midVal.compareTo(key);

            if (cmp < 0) {
                low = mid + 1;
            } else if (cmp >0) {
                high = mid - 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    public static int binarySearchRecursion(Object[] array, int from, int to, Object key) {
        int low = from;
        int high = to - 1;
        int mid = (low + high) >>> 1;

        Comparable midVal = (Comparable)array[mid];
        int cmp = midVal.compareTo(key);
        if (cmp < 0) {
            return binarySearchRecursion(array, mid + 1, to, key);
        } else if (cmp > 0) {
            return binarySearchRecursion(array, from, mid - 1, key);
        } else {
            return mid;
        }

    }

}
