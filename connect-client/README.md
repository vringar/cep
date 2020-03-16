# Connect client

Writing a Java client, using the REST-Api https://docs.confluent.io/current/connect/references/restapi.html#

Beispielprogramm:

```
String json = "{...}";
ObjectMapper object_mapper = new ObjectMapper();
Config config = object_mapper.readValue(json, Config.class);
ConnectClient cc = new ConnectClient(config);
cc.deploy();
cc.delete();
```

Exemplary json that needs to be mapped into `Config`:
```
{
    "kafka_host": "http://localhost:8083/",
    "zeebe_client_broker_contactPoint": "zeebe:26500",
    "source_configs":
    [
        {
            "name": "some_ping_name",
            ...// all other fields have sensible defaults
        }
        ,
        ...// normally one source_config for all messages should be ok 
    ],  
    "sink_configs":
    [
        {
            "name": "some_pong_name",
            "topics": "some_topic_for_me",
            "message_path_messageName": "$.variablesAsMap.name",
            "message_path_correlationKey": "$.variablesAsMap.key",
            "message_path_variables": "$.variablesAsMap.payload",
            "message_path_timeToLive": "$.variablesAsMap.ttl",
            ...// all other fields have sensible defaults
        }
        ,
        ...
    ]
}
```

If not specified, a bpmn file needs to have the following values to send a message:
```
<zeebe:taskDefinition type="sendMessage" />
<zeebe:taskHeaders>
    <zeebe:header key="kafka-topic" value="here_is_the_kafka_topic_that_this_message_will_be_written_to" />
</zeebe:taskHeaders>
```
