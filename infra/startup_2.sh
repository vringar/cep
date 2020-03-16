#!/bin/sh

ZEEBECLIENT_HOME=../zeebe-client
TERMINAL_EMULATOR=konsole

set -e
export HOSTNAME_COMMAND=172.168.2.111
docker-compose -f docker-compose.yml up -d
# sleep 180
java -jar ../deployer/target/deployer-1.0-SNAPSHOT-jar-with-dependencies.jar sampleFiles/secretRoom/deployer_config.json
# Hier zu client directory navigieren
cd $ZEEBECLIENT_HOME
java -jar target/zeebe-get-started-java-client-1.0-SNAPSHOT-jar-with-dependencies.jar &
# Manually type: kafka-console-consumer --bootstrap-server localhost:9092 --topic toSiddhi
$TERMINAL_EMULATOR -e "docker exec -it infra_kafka_1 bash" &
# Manually type: kafka-console-consumer --bootstrap-server localhost:9092 --topic toZeebe
$TERMINAL_EMULATOR -e "docker exec -it infra_kafka_1  bash" &
$TERMINAL_EMULATOR -e "docker logs -f infra_connect_1" &
