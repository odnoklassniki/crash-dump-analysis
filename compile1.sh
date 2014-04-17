#!/bin/bash
JAVA_HOME=/usr/java/jdk1.7.0_51
gcc -O2 -fPIC -shared -olib/libdiv.so -Wl,-soname,libdiv.so -I $JAVA_HOME/include -I $JAVA_HOME/include/linux src/demo1/div.c
