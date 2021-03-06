package com.dokyme.alg4.sorting.basic;

import com.dokyme.alg4.sorting.Sorting;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

import java.lang.reflect.Array;
import java.util.Comparator;

import static com.dokyme.alg4.sorting.basic.SortCompare.*;

/**
 * Created by intellij IDEA.But customed by hand of Dokyme.
 *
 * @author dokym
 * @date 2018/3/10-13:19
 * Description:
 */
public class Example {

    public static void sort(Comparable[] a) {
    }

    public static boolean eq(Object v, Object w, Comparator c) {
        return c.compare(v, w) == 0;
    }

    public static boolean eq(Comparable v, Comparable w) {
        return v.compareTo(w) == 0;
    }

    public static boolean less(Object v, Object w, Comparator c) {
        return c.compare(v, w) < 0;
    }

    public static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    public static void exch(double[] a, int i, int j) {
        double temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    public static void exch(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            StdOut.print(a[i] + " ");
        }
        StdOut.println();
    }

    public static boolean isSorted(Comparable[] a) {
        return isSorted(a, false);
    }

    /**
     * 检查一个数组的元素是否按照升序排列。
     *
     * @param a
     * @return
     */
    public static boolean isSorted(Comparable[] a, boolean desc) {
        if (!desc) {
            for (int i = 1; i < a.length; i++) {
                if (less(a[i], a[i - 1])) {
                    return false;
                }
            }
        } else {
            for (int i = 1; i < a.length; i++) {
                if (less(a[i - 1], a[i])) {
                    return false;
                }
            }
        }
        return true;
    }

    public interface DataMocker<T extends Comparable<T>> {
        T mock(int i);
    }

    public static <T extends Comparable<T>> T[] generate(Class<T> cls, int length, DataMocker<T> generator) {
        T[] a = (T[]) Array.newInstance(cls, length);
        for (int i = 0; i < length; i++) {
            a[i] = generator.mock(i);
        }
        return a;
    }

    public static <T extends Comparable<T>> T[] generate(int length, DataMocker<T> generator) {
        T[] a = (T[]) new Comparable[length];
        for (int i = 0; i < length; i++) {
            a[i] = generator.mock(i);
        }
        return a;
    }

    public static Comparable[] generateTestData(Object cls, int length) {
        if (cls.getClass().equals(Double.class)) {
            Double[] a = new Double[length];
            for (int i = 0; i < length; i++) {
                a[i] = StdRandom.uniform();
            }
            return a;
        } else if (cls.getClass().equals(Integer.class)) {
            Integer[] a = new Integer[length];
            for (int i = 0; i < length; i++) {
                a[i] = StdRandom.uniform(length);
            }
            return a;
        } else {
            return null;
        }
    }

    public static void testSorting(Sorting sorting) {
        testSorting(sorting, 1000, false);
    }

    public static void testSorting(Sorting sorting, int length) {
        testSorting(sorting, length, false);
    }

    public static void testSorting(Sorting sorting, boolean desc) {
        testSorting(sorting, 1000, desc);
    }

    public static void testSorting(Sorting sorting, int length, boolean desc) {
        Double[] array = new Double[length];
        for (int i = 0; i < array.length; i++) {
            array[i] = StdRandom.uniform();
        }
        sorting.sort(array);
        assert isSorted(array, desc);
        for (double d : array) {
            StdOut.print(d + "\n");
        }
    }

    public static void doubleTesting(Sorting sorting) {
        final int start = 1 << 8;
        final int end = 1 << 22;
        double prev = 1;
        for (int i = start; i <= end; i <<= 1) {
            double time = 0;
            for (int t = 0; t < 100; t++) {
                Comparable[] array = generateTestData(new Double(1), i);
                Stopwatch stopwatch = new Stopwatch();
                sorting.sort(array);
                time += stopwatch.elapsedTime();
            }
            double arraysort = testArraysSort(i, 100);
            StdOut.printf("size:%d\ttime:%f\tratio:%f\tArrays.sort:%f\n", i, time, time / prev, arraysort);
            prev = time;
        }
    }

    public static <T extends Comparable<T>> boolean test(Sorting sorting, DataMocker<T> mocker, int length) {
        T[] a = (T[]) generate(length, mocker);
        sorting.sort(a);
        return isSorted(a);
    }

    public static <T extends Comparable<T>> double test(Sorting sorting, DataMocker<T> mocker, int length, int times) {
        return test(sorting, mocker, length, times, false);
    }

    public static <T extends Comparable<T>> double test(Sorting sorting, DataMocker<T> mocker, int length, int times, boolean shuffle) {
        double total = 0d;
        for (int t = 0; t < times; t++) {
            T[] a = (T[]) generate(length, mocker);
            if (shuffle) {
                StdRandom.shuffle(a);
            }
            Stopwatch stopwatch = new Stopwatch();
            sorting.sort(a);
            total += stopwatch.elapsedTime();
        }
        return total;
    }

    public static <T extends Comparable<T>> void test(Sorting[] sortings, DataMocker<T> mocker, int length, int times) {
        StdOut.println("Length:" + length);
        for (Sorting sorting : sortings) {
            double t = 0d;
            for (int i = 0; i < times; i++) {
                Comparable[] arrays = generate(length, mocker);
                Stopwatch w = new Stopwatch();
                sorting.sort(arrays);
                t += w.elapsedTime();
            }
            StdOut.println(String.format("%s(x%d)\t%f", sorting.getClass().getSimpleName(), times, t));
        }
        StdOut.println();
    }

    public interface EachSortingAction {
        void run(Sorting sorting, int length, int times);
    }

    public static void test(Sorting[] sortings, int minLength, int maxLength, int times, EachSortingAction action) {
        for (Sorting sorting : sortings) {
            for (int length = minLength; length <= maxLength; length *= 2) {
                action.run(sorting, length, times);
            }
        }
    }

    public static <T extends Comparable<T>> void test(Sorting[] sortings, int minLength, int maxLength, int times, DataMocker<T> mocker) {
        for (int length = minLength; length <= maxLength; length *= 2) {
            StdOut.println("Length:" + length);
            for (Sorting sorting : sortings) {
                double t = 0d;
                for (int i = 0; i < times; i++) {
                    Stopwatch stopwatch = new Stopwatch();
                    sorting.sort(generate(length, mocker));
                    t += stopwatch.elapsedTime();
                }
                StdOut.println(String.format("%s(x%d)\t%f", sorting.toString(), times, t));
            }
            StdOut.println();
        }
    }

    public static boolean isSorted(int[] a) {
        for (int i = 1; i < a.length; i++) {
            if (a[i] < a[i - 1]) {
                return false;
            }
        }
        return true;
    }

    public static boolean isSorted(double[] a) {
        for (int i = 1; i < a.length; i++) {
            if (a[i] < a[i - 1]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
//        Double[] pq = (Double[]) generateTestData(new Double(1.0), 100);
//        sort(pq);
//        exch(pq, 0, 1);
//        boolean r = isSorted(pq);
//        assert r;
//        show(pq);
    }
}
