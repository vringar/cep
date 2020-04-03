#!/bin/sh
mvn clean package
java -jar deployer/target/deployer-0.0-SNAPSHOT-jar-with-dependencies.jar -deploy deployer/deployer_config.json
