# Deployer

This is a all-in-one deployer to set up a Zeebe-Kafka-Siddhi connection.

#### Compilation
If you don't have `./target/deployer-1.0-SNAPSHOT-jat-with-dependencies.jar` available you can simply run
```
./compile_an_run.sh
```
to compile the sources. For that to work you have to have the project folders of the `connect-client` and of the `siddhi--client` in the parent directory of the deployer folder.

#### Usage

```
java -jar ./target/deployer-1.0-SNAPSHOT-jat-with-dependencies.jar -deploy config.json
```
where `config.json` looks like this:
```
{
     "kafkaconnect_config":
     {
       "kafka_host": "http://localhost:8083/",
       "zeebe_client_broker_contactPoint": "zeebe:26500",
       "source_configs":
       [
         {
           "name": "ping",
           "job_types": "ping",
           "job_header_topics": "topic"
           //, ... all other settings can be configured also but
           // have sensible defaults
         }
         //, ... as many source configs as you want
       ],
       "sink_configs":
       [
         {
           "name": "pong",
           "topics": "toZeebe",
           "message_path_messageName": "$.name",
           "message_path_correlationKey": "$.key",
           "message_path_variables": "$.payload",
           "message_path_timeToLive": "$.ttl"
           //, ... all other settings can be configured but
           // have sensible defaults
         }
         //, ... as many sink configs as you want
       ]
     },
     "siddhi_config":
     {
       "hostaddress": "https://localhost:9443/",
       "siddhi-files":
       [
         "sampleFiles/pingpong/KafkaTest.siddhi"
         //, ... as many siddhi files as you want
       ]
     }
   }
```

To remove a deployed configuration replace `-deploy` with `-remove`