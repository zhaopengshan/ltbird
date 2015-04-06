#!/bin/sh
JAVA_HOME=/var/mas/jdk/
export PATH=$JAVA_HOME/bin:$PATH
export CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar:/var/senderApp/senderApp_fat.jar
$JAVA_HOME/bin/java   -jar /var/senderApp/senderApp_fat.jar

