package berlin.hu.cep.connector;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.LinkedList;


/**
 * A class to configure a <em>MongoDBSinkConnector</em> for <strong>Kafka Connect</strong> with sane defaults.
 * <p>The configuration is designed so that all communication between a <strong>Siddhi</strong>-
 * and a <strong>Zeebe</strong>-instance is logged in a <strong>MongoDB</strong>database.</p>
 *
 * @see <a href="https://docs.mongodb.com/kafka-connector/current/kafka-sink/">MongoDB Kafka Sink Connector Documentation</a>
 * */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MongoDBConnectConfig extends ConnectorConfig
{
    private String connector_class = "com.mongodb.kafka.connect.MongoSinkConnector";
    private int tasks_max = 1;
    private String connection_uri = "mongodb://mongo:27017";
    private List<String> topics;
    private String database;
    private String collection;
    private String key_converter = "org.apache.kafka.connect.json.JsonConverter";
    private boolean key_converter_schemas_enable = false;
    private String value_converter = "org.apache.kafka.connect.json.JsonConverter";
    private boolean value_converter_schemas_enable = false;

    /**
     * Constructor for MongoDBConnectConfig.
     * Note that since this class has some assumptions not all configuration properties provided by the official <em>MongoDBSinkConnector</em> can be set here.
     *
     * @see <a href="https://docs.mongodb.com/kafka-connector/current/kafka-sink-properties/">MongoDB Kafka Sink Connector Properties</a>
     * @param connection_uri The uri of the <strong>MongoDB</strong>-instance.
     * @param topic The <strong>Kafka</strong>topic which should be logged in the database.
     * @param database The name of the database the sink writes to
     * @param collection Sink MongoDB collection name to write to.
     * */
    public MongoDBConnectConfig(String connection_uri, String topic,String database, String collection){
        this.topics = new LinkedList<String>();
        this.topics.add(topic);
        this.database = database;
        this.connection_uri = connection_uri;
        this.collection = collection;
    }

    /**
     * The Topic is that <strong>Kafka</strong>topic name the connector listens to.
     * This class is designed to listen to only one topic. The attribute is only named topics to be compatible with the official sink connector.
     * @return the <strong>Kafka</strong>topic the connector listens to.*/
    public String getTopics(){
        return String.join(",", topics);
    }

    /**
     * The connector class is the name of the class which implements the <strong>Kafka Connector</strong>.
     * In this case it is always "com.mongodb.kafka.connect.MongoSinkConnector"
     * @return the connector_class
     */
     // TODO: Can this be in the abstract class?
    @JsonGetter("connector.class")
    public String getConnector_class() {
        return connector_class;
    }

    /**
     * The uri of the <strong>MongoDB</strong> instance.
     */
    @JsonGetter("connection.uri")
    public String getConnection_uri() {
        return connection_uri;
    }

    /**
     * TODO: What does this do? Can it be in the abstract class?
     * @return the tasks_max
     */
    @JsonGetter("tasks.max")
    public int getTasks_max() {
        return tasks_max;
    }

    /**
     * The name of the <strong>MongoDB</strong>database to which the sinkconnector writes.
     */
    public String getDatabase() {
        return database;
    }

    /**
     * The name of the <strong>MongoDB</strong>collection to which the sinkconnector writes.
     */
    public String getCollection() {
        return collection;
    }

    /**
     * TODO: What does it do? Can it be in the abstract class?
     * @return the key_converter
     */
    @JsonGetter("key.converter")
    public String getKey_converter() {
        return key_converter;
    }

    /**
     * TODO: What does it do? Can it be in the abstract class?
     * @return the value_converter
     */
    @JsonGetter("value.converter")
    public String getValue_converter() {
        return value_converter;
    }

    /**
     * TODO: What does it do? Can it be in the abstract class?
     * @return the key_converter_schemas_enable
     */
    @JsonGetter("key.converter.schemas.enable")
    public boolean isKey_converter_schemas_enable() {
        return key_converter_schemas_enable;
    }

    /**
     * TODO: What does it do? Can it be in the abstract class?
     * @return the value_converter_schemas_enable
     */
    @JsonGetter("value.converter.schemas.enable")
    public boolean isValue_converter_schemas_enable() {
        return value_converter_schemas_enable;
    }
}
