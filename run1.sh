#!/bin/bash
java -Djava.library.path=$PWD/lib -cp out/production/crash -XX:ErrorFile=src/demo1/hs_err_pid2255.log $* demo1.NativeDiv
