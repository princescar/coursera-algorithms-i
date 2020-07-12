import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private class RandomizedQueueIterator implements Iterator<Item> {
        private final int[] indexes;
        private int n;

        public RandomizedQueueIterator() {
            int[] sequence = new int[RandomizedQueue.this.count];
            for (int i = 0; i < sequence.length; i++) {
                sequence[i] = i;
            }
            StdRandom.shuffle(sequence);
            this.indexes = sequence;
            this.n = 0;
        }

        @Override
        public boolean hasNext() {
            return this.n != indexes.length;
        }

        @Override
        public Item next() {
            if (this.n == indexes.length) {
                throw new NoSuchElementException();
            }
            int index = indexes[this.n++];
            return (Item) RandomizedQueue.this.items[index];
        }
    }

    private Object[] items;
    private int count;

    // construct an empty randomized queue
    public RandomizedQueue() {
        this.items = new Object[1];
        this.count = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return this.count == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return this.count;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (this.count == this.items.length) {
            this.resize(this.count * 2);
        }
        this.items[this.count++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (this.count == 0) {
            throw new NoSuchElementException();
        }
        int index = StdRandom.uniform(this.count);
        Item item = (Item) this.items[index];
        this.items[index] = this.items[count - 1];
        this.items[count - 1] = null;
        this.count--;
        if (this.count > 0 && this.count * 4 <= this.items.length) {
            this.resize(this.count * 2);
        }
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (this.count == 0) {
            throw new NoSuchElementException();
        }
        int index = StdRandom.uniform(this.count);
        return (Item) this.items[index];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private void resize(int capacity) {
        Object[] newItems = new Object[capacity];
        for (int i = 0; i < this.count; i++) {
            newItems[i] = this.items[i];
        }
        this.items = newItems;
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        rq.enqueue(990);
        rq.dequeue();
        rq.enqueue(364);
        rq.dequeue();

        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        queue.enqueue("a");
        queue.enqueue("b");
        queue.enqueue("c");
        queue.enqueue("d");
        queue.enqueue("e");
        queue.enqueue("f");
        queue.enqueue("g");
        queue.enqueue("h");
        queue.enqueue("i");
        queue.enqueue("j");
        StdOut.println(queue.size());
        StdOut.println(queue.sample());

        StdOut.println();

        StdOut.println(queue.dequeue());
        StdOut.println(queue.dequeue());
        StdOut.println(queue.dequeue());
        StdOut.println(queue.dequeue());
        StdOut.println(queue.dequeue());
        StdOut.println(queue.dequeue());
        StdOut.println(queue.dequeue());
        StdOut.println(queue.dequeue());
        StdOut.println(queue.dequeue());
        StdOut.println(queue.dequeue());

        StdOut.println();

        RandomizedQueue<String> queue2 = new RandomizedQueue<String>();
        queue2.enqueue("a");
        queue2.enqueue("b");
        queue2.enqueue("c");
        queue2.enqueue("d");
        queue2.enqueue("e");
        queue2.enqueue("f");
        queue2.enqueue("g");
        queue2.enqueue("h");
        queue2.enqueue("i");
        queue2.enqueue("j");
        for (String s : queue2) {
            StdOut.println(s);
        }
        StdOut.println();

        RandomizedQueue<String> queue3 = new RandomizedQueue<String>();
        try {
            queue3.enqueue(null);
        } catch (IllegalArgumentException e) {
            StdOut.println("proper exception for enqueue");
        }
        try {
            queue3.sample();
        } catch (NoSuchElementException e) {
            StdOut.println("proper exception for sample");
        }
        try {
            queue3.dequeue();
        } catch (NoSuchElementException e) {
            StdOut.println("proper exception for dequeue");
        }
        queue3.enqueue("a");
        queue3.enqueue("b");
        Iterator<String> iterator = queue3.iterator();
        iterator.next();
        try {
            iterator.remove();
        } catch (UnsupportedOperationException e) {
            StdOut.println("proper exception for remove");
        }
        iterator.next();
        try {
            iterator.next();
        } catch (NoSuchElementException e) {
            StdOut.println("proper exception for next");
        }
    }

}