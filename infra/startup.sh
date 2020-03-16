#!/bin/sh

ZEEBECLIENT_HOME=../zeebeclient
TERMINAL_EMULATOR=xterm

set -e
docker-compose -f docker-compose.yml up -d
curl -X POST -H "Content-Type: application/json" --data @sampleFiles/pingpong/source.json http://localhost:8083/connectors
echo
curl -X POST -H "Content-Type: application/json" --data @sampleFiles/pingpong/sink.json http://localhost:8083/connectors
curl -X PUT "https://localhost:9443/siddhi-apps" -H "accept: application/json" -H "Content-Type: text/plain" \
 --data-binary @sampleFiles/pingpong/KafkaTest.siddhi -u admin:admin -k
# Hier zu client directory navigieren
cd $ZEEBECLIENT_HOME
java -jar target/zeebe-get-started-java-client-1.0-SNAPSHOT-jar-with-dependencies.jar
# Manually type: kafka-console-consumer --bootstrap-server localhost:9092 --topic toSiddhi
$TERMINAL_EMULATOR -e "docker exec -it infra_kafka_1 bash" &
# Manually type: kafka-console-consumer --bootstrap-server localhost:9092 --topic toZeebe
$TERMINAL_EMULATOR -e "docker exec -it infra_kafka_1  bash" &
$TERMINAL_EMULATOR -e "docker logs -f infra_connect_1" &
