package com.dokyme.alg4.searching.st;

import java.util.LinkedList;

/**
 * Created by intellij IDEA.But customed by hand of Dokyme.
 *
 * @author dokym
 * @date 2018/6/1-17:44
 * Description:
 */
public class SequentialSearchSymbolTable<Key extends Comparable<Key>, Value> implements SymbolTable<Key, Value> {

    private Node cached;

    private class Node {
        Key key;
        Value val;
        Node next;

        public Node(Key key, Value val, Node next) {
            this.key = key;
            this.val = val;
            this.next = next;
        }
    }

    private Node first;

    private int size;

    @Override
    public Value get(Key key) {
        if (cached != null && key.compareTo(cached.key) == 0) {
            return cached.val;
        }
        for (Node x = first; x != null; x = x.next) {
            if (key.equals(x.key)) {
                cached = x;
                return x.val;
            }
        }
        return null;
    }

    @Override
    public void put(Key key, Value val) {
        if (cached != null && key.compareTo(cached.key) == 0) {
            cached.val = val;
            return;
        }
        for (Node x = first; x != null; x = x.next) {
            if (key.equals(x.key)) {
                x.val = val;
                cached = x;
                return;
            }
        }
        first = new Node(key, val, first);
        size++;
    }

    @Override
    public void delete(Key key) {
        Node last = null;
        for (Node x = first; x != null; x = x.next) {
            if (key.compareTo(x.key) == 0) {
                if (last == null) {
                    first = first.next;
                } else {
                    last.next = x.next;
                }
                size--;
                return;
            }
            last = x;
        }
    }

    @Override
    public boolean contains(Key key) {
        return get(key) != null;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Key min() {
        return null;
    }

    @Override
    public Key max() {
        return null;
    }

    @Override
    public Key floor(Key key) {
        return null;
    }

    @Override
    public Key ceiling(Key key) {
        return null;
    }

    @Override
    public int rank(Key key) {
        return 0;
    }

    @Override
    public Key select(int n) {
        return null;
    }

    @Override
    public void deleteMin() {
        delete(min());
    }

    @Override
    public void deleteMax() {
        delete(max());
    }

    @Override
    public int size(Key lo, Key hi) {
        return size;
    }

    @Override
    public Iterable<Key> keys(Key lo, Key hi) {
        LinkedList<Key> q = new LinkedList<>();
        for (Node x = first; x != null; x = x.next) {
            if (lo.compareTo(x.key) == 0) {
                for (; x != null && hi.compareTo(x.key) != 0; x = x.next) {
                    q.push(x.key);
                }
                return q;
            }
        }
        return null;
    }

    @Override
    public Iterable<Key> keys() {
        return keys(min(),max());
    }

    public static void main(String[] args) {

    }
}
