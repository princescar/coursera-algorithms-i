import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
    private Node head;
    private Node tail;
    private int count;

    private class Node {
        public Item item;
        public Node next;
        public Node prev;

        public Node(Item item) {
            this.item = item;
            this.next = null;
            this.prev = null;
        }
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current;

        public DequeIterator() {
            this.current = Deque.this.head;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (this.current == null) {
                throw new NoSuchElementException();
            }
            Item item = this.current.item;
            this.current = this.current.next;
            return item;
        }
    }

    // construct an empty deque
    public Deque() {
        this.head = null;
        this.tail = null;
        this.count = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return this.count == 0;
    }

    // return the number of items on the deque
    public int size() {
        return this.count;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node node = new Node(item);
        node.next = this.head;
        if (this.head != null) {
            this.head.prev = node;
        }
        this.head = node;
        if (this.tail == null) {
            this.tail = this.head;
        }
        this.count++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node node = new Node(item);
        node.prev = this.tail;
        if (this.tail != null) {
            this.tail.next = node;
        }
        this.tail = node;
        if (this.head == null) {
            this.head = this.tail;
        }
        this.count++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (this.head == null) {
            throw new NoSuchElementException();
        }
        Item item = this.head.item;
        this.head = this.head.next;
        if (this.head != null) {
            this.head.prev = null;
        } else {
            this.tail = null;
        }
        this.count--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (this.tail == null) {
            throw new NoSuchElementException();
        }
        Item item = this.tail.item;
        this.tail = this.tail.prev;
        if (this.tail != null) {
            this.tail.next = null;
        } else {
            this.head = null;
        }
        this.count--;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> deque = new Deque<String>();
        deque.addFirst("1");
        deque.addFirst("2");
        deque.addLast("3");
        deque.addLast("4");
        StdOut.println(deque.removeFirst());
        StdOut.println(deque.removeLast());
        StdOut.println(deque.removeLast());
        StdOut.println(deque.removeLast());
        deque.addFirst("1");
        deque.addLast("2");
        deque.addFirst("3");
        deque.addFirst("4");
        deque.addLast("5");
        deque.addFirst("6");
        deque.addLast("7");
        deque.addLast("8");
        deque.addLast("9");

        // 6 4 3 1 2 5 7 8 9
        StdOut.println();
        StdOut.println(deque.size());

        for (String s : deque) {
            StdOut.println(s);
        }

        Deque<String> deque2 = new Deque<String>();
        try {
            deque2.addFirst(null);
        } catch (IllegalArgumentException e) {
            StdOut.println("proper exception for addFirst");
        }
        try {
            deque2.addLast(null);
        } catch (IllegalArgumentException e) {
            StdOut.println("proper exception for addLast");
        }
        try {
            deque2.removeFirst();
        } catch (NoSuchElementException e) {
            StdOut.println("proper exception for removeFirst");
        }
        try {
            deque2.removeLast();
        } catch (NoSuchElementException e) {
            StdOut.println("proper exception for removeLast");
        }
        deque2.addLast("a");
        deque2.addLast("b");
        deque2.addLast("c");
        Iterator<String> iterator = deque2.iterator();
        iterator.next();
        try {
            iterator.remove();
        } catch (UnsupportedOperationException e) {
            StdOut.println("proper exception for remove");
        }
        iterator.next();
        iterator.next();
        try {
            iterator.next();
        } catch (NoSuchElementException e) {
            StdOut.println("proper exception for next");
        }
    }
}