#include <jni.h>
#include <math.h>

jdouble (*distanceFunc)(jdouble);

JNIEXPORT
jlong Java_demo5_NativeDistance_distance(JNIEnv* env, jclass cls,
                                         jlong x1, jlong y1, jlong z1,
                                         jlong x2, jlong y2, jlong z2) {
    jdouble dx = x2 - x1;
    jdouble dy = y2 - y1;
    jdouble dz = z2 - z1;
    return (jlong) distanceFunc(dx*dx + dy*dy + dz*dz);
}
