#!/bin/bash
#!/bin/sh
if [ -d "target" ]; then
    mv "target" "target`date +'%Y%m%d%H%M%S'`"
fi

mvn package 
