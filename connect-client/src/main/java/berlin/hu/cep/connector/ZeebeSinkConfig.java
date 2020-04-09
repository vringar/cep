package berlin.hu.cep.connector;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A class to configure a <em>Zeebe Sink Connector</em> with sane defaults.
 * <p>The connector is designed to get complex events from a running <strong>Siddhi</strong>instace</p>
 * <p>It also holds the attribute 'mongoDB_logging' which is not a configuration property of a <em>Zeebe Sink Connetor</em>. 'mongoDB_logging' is a way to enable logging in a <strong>MongoDB</strong> for the events which are sent to this sink connector.</p>
 *<p> An object of this class can be converted to a properties file for the MongDB Kafka Sink Connector in json <a href="https://fasterxml.github.io/jackson-databind/javadoc/2.7/com/fasterxml/jackson/databind/ObjectMapper.html">Jackson ObjectMapper</a>.</p>
 *
 * @see <a href="https://github.com/zeebe-io/kafka-connect-zeebe">Home of the Kafka Connect connector for Zeebe</a>
 * @author Lukas Gehring
 * @author Leon Haussknecht
 * @author Maurice Lindner
 * @author Jost Hermann Triller
 * @author Stefan Zabka
 * */
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


    /**
     * Constructor for ZeebeSinkConfig.
     * Note that since this class has some assumptions not all configuration properties provided by the official <em>Zeebe Sink Connector</em> can be set here.
     * @param name TODO Do we need that? Isn't PostObject enought?
     * @param mongoDB_logging Enables logging of all related events into a <strong>MongDB</strong>database.
     * @param message_path_messageName JSONPath query to use to extract the message name from the record.
     * @param message_path_correlationKey JSONPath query to use to extract the correlation key from the record.
     * @param message_path_variables JSONPath query to use to extract the variables from the record.
     * @param message_path_timeToLive JSONPath query to use to extract the time to live from the record.
     * @param topics The <strong>Kakfa</strong>topic the sink connector listens to.
     */
    @JsonCreator
    public ZeebeSinkConfig(
            @JsonProperty("name") String name,
            @JsonProperty("mongoDB_logging") Boolean mongoDB_logging,
            @JsonProperty("message_path_messageName") String message_path_messageName,
            @JsonProperty("message_path_correlationKey") String message_path_correlationKey,
            @JsonProperty("message_path_variables") String message_path_variables,
            @JsonProperty("message_path_timeToLive") String message_path_timeToLive,
            @JsonProperty("topics") String topics) {
        this.name = name;
        this.mongoDB_logging = mongoDB_logging==null?false:mongoDB_logging;
        this.message_path_messageName = message_path_messageName;
        this.message_path_correlationKey = message_path_correlationKey;
        this.message_path_variables = message_path_variables;
        this.message_path_timeToLive = message_path_timeToLive;
        this.topics = topics;
    }

    //TODO Move to abstract super class
    @JsonGetter("connector.class")
    public String getConnector_class() {
        return connector_class;
    }

    //TODO Move to abstract super class
    @JsonGetter("tasks.max")
    public int getTasks_max() {
        return tasks_max;
    }

    //TODO Move to abstract super class
    @JsonGetter("key.converter")
    public String getKey_converter() {
        return key_converter;
    }

    //TODO Move to abstract super class
    @JsonGetter("key.converter.schemas.enable")
    public boolean isKey_converter_schemas_enable() {
        return key_converter_schemas_enable;
    }

    //TODO Move to abstract super class
    @JsonGetter("value.converter")
    public String getValue_converter() {
        return value_converter;
    }

    //TODO Move to abstract super class
    @JsonGetter("value.converter.schemas.enable")
    public boolean isValue_converter_schemas_enable() {
        return value_converter_schemas_enable;
    }

    /**
     * Returns the topic the sink connector listens to.
     * Note that it is normally just one topic. The attribute is named topics to be compatible with the official <em>Zeebe Sink Connector</em>
     * @return The name of the topic
     */
    public String getTopics() {
        return topics;
    }

    /**
     * The address of the Zeebe broker.
     * @return The address as host:port
     */
    @JsonGetter("zeebe.client.broker.contactPoint")
    public String getZeebe_client_broker_contactPoint() {
        return zeebe_client_broker_contactPoint;
    }

    /**
     * Sets the address of the Zeebe broker to which the sink connector should connect to.
     * @param zeebe_client_broker_contactPoint The broker address as host:port
     */
    public void setZeebe_client_broker_contactPoint(String zeebe_client_broker_contactPoint) {
        this.zeebe_client_broker_contactPoint = zeebe_client_broker_contactPoint;
    }

    /**
     * The timeout for requests to the Zeebe broker.
     * It's value is set to 10 seconds.
     * @return The timeout in milliseconds
     */
    @JsonGetter("zeebe.client.requestTimeout")
    public int getZeebe_client_requestTimeout() {
        return zeebe_client_requestTimeout;
    }

    /**
     * Disables secure connections to the gateway for local development setups.
     * It's value is set to false
     * @return Do connection to gateways are plaintext?
     */
    @JsonGetter("zeebe.client.security.plaintext")
    public boolean isZeebe_client_security_plaintext() {
        return zeebe_client_security_plaintext;
    }

    /**
     * JSONPath query to use to extract the message name from the record
     */
    @JsonGetter("message.path.messageName")
    public String getMessage_path_messageName() {
        return message_path_messageName;
    }

    /**
     * JSONPath query to use to extract the correlation key from the record
     */
    @JsonGetter("message.path.correlationKey")
    public String getMessage_path_correlationKey() {
        return message_path_correlationKey;
    }

    /**
     * JSONPath query to use to extract the variables from the record
     */
    @JsonGetter("message.path.variables")
    public String getMessage_path_variables() {
        return message_path_variables;
    }

    /**
     * JSONPath query to use to extract the time to live from the record
     */
    @JsonGetter("message.path.timeToLive")
    public String getMessage_path_timeToLive() {
        return message_path_timeToLive;
    }

    /**
     * mongoDB_logging tells us if the events for this source connector should be loggend to <strong>MongoDB</strong>.
     * This is not a property of the <em>Zeebe Sink Connector</em>.
     * It will not be written to the json config file.
     * @return if the events for this connector get logged
     */
    @JsonIgnore
    public boolean isMongoDB_logging() {
        return mongoDB_logging;
    }

    //TODO: Do we need that?
    @JsonIgnore
    public String getName() {
        return name;
    }

}
