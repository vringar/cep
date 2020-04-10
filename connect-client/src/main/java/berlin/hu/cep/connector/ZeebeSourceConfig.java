package berlin.hu.cep.connector;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A class to configure a <em>Zeebe Source Connector</em> with sane defaults.
 * <p>The connector is designed to send simple events to a <strong>Siddhi</strong>instace for further processing.</p>
 * <p>It also holds the attribute 'mongoDB_logging' which is not a configuration property of a <em>Zeebe Source Connetor</em>. 'mongoDB_logging' is a way to enable logging in a <strong>MongoDB</strong> for the events which are sent from this source connector.</p>
 *<p> An object of this class can be converted to a properties file for the <em>Zeebe Source Connector</em> in json with the<a href="https://fasterxml.github.io/jackson-databind/javadoc/2.7/com/fasterxml/jackson/databind/ObjectMapper.html">Jackson ObjectMapper</a>.</p>
 *
 * @see <a href="https://github.com/zeebe-io/kafka-connect-zeebe">Kafka Connect connector for Zeebe</a>
 * @author Lukas Gehring
 * @author Leon Haussknecht
 * @author Maurice Lindner
 * @author Jost Hermann Triller
 * @author Stefan Zabka
 * */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ZeebeSourceConfig extends ZeebeConfig
{
    private String zeebe_client_job_worker = "kafka-connector";
    private int zeebe_client_worker_maxJobsActive = 100;
    private int zeebe_client_job_pollinterval = 2000;
    private int zeebe_client_job_timeout = 5000;
    private String job_types = "sendMessage";
    private String job_header_topics = "kafka-topic";

    /**
     * Constructor for ZeebeSourceConfig.
     * Note that since this class has some assumptions not all configuration properties provided by the official <em>Zeebe Source Connector</em> can be set here.
     * @param name name of the connector.
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
        super("io.zeebe.kafka.connect.ZeebeSourceConnector", name, mongoDB_logging);
        this.job_types = job_types;
        this.job_header_topics = job_header_topics;
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
}
