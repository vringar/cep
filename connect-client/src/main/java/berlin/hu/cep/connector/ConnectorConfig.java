package berlin.hu.cep.connector;

import com.fasterxml.jackson.annotation.JsonGetter;

/**
 * An abstract class for <strong>Kafka Connect</strong> configurations.
 * <p>As an attribute of a @{@link berlin.hu.cep.connector.ConnectorPostObject ConnectorPostObject} it can be send to a <strong>Kafka Connect</strong>instance
 * via a POST-methode of the <strong>Kafka Connect</strong>-REST-API.</p>
 *
 * @author Lukas Gehring
 * @author Leon Haussknecht
 * @author Maurice Lindner
 * @author Jost Hermann Triller
 * @author Stefan Zabka
 *
 * @see <a href="https://docs.confluent.io/current/connect/index.html">Kafka Connect Dokumentation</a>
 * */
public abstract class ConnectorConfig
{
    private String connector_class;
    private int tasks_max = 1;
    protected String key_converter = "org.apache.kafka.connect.json.JsonConverter";
    private boolean key_converter_schemas_enable = false;
    protected String value_converter = "org.apache.kafka.connect.json.JsonConverter";
    private boolean value_converter_schemas_enable = false;

    /**
     * The Constructor of the ConnectorConfig Class.
     * @param connector_class The name of the connectorclass where the <strong>Kafka Connect</strong> connector is implemented.
     */
    public ConnectorConfig(String connector_class) {
        this.connector_class = connector_class;
    }

    /**
     * The connector class is the name of the class which implements the <strong>Kafka Connector</strong>.
     * In this case it is always "com.mongodb.kafka.connect.MongoSinkConnector"
     * @return the connector_class
     */
    @JsonGetter("connector.class")
    public String getConnector_class() {
        return connector_class;
    }

    /**
     * Returns tasks.max (the maximum number of tasks that should be created for this connector. The connector may create fewer tasks if it cannot achieve this level of parallelism)
     * (https://docs.confluent.io/current/connect/managing/configuring.html)
     * @return the tasks_max
     */
    @JsonGetter("tasks.max")
    public int getTasks_max() {
        return tasks_max;
    }

    /**
     * Returns key.converter (Converter class for key Connect data. This controls the format of the data that will be written to Kafka for source connectors or read from Kafka for sink connectors. Popular formats include Avro and JSON.)
     * (https://docs.confluent.io/current/connect/references/allconfigs.html#connect-allconfigs)
     * @return the key_converter
     */
    @JsonGetter("key.converter")
    public String getKey_converter() {
        return key_converter;
    }

    /**
     * Returns value.converter (Converter class for value Connect data. This controls the format of the data that will be written to Kafka for source connectors or read from Kafka for sink connectors. Popular formats include Avro and JSON.)
     * (https://docs.confluent.io/current/connect/references/allconfigs.html#connect-allconfigs)
     * @return the value_converter
     */
    @JsonGetter("value.converter")
    public String getValue_converter() {
        return value_converter;
    }

    /**
     * Returns whether the key converter has JSON schemas enabled.
     * @return the key_converter_schemas_enable
     */
    @JsonGetter("key.converter.schemas.enable")
    public boolean isKey_converter_schemas_enable() {
        return key_converter_schemas_enable;
    }

    /**
     * Returns whether the value converter has JSON schemas enabled.
     * @return the value_converter_schemas_enable
     */
    @JsonGetter("value.converter.schemas.enable")
    public boolean isValue_converter_schemas_enable() {
        return value_converter_schemas_enable;
    }

}
