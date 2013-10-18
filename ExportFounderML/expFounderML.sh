#!/bin/sh
export JAVA_HOME=/opt/jdk1.6.0_43
JAVA_HOME=$JAVA_HOME
for i in lib/*.jar SourceSys/*.jar
do
CLASSPATH=$PWD/$i:$CLASSPATH
done

export CLASSPATH=.:./SourceSys:./conf:$CLASSPATH
$JAVA_HOME/bin/java -classpath $CLASSPATH system.controller.Application 
