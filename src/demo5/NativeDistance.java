package demo5;

public class NativeDistance {

    public static void main(String[] args) {
        long d = distance(1, 2, 3, 4, 5, 6);
        System.out.println(d);
    }

    public static native long distance(long x1, long y1, long z1,
                                       long x2, long y2, long z2);

    static {
        System.loadLibrary("distance");
    }
}
