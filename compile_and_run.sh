#!/bin/sh
mvn clean package
java -jar deployer/target/deployer-1.0-jar-with-dependencies.jar -deploy deployer/deployer_config.json
