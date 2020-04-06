package berlin.hu.cep.connector;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.LinkedList;

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

    public MongoDBConnectConfig(String connection_uri, String topic,String database, String collection){
        this.topics = new LinkedList<String>();
        this.topics.add(topic);
        this.database = database;
        this.connection_uri = connection_uri;
        this.collection = collection;
    }

    public String getTopics(){
        return String.join(",", topics);
    }

    /**
     * @return the connector_class
     */
    @JsonGetter("connector.class")
    public String getConnector_class() {
        return connector_class;
    }

    /**
     * @return the connection_uri
     */
    @JsonGetter("connection.uri")
    public String getConnection_uri() {
        return connection_uri;
    }

    /**
     * @return the tasks_max
     */
    @JsonGetter("tasks.max")
    public int getTasks_max() {
        return tasks_max;
    }

    /**
     * @return the database
     */
    public String getDatabase() {
        return database;
    }

    /**
     * @return the collection
     */
    public String getCollection() {
        return collection;
    }

    /**
     * @return the key_converter
     */
    @JsonGetter("key.converter")
    public String getKey_converter() {
        return key_converter;
    }

    /**
     * @return the value_converter
     */
    @JsonGetter("value.converter")
    public String getValue_converter() {
        return value_converter;
    }

    /**
     * @return the key_converter_schemas_enable
     */
    @JsonGetter("key.converter.schemas.enable")
    public boolean isKey_converter_schemas_enable() {
        return key_converter_schemas_enable;
    }

    /**
     * @return the value_converter_schemas_enable
     */
    @JsonGetter("value.converter.schemas.enable")
    public boolean isValue_converter_schemas_enable() {
        return value_converter_schemas_enable;
    }
}
