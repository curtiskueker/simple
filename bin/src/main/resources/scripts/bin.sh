#!/bin/bash

JAR_DIR=/home/curtis/.m2/repository/org/curtis/bin/1.0-SNAPSHOT

CLASSPATH=${JAR_DIR}/bin-1.0-SNAPSHOT.jar

java -classpath ${CLASSPATH} -Dnet.sf.ehcache.enableShutdownHook=true org.curtis.bin.Scratch

