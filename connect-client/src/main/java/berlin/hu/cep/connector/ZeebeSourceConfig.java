package berlin.hu.cep.connector;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A class to configure a <em>Zeebe Source Connector</em> with sane defaults.
 * <p>The connector is designed to send simple events to a <strong>Siddhi</strong>instace for further processing.</p>
 * <p>It also holds the attribute 'mongoDB_logging' which is not a configuration property of a <em>Zeebe Source Connetor</em>. 'mongoDB_logging' is a way to enable logging in a <strong>MongoDB</strong> for the events which are sent from this source connector.</p>
 *<p> An object of this class can be converted to a properties file for the Zeebe Source Connector in json <a href="https://fasterxml.github.io/jackson-databind/javadoc/2.7/com/fasterxml/jackson/databind/ObjectMapper.html">Jackson ObjectMapper</a>.</p>
 *
 * @see <a href="https://github.com/zeebe-io/kafka-connect-zeebe">Home of the Kafka Connect connector for Zeebe</a>
 * @author Lukas Gehring
 * @author Leon Haussknecht
 * @author Maurice Lindner
 * @author Jost Hermann Triller
 * @author Stefan Zabka
 * */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ZeebeSourceConfig extends ConnectorConfig
{
    private String name;
    private String connector_class = "io.zeebe.kafka.connect.ZeebeSourceConnector";
    private int tasks_max = 1;
    private String key_converter = "org.apache.kafka.connect.json.JsonConverter";
    private boolean key_converter_schemas_enable = false;
    private String value_converter = "org.apache.kafka.connect.json.JsonConverter";
    private boolean value_converter_schemas_enable = false;
    private int zeebe_client_requestTimeout = 10000;
    private boolean zeebe_client_security_plaintext = true;
    private String zeebe_client_job_worker = "kafka-connector";
    private int zeebe_client_worker_maxJobsActive = 100;
    private int zeebe_client_job_pollinterval = 2000;
    private int zeebe_client_job_timeout = 5000;
    private String job_types = "sendMessage";
    private String job_header_topics = "kafka-topic";
    private boolean mongoDB_logging = false;
    private String zeebe_client_broker_contactPoint;

    /**
     * Constructor for ZeebeSourceConfig.
     * Note that since this class has some assumptions not all configuration properties provided by the official <em>Zeebe Source Connector</em> can be set here.
     * @param name TODO Do we need that? Isn't PostObject enought?
     * @param mongoDB_logging Enables logging of all related events into a <strong>MongDB</strong>database.
     * @param job_types a comma-separated list of job types that should be consumed by the connector
     * @param job_header_topics the custom service task header which specifies to which <strong>Kafka</strong>topics the message should be published to.
     */
    @JsonCreator
    public ZeebeSourceConfig(
            @JsonProperty("name") String name,
            @JsonProperty("job_types") String job_types,
            @JsonProperty("job_header_topics") String job_header_topics,
            @JsonProperty("mongoDB_logging") Boolean mongoDB_logging) {
        this.name = name;
        this.job_types = job_types;
        this.job_header_topics = job_header_topics;
        this.mongoDB_logging = mongoDB_logging==null?false:mongoDB_logging;
    }

    //TODO Remove
    @JsonIgnore
    public String getName() {
        return name;
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

    /**
     * The timeout for requests to the Zeebe broker.
     * It's value is set to 10 seconds.
     * @return The timeout in milliseconds
     */
    @JsonGetter("zeebe.client.requestTimeout")
    public int getZeebe_client_requestTimeout() {
        return zeebe_client_requestTimeout;
    }

    @JsonGetter("zeebe.client.security.plaintext")
    public boolean isZeebe_client_security_plaintext() {
        return zeebe_client_security_plaintext;
    }

    @JsonGetter("zeebe.client.job.worker")
    public String getZeebe_client_job_worker() {
        return zeebe_client_job_worker;
    }

    @JsonGetter("zeebe.client.worker.maxJobsActive")
    public int getZeebe_client_worker_maxJobsActive() {
        return zeebe_client_worker_maxJobsActive;
    }

    @JsonGetter("zeebe.client.job.pollinterval")
    public int getZeebe_client_job_pollinterval() {
        return zeebe_client_job_pollinterval;
    }

    @JsonGetter("zeebe.client.job.timeout")
    public int getZeebe_client_job_timeout() {
        return zeebe_client_job_timeout;
    }

    /**
     * A comma-separated list of job types that should be consumed by the connector
     * @return Job types that should be consumed
     * */
    @JsonGetter("job.types")
    public String getJob_types() {
        return job_types;
    }

    /**
     * The custom service task header which specifies to which topics the message should be published to
     * @return Which topic the connector should write to
     * */
    @JsonGetter("job.header.topics")
    public String getJob_header_topics() {
        return job_header_topics;
    }

    /**
     * mongoDB_logging tells us if the events for this sink connector should be loggend to <strong>MongoDB</strong>.
     * This is not a property of the <em>Zeebe Sink Connector</em>.
     * It will not be written to the json config file.
     * @return if the events for this connector get logged
     */
    @JsonIgnore
    public boolean isMongoDB_logging() {
        return mongoDB_logging;
    }

    @JsonGetter("zeebe.client.broker.contactPoint")
    public String getZeebe_client_broker_contactPoint() {
        return zeebe_client_broker_contactPoint;
    }

    public void setZeebe_client_broker_contactPoint(String zeebe_client_broker_contactPoint) {
        this.zeebe_client_broker_contactPoint = zeebe_client_broker_contactPoint;
    }


}
