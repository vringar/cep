#!/bin/bash
mvn clean package assembly:single && java -jar target/kafkaconnect-0.0-SNAPSHOT-jar-with-dependencies.jar
