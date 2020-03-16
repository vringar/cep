#!/bin/bash

RELATIVE_SIDDHI_CLIENT_HOME="../siddhi-client"
RELATIVE_CONNECT_CLIENT_HOME="../connect-client"
ABSOLUT_RUN_DIR=$(pwd)

cd $ABSOLUT_RUN_DIR/$RELATIVE_SIDDHI_CLIENT_HOME
echo "changed directory to:"
pwd
mvn clean package
cd $ABSOLUT_RUN_DIR/$RELATIVE_CONNECT_CLIENT_HOME
echo "changed directory to:"
pwd
mvn clean package assembly:single

cd $ABSOLUT_RUN_DIR
echo "changed directory to:"
pwd
mvn org.apache.maven.plugins:maven-install-plugin:2.3.1:install-file -Dfile=$ABSOLUT_RUN_DIR/$RELATIVE_CONNECT_CLIENT_HOME/target/kafkaconnect-0.0-SNAPSHOT-jar-with-dependencies.jar -DgroupId=berlin.hu.cep -DartifactId=kafkaconnect -Dversion=0.0 -Dpackaging=jar
mvn org.apache.maven.plugins:maven-install-plugin:2.3.1:install-file -Dfile=$ABSOLUT_RUN_DIR/$RELATIVE_SIDDHI_CLIENT_HOME/target/SiddhiRestClient-1.0-SNAPSHOT.jar -DgroupId=berlin.hu.cep -DartifactId=siddhi-client -Dversion=1.0 -Dpackaging=jar

mvn clean package assembly:single
java -jar target/deployer-1.0-SNAPSHOT-jar-with-dependencies.jar -deploy deployer_config.json
java -jar target/deployer-1.0-SNAPSHOT-jar-with-dependencies.jar -remove deployer_config.json
