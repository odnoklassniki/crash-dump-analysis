package extra2;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class LongMask {
    private static final Unsafe unsafe = getUnsafe();
    private static final long SIZE = 4 * 1024 * 1024;

    private static Unsafe getUnsafe() {
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            return (Unsafe) f.get(null);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    long base = unsafe.allocateMemory(SIZE);
    long bottom = base + SIZE;
    int ptr = -1000000;

    public void put() {
        long offset = ptr & ~3;
        unsafe.putInt(bottom + offset, 1);
    }

    public static void main(String[] args) {
        LongMask test = new LongMask();
        for (int i = 0; i < 1000000; i++) {
            test.put();
        }
    }
}
