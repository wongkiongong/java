#!/bin/bash

CURRENT_HOME=`dirname $0`
cd $CURRENT_HOME;
cd ..;
PROGRAM_HOME=${PWD}
echo "CURRENT_HOME:"$CURRENT_HOME
echo "PROGRAM_HOME:"$PROGRAM_HOME

#CLASSPATH=$RPOGRAM_HOME/bin;
CLASSPATH=/home/huangqg/eclipse-mar/workspace/JSoupCollector/bin
echo "CLASSPATH:"$CLASSPATH
for f in $PROGRAM_HOME/lib/*.jar; do
    CLASSPATH=$CLASSPATH:$f;
done

for f in $PROGRAM_HOME/lib/spring/*.jar; do
    CLASSPATH=$CLASSPATH:$f;
done
#echo "CLASSPATH:"$CLASSPATH
CLASS="com.onecloud.huangqg.JSoupCollector";

echo /usr/lib/jvm/java-7-openjdk-amd64/bin/java -Dfile.encoding=UTF-8 -classpath $CLASSPATH $CLASS "$@"
/usr/lib/jvm/java-7-openjdk-amd64/bin/java -Dfile.encoding=UTF-8 -classpath $CLASSPATH $CLASS "$@"
