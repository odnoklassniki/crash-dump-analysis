package demo2;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLongArray;

public class LongHashSet {
    private AtomicLongArray array;
    private int count;

    public LongHashSet(int capacity) {
        this.array = new AtomicLongArray(capacity);
    }

    public boolean add(long value) {
        int mask = array.length() - 1;
        int index = hash(value) & mask;

        for (;;) {
            long current = array.get(index);
            if (current == 0) {
                if (array.compareAndSet(index, 0, value)) {
                    count++;
                    return true;
                }
            } else if (current == value) {
                return false;
            } else {
                index = (index + 1) & mask;
            }
        }
    }

    public boolean isFull() {
        return count >= array.length();
    }

    private int hash(long value) {
        return (int) (value ^ (value >>> 32));
    }

    public static void main(String[] args) {
        LongHashSet set = new LongHashSet(256 * 1024 * 1024);
        Random random = new Random(0);

        while (!set.isFull()) {
            set.add(random.nextLong());
        }
    }
}
