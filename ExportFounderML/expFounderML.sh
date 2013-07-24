#!/bin/sh

JAVA_HOME=$JAVA_HOME
for i in lib/*.jar SourceSys/*.jar
do
CLASSPATH=$PWD/$i:$CLASSPATH
done

export CLASSPATH=.:./SourceSys:./conf:$CLASSPATH
$JAVA_HOME/bin/java -classpath $CLASSPATH system.controller.Application 
