#!/bin/bash
JAVA_HOME=/usr/java/jdk1.7.0_51
gcc -g -O2 -fPIC -shared -olib/libdistance.so -Wl,-soname,libdistance.so -I $JAVA_HOME/include -I $JAVA_HOME/include/linux src/demo6/distance.c
