package demo1;

import java.util.Random;

public class NativeDiv {

    public static void main(String[] args) {
        System.loadLibrary("div");

        final Random random = new Random(63);

        runLoop(100000000, new Runnable() {
            @Override
            public void run() {
                int a = random.nextInt();
                int b = random.nextInt(16);
                div(a, b);
            }
        });
    }

    private static void runLoop(int iterations, Runnable code) {
        for (int i = 0; i < iterations; i++) {
            code.run();
        }
    }

    private static native int div(int a, int b);
}
