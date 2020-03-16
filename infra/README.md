# Infra

All the setup to get started with zeebe and siddhi.

## Getting started

Get a [`zeebe-client`](https://gitlab.informatik.hu-berlin.de/complexeventprocessing/zeebeclient), build `MvnFixup`
hopefully soon just `master` and then edit and run `./startup.sh` wait a bit and then retry until `curl`
doesn't return an error and then everything should be set up.

Type the `kafka-console-consumer` commands into the kafka shell which opened, to see the ping-pong happen.
A final `java -jar target/zeebe-get-started-java-client-1.0-SNAPSHOT-jar-with-dependencies.jar 123455` will finally start the ping-pong.


## Rambelings on 2020-01-03

What is currently working:
 - The demo provided in the Kafka Connect Zeebe repository
 - The Siddhi runner is starting
 - Siddhi Kafka Integration (Look at sampleFiles/KafkaTest.siddhi to see an example and industructions)
 - Roundtrip

What I need to get working:
 - Zeebe Kafka exporter (Do we really want that)

Observation:

The Zeebe Kafka exporter seems to dump out everything as a protobuf which siddhi doesn't understand natively.
Maybe there is more research to be done there as it might be desireable to capture the full event log of Zeebe.

