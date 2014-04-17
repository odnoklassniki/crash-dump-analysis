package demo2;

public class NativeArgs {

    public static void main(String[] args) {
        System.loadLibrary("args");

        new NativeArgs().invoke6(1, 2, (short) 3, (byte) 4, 5, 6);
    }

//    |-------------------------------------------------------|
//    | c_rarg0   c_rarg1  c_rarg2 c_rarg3 c_rarg4 c_rarg5    |
//    |-------------------------------------------------------|
//    | rcx       rdx      r8      r9      rdi*    rsi*       | windows
//    | rdi       rsi      rdx     rcx     r8      r9         | linux
//    |-------------------------------------------------------|
//    | j_rarg5   j_rarg0  j_rarg1 j_rarg2 j_rarg3 j_rarg4    |
//    |-------------------------------------------------------|

    native void invoke6(int a, int b, short c, byte d, long e, long f);
}
