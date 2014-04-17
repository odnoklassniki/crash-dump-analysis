#include <jni.h>
#include <math.h>

// double (*sqrtFunc)(double) = sqrt;  // correct
double (*sqrtFunc)(double);            // bad

JNIEXPORT
jfloat Java_demo6_NativeDistance_distance(JNIEnv* env, jclass cls,
                                          jfloat x1, jfloat y1,
                                          jfloat x2, jfloat y2) {
    jfloat dx = x2 - x1;
    jfloat dy = y2 - y1;
    return (jfloat) sqrtFunc(dx*dx + dy*dy);
}
