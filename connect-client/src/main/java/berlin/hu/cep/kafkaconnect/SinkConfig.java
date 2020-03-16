package berlin.hu.cep.kafkaconnect;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SinkConfig
{
    public String name;
    public String connector_class = "io.zeebe.kafka.connect.ZeebeSinkConnector";
    public int tasks_max = 1;
    public String key_converter = "org.apache.kafka.connect.json.JsonConverter";
    public boolean key_converter_schemas_enable = false;
    public String value_converter = "org.apache.kafka.connect.json.JsonConverter";
    public boolean value_converter_schemas_enable = false;
    public String topics;
    public int zeebe_client_requestTimeout = 10000;
    public boolean zeebe_client_security_plaintext = true;
    public String message_path_messageName;
    public String message_path_correlationKey;
    public String message_path_variables;
    public String message_path_timeToLive;

    public String getJson(String zeebe_client_broker_contactPoint)
    {
        return
                "{\n" +
                "  \"name\": \""+name+"\",\n" +
                "  \"config\": {\n" +
                "    \"connector.class\": \""+connector_class+"\",\n" +
                "    \"tasks.max\": \""+tasks_max+"\",\n" +
                "    \"key.converter\": \""+key_converter+"\",\n" +
                "    \"key.converter.schemas.enable\": "+key_converter_schemas_enable+",\n" +
                "    \"value.converter\": \""+value_converter+"\",\n" +
                "    \"value.converter.schemas.enable\": "+value_converter_schemas_enable+",\n" +
                "    \"topics\": \""+topics+"\",\n" +
                "    \"zeebe.client.broker.contactPoint\": \""+zeebe_client_broker_contactPoint+"\",\n" +
                "    \"zeebe.client.requestTimeout\": \""+zeebe_client_requestTimeout+"\",\n" +
                "    \"zeebe.client.security.plaintext\": "+zeebe_client_security_plaintext+",\n" +
                "    \"message.path.messageName\": \""+message_path_messageName+"\",\n" +
                "    \"message.path.correlationKey\": \""+message_path_correlationKey+"\",\n" +
                "    \"message.path.variables\": \""+message_path_variables+"\",\n" +
                "    \"message.path.timeToLive\": \""+message_path_timeToLive+"\"\n" +
                "  }\n" +
                "}";
    }
}
