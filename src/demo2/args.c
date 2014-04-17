#include <jni.h>

JNIEXPORT
void Java_demo2_NativeArgs_invoke6(JNIEnv* env, jobject self,
                                   jint a, jint b, jshort c,
                                   jbyte d, jlong e, jlong f) {
    int* ptr = NULL;
    *ptr = 1;
}
