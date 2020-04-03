package berlin.hu.cep.kafkaconnect;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConnectorPostObject
{
    private String name;
    private ConnectorConfig config;

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