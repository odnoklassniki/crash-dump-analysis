#!/bin/bash
java -cp out/production/crash -XX:ErrorFile=src/demo6/hs_err_pid2362.log $* demo6.Malloc
