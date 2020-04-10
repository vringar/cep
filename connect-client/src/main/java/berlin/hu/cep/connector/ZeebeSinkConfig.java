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
 *<p> An object of this class can be converted to a properties file for the Zeebe Sink Connector in json <a href="https://fasterxml.github.io/jackson-databind/javadoc/2.7/com/fasterxml/jackson/databind/ObjectMapper.html">Jackson ObjectMapper</a>.</p>
 *
 * @see <a href="https://github.com/zeebe-io/kafka-connect-zeebe">Home of the Kafka Connect connector for Zeebe</a>
 * @author Lukas Gehring
 * @author Leon Haussknecht
 * @author Maurice Lindner
 * @author Jost Hermann Triller
 * @author Stefan Zabka
 * */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ZeebeSinkConfig extends ZeebeConfig
{
    private String topics;
    private String message_path_messageName;
    private String message_path_correlationKey;
    private String message_path_variables;
    private String message_path_timeToLive;

    /**
     * Constructor for ZeebeSinkConfig.
     * Note that since this class has some assumptions not all configuration properties provided by the official <em>Zeebe Sink Connector</em> can be set here.
     * @param name
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
        super("io.zeebe.kafka.connect.ZeebeSinkConnector", name, mongoDB_logging);
        this.message_path_messageName= message_path_messageName;
        this.message_path_correlationKey = message_path_correlationKey;
        this.message_path_variables = message_path_variables;
        this.message_path_timeToLive = message_path_timeToLive;
        this.topics = topics;
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
     * JSONPath query to use to extract the message name from the record
     * @return JSONPath query for the message name
     */
    @JsonGetter("message.path.messageName")
    public String getMessage_path_messageName() {
        return message_path_messageName;
    }

    /**
     * JSONPath query to use to extract the correlation key from the record
     * @JSONPath query for the correlation key
     */
    @JsonGetter("message.path.correlationKey")
    public String getMessage_path_correlationKey() {
        return message_path_correlationKey;
    }

    /**
     * JSONPath query to use to extract the variables from the record
     * @JSONPath query for variables from the record
     */
    @JsonGetter("message.path.variables")
    public String getMessage_path_variables() {
        return message_path_variables;
    }

    /**
     * JSONPath query to use to extract the time to live from the record
     * @return JSONPath query for time to live
     */
    @JsonGetter("message.path.timeToLive")
    public String getMessage_path_timeToLive() {
        return message_path_timeToLive;
    }
}
