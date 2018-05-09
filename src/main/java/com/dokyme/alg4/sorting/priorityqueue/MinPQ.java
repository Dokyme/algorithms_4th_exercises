package com.dokyme.alg4.sorting.priorityqueue;

import com.dokyme.alg4.sorting.Sorting;

import static com.dokyme.alg4.sorting.basic.Example.*;

/**
 * Created by intellij IDEA.But customed by hand of Dokyme.
 * 二叉最小堆实现
 *
 * @author dokym
 * @date 2018/5/8-22:07
 * Description:
 */
public class MinPQ<T extends Comparable> extends AbstractPriorityQueue<T> implements Sorting, IMinPQ<T> {

    @Override
    public void sort(Comparable[] a) {
        pq = (T[]) new Comparable[a.length + 1];
        System.arraycopy(a, 0, pq, 1, a.length);
        n = a.length;
        for (int k = n / 2; k >= 1; k--) {
            sink(k);
        }
        while (n > 1) {
            exch(1, n--);
            sink(1);
        }
    }

    public MinPQ(Class cls, int n) {
        pq = (T[]) new Comparable[n + 1];
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    @Override
    public void insert(T t) {
        pq[++n] = t;
        swim(n);
    }

    /**
     * 弹出最小堆中的最小值
     *
     * @return
     */
    @Override
    public T delMin() {
        T min = pq[1];
        exch(1, n--);
        pq[n + 1] = null;
        sink(1);
        return min;
    }

    /**
     * 下沉
     *
     * @param k
     */
    private void sink(int k) {
        while (2 * k + 1 <= n) {
            int n = k * 2;
            if (less(n + 1, n)) {
                //找到最小的子节点
                n += 1;
            }
            if (less(k, n)) {
                //如果k比最小的子节点小，说明下沉完毕
                break;
            }
            exch(k, n);
            k = n;
        }
    }

    /**
     * 上浮
     *
     * @param k
     */
    private void swim(int k) {
        while (k > 1 && less(k, k / 2)) {
            exch(k, k / 2);
            k /= 2;
        }
    }

    public static void main(String[] args) {
//        testSorting(new MinPQ<>(Double.class, 10), true);
    }
}
