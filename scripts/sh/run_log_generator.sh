#!/bin/sh

for i in {0..100}
do
   echo "Welcome $i times"
   echo "Generating apache log"

   python ../fake_apache_log_generator/apache-fake-log-gen.py -n 1 -o CONSOLE >> /var/log/fake_apache_access.log

   echo "Sleep 1 secs"
   sleep 1s
   echo "===================================="
done
