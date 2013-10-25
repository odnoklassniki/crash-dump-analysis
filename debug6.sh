#!/bin/bash
export LD_LIBRARY_PATH=$PWD/lib
java -XX:CompileCommandFile=src/demo6/.hotspot_compiler -cp out/production/crash $* demo6.Malloc
