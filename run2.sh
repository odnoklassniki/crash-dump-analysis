#!/bin/bash
JAVA_HOME=/usr/java/jdk1.6.0_13
$JAVA_HOME/bin/java -Xmx3200M -cp out/production/crash -XX:ErrorFile=src/demo2/hs_err_pid2285.log $* demo2.LongHashSet
