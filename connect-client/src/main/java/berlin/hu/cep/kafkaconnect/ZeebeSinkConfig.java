package berlin.hu.cep.kafkaconnect;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ZeebeSinkConfig extends ConnectorConfig
{
    private String name;
    private String connector_class = "io.zeebe.kafka.connect.ZeebeSinkConnector";
    private int tasks_max = 1;
    private String key_converter = "org.apache.kafka.connect.json.JsonConverter";
    private boolean key_converter_schemas_enable = false;
    private String value_converter = "org.apache.kafka.connect.json.JsonConverter";
    private boolean value_converter_schemas_enable = false;
    private String topics;
    private int zeebe_client_requestTimeout = 10000;
    private boolean zeebe_client_security_plaintext = true;
    private String message_path_messageName;
    private String message_path_correlationKey;
    private String message_path_variables;
    private String message_path_timeToLive;
    private boolean mongoDB_logging = false;
    private String zeebe_client_broker_contactPoint;

    @JsonCreator
    public ZeebeSinkConfig(
            @JsonProperty("name") String name,
            @JsonProperty("mongoDB_logging") boolean mongoDB_logging,
            @JsonProperty("message_path_messageName") String message_path_messageName,
            @JsonProperty("message_path_correlationKey") String message_path_correlationKey,
            @JsonProperty("message_path_variables") String message_path_variables,
            @JsonProperty("message_path_timeToLive") String message_path_timeToLive,
            @JsonProperty("topics") String topics) {
        this.name = name;
        this.mongoDB_logging = mongoDB_logging;
        this.message_path_messageName = message_path_messageName;
        this.message_path_correlationKey = message_path_correlationKey;
        this.message_path_variables = message_path_variables;
        this.message_path_timeToLive = message_path_timeToLive;
        this.topics = topics;
    }

    @JsonGetter("connector.class")
    public String getConnector_class() {
        return connector_class;
    }

    @JsonGetter("tasks.max")
    public int getTasks_max() {
        return tasks_max;
    }

    @JsonGetter("key.converter")
    public String getKey_converter() {
        return key_converter;
    }

    @JsonGetter("key.converter.schemas.enable")
    public boolean isKey_converter_schemas_enable() {
        return key_converter_schemas_enable;
    }

    @JsonGetter("value.converter")
    public String getValue_converter() {
        return value_converter;
    }

    @JsonGetter("value.converter.schemas.enable")
    public boolean isValue_converter_schemas_enable() {
        return value_converter_schemas_enable;
    }

    public String getTopics() {
        return topics;
    }

    @JsonGetter("zeebe.client.broker.contactPoint")
    public String getZeebe_client_broker_contactPoint() {
        return zeebe_client_broker_contactPoint;
    }

    public void setZeebe_client_broker_contactPoint(String zeebe_client_broker_contactPoint) {
        this.zeebe_client_broker_contactPoint = zeebe_client_broker_contactPoint;
    }

    @JsonGetter("zeebe.client.requestTimeout")
    public int getZeebe_client_requestTimeout() {
        return zeebe_client_requestTimeout;
    }

    @JsonGetter("zeebe.client.security.plaintext")
    public boolean isZeebe_client_security_plaintext() {
        return zeebe_client_security_plaintext;
    }

    @JsonGetter("message.path.messageName")
    public String getMessage_path_messageName() {
        return message_path_messageName;
    }

    @JsonGetter("message.path.correlationKey")
    public String getMessage_path_correlationKey() {
        return message_path_correlationKey;
    }

    @JsonGetter("message.path.variables")
    public String getMessage_path_variables() {
        return message_path_variables;
    }

    @JsonGetter("message.path.timeToLive")
    public String getMessage_path_timeToLive() {
        return message_path_timeToLive;
    }

    @JsonIgnore
    public boolean isMongoDB_logging() {
        return mongoDB_logging;
    }

    @JsonIgnore
    public String getName() {
        return name;
    }

}
