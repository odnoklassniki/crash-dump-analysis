package demo6;

public class NativeDistance {

    public static void main(String[] args) {
        System.loadLibrary("distance");

        float d = distance(1, 2, 3, 4);
        System.out.println(d);
    }

    static native float distance(float x1, float y1, float x2, float y2);
}
