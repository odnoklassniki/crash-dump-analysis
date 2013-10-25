package demo6;

import sun.misc.Unsafe;

import java.util.Random;

public class Malloc {
    private static final Unsafe unsafe = getUnsafe();

    private static final int HEADER_SIZE = 8;
    private static final int SIZE_OFFSET = 0;
    private static final int LEFT_OFFSET = 4;
    private static final int NEXT_OFFSET = 8;
    private static final int PREV_OFFSET = 16;
    private static final int MIN_CHUNK   = HEADER_SIZE + 16;

    private final long base;

    public Malloc(int capacity) {
        base = unsafe.allocateMemory(capacity);
        unsafe.setMemory(base, capacity, (byte) 0);
        addFreeChunk(base + MIN_CHUNK, capacity - MIN_CHUNK * 2);
    }

    public long malloc(int size) {
        // Align size
        size = Math.max((size + HEADER_SIZE + 7) & ~7, MIN_CHUNK);

        for (long chunk = base; (chunk = unsafe.getLong(chunk + NEXT_OFFSET)) != 0; ) {
            int chunkSize = unsafe.getInt(chunk + SIZE_OFFSET);
            int leftoverSize = chunkSize - size;

            if (leftoverSize < 0) {
                // Continue search
            } else if (leftoverSize < MIN_CHUNK) {
                // Allocated memory perfectly fits the chunk
                unsafe.putInt(chunk + SIZE_OFFSET, ~chunkSize);
                removeFreeChunk(chunk);
                return chunk + HEADER_SIZE;
            } else {
                // Allocate memory from an oversized chunk
                unsafe.putInt(chunk + SIZE_OFFSET, ~size);
                removeFreeChunk(chunk);
                // Cut off the remaining tail and treat it as a smaller free chunk
                long leftoverChunk = chunk + size;
                addFreeChunk(leftoverChunk, leftoverSize);
                unsafe.putInt(leftoverChunk + LEFT_OFFSET, size);
                return chunk + HEADER_SIZE;
            }
        }

        return 0;
    }

    public void free(long address) {
        address -= HEADER_SIZE;
        int size = ~unsafe.getInt(address + SIZE_OFFSET);
        freeImpl(address, size);
    }

    private void freeImpl(long address, int size) {
        // Calculate the addresses of the neighbour chunks
        long leftChunk = address - unsafe.getInt(address + LEFT_OFFSET);
        long rightChunk = address + size;
        int leftSize = unsafe.getInt(leftChunk + SIZE_OFFSET);
        int rightSize = unsafe.getInt(rightChunk + SIZE_OFFSET);

        // Coalesce with left neighbour chunk if it is free
        if (leftSize > 0) {
            size += leftSize;
            removeFreeChunk(leftChunk);
            address = leftChunk;
        }

        // Coalesce with right neighbour chunk if it is free
        if (rightSize > 0) {
            size += rightSize;
            removeFreeChunk(rightChunk);
        }

        // Return the combined chunk to the free list
        addFreeChunk(address, size);
    }

    // Insert a new chunk in the head of the linked list of free chunks
    private void addFreeChunk(long address, int size) {
        unsafe.putInt(address + SIZE_OFFSET, size);
        unsafe.putInt(address + size + LEFT_OFFSET, size);

        long head = unsafe.getLong(base + NEXT_OFFSET);
        unsafe.putLong(address + NEXT_OFFSET, head);
        unsafe.putLong(address + PREV_OFFSET, base);
        unsafe.putLong(base + NEXT_OFFSET, address);
        if (head != 0) {
            unsafe.putLong(head + PREV_OFFSET, address);
        }
    }

    // Remove a chunk from the linked list of free chunks
    private void removeFreeChunk(long address) {
        long next = unsafe.getLong(address + NEXT_OFFSET);
        long prev = unsafe.getLong(address + PREV_OFFSET);

        unsafe.putLong(prev + NEXT_OFFSET, next);
        if (next != 0) {
            unsafe.putLong(next + PREV_OFFSET, prev);
        }
    }

    private static Unsafe getUnsafe() {
        try {
            java.lang.reflect.Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            return (Unsafe) f.get(null);
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    public static void main(String[] args) {
        Malloc u = new Malloc(512 * 1024 * 1024);
        long[] addr = new long[1024];
        Random random = new Random(0);

        for (int i = 0; i < 1000000; i++) {
            int n = random.nextInt(addr.length);
            if (addr[n] == 0) {
                addr[n] = u.malloc(n);
            } else {
                u.free(addr[n]);
                addr[n] = 0;
            }
        }

        System.out.println("done");
    }
}
