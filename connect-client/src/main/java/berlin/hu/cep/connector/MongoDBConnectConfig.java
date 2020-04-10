package berlin.hu.cep.connector;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.LinkedList;

/**
 * A class to configure a <em>MongoDBSinkConnector</em> for <strong>Kafka Connect</strong> with sane defaults.
 * <p>The configuration is designed so that all communication between a <strong>Siddhi</strong>-
 * and a <strong>Zeebe</strong>-instance is logged in a <strong>MongoDB</strong>database.</p>
 * An object of this class can be converted to a properties file for the <em>MongDB Kafka Sink Connector</em> in json with the<a href="https://fasterxml.github.io/jackson-databind/javadoc/2.7/com/fasterxml/jackson/databind/ObjectMapper.html">Jackson ObjectMapper</a>.
 *
 * @see <a href="https://docs.mongodb.com/kafka-connector/current/kafka-sink/">MongoDB Kafka Sink Connector Documentation</a>
 * @author Lukas Gehring
 * @author Leon Haussknecht
 * @author Maurice Lindner
 * @author Jost Hermann Triller
 * @author Stefan Zabka
 * */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MongoDBConnectConfig extends ConnectorConfig
{
    private String connection_uri = "mongodb://mongo:27017";
    private List<String> topics;
    private String database;
    private String collection;

    /**
     * Constructor for MongoDBConnectConfig.
     * Note that since this class has some assumptions not all configuration properties provided by the official <em>MongoDBSinkConnector</em> can be set here.
     *
     * @see <a href="https://docs.mongodb.com/kafka-connector/current/kafka-sink-properties/">MongoDB Kafka Sink Connector Properties</a>
     * @param connection_uri The uri of the <strong>MongoDB</strong>-instance.
     * @param topic The <strong>Kafka</strong>topic which should be logged in the database.
     * @param database The name of the database the sink writes to
     * @param collection MongoDB collection name to write to.
     * */
    public MongoDBConnectConfig(String connection_uri, String topic,String database, String collection){
        super("com.mongodb.kafka.connect.MongoSinkConnector");
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
     * The uri of the <strong>MongoDB</strong> instance.
     * @return <strong>MongoDB</strong>uri to connect to.
     */
    @JsonGetter("connection.uri")
    public String getConnection_uri() {
        return connection_uri;
    }

    /**
     * A <strong>MongoDB</strong>instace has several databases it manages.
     * Databases hold collection of (BSON)documents.
     * @return The name of the <strong>MongoDB</strong>database to log to.
     */
    public String getDatabase() {
        return database;
    }

    /**
     * One <strong>MongoDB</strong>database holds several collection.
     * A collections holds several (BSON)documents.
     * @return <strong>MongoDB</strong>collection to database to log to.
     */
    public String getCollection() {
        return collection;
    }
}
