#!/bin/bash
JAVA_HOME=/usr/java/jdk1.7.0_51
gcc -O2 -fPIC -shared -olib/libargs.so -Wl,-soname,libargs.so -I $JAVA_HOME/include -I $JAVA_HOME/include/linux src/demo2/args.c
