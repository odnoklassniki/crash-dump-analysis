#!/bin/bash
java -cp out/production/crash -XX:ErrorFile=src/demo3/hs_err_pid2315.log $* demo3.MappedFile
