package berlin.hu.cep.connector;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * A class which can be send to <strong>Kafka Connect</strong> as a json-object trought a POST methode.
 *
 * @see <a href="https://docs.confluent.io/current/connect/references/restapi.html#post--connectors">The POST methode of the Kafka Connect REST Interface</a>
 * */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConnectorPostObject
{
    private String name;
    private ConnectorConfig config;

    /**
     * The constructor of {@link ConnectorPostObject}
     *
     * @param name Name of the connector to create
     * @param config Object which holds all configuration parameters for the connector.
     * */
    public ConnectorPostObject(String name, ConnectorConfig config){
        this.name = name;
        this.config = config;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the config
     */
    public ConnectorConfig getConfig() {
        return config;
    }
}
