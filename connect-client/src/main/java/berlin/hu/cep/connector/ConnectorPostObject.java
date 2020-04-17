package berlin.hu.cep.connector;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * A class which can be send to <strong>Kafka Connect</strong> as a json-object trough a POST method.
 *
 * @see <a href="https://docs.confluent.io/current/connect/references/restapi.html#post--connectors">The POST method of the Kafka Connect REST Interface</a>
 * @author Lukas Gehring
 * @author Leon Haussknecht
 * @author Maurice Lindner
 * @author Jost Hermann Triller
 * @author Stefan Zabka
 *
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
     * The name of the connector in <strong>Kafka Connect</strong>.
     *
     * @return the name of the connector
     */
    public String getName() {
        return name;
    }

    /**
     * The configuration parameters for this <strong>Kafka Connect</strong> connector.
     *
     * @return the configuration parameters
     */
    public ConnectorConfig getConfig() {
        return config;
    }
}
