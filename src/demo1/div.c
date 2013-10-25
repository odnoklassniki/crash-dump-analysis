#include <jni.h>

JNIEXPORT
jint Java_demo1_NativeDiv_div(JNIEnv* env, jclass cls, jint a, jint b) {
    return a / b;
}
