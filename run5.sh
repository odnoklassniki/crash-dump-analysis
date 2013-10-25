#!/bin/bash
java -Djava.library.path=$PWD/lib -cp out/production/crash -XX:ErrorFile=src/demo5/hs_err_pid2372.log $* demo5.NativeDistance
