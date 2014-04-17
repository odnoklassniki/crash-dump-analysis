#!/bin/bash
JAVA_HOME=/usr/java/jdk1.6.0_13
$JAVA_HOME/bin/java -Xmx1g -Xms1g -XX:+UseConcMarkSweepGC -XX:+UseParNewGC -cp out/production/crash demo5.ClientServer
