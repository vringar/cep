package berlin.hu.cep;

import berlin.hu.cep.connector.*;
import berlin.hu.cep.siddhi.*;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeployerConfig
{
    private ConnectClient connector_config;
    private SiddhiClientConfig siddhi_config;

    @JsonCreator
    public DeployerConfig(
            @JsonProperty("connector_config") ConnectClient connector_config,
            @JsonProperty("siddhi_config") SiddhiClientConfig siddhi_config) {
        this.connector_config = connector_config;
        this.siddhi_config = siddhi_config;
    }

    public ConnectClient getKafkaconnect_config() {
        return connector_config;
    }

    public SiddhiClientConfig getSiddhi_config() {
        return siddhi_config;
    }
}
