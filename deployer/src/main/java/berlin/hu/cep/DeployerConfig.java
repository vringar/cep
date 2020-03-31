package berlin.hu.cep;

import berlin.hu.cep.kafkaconnect.*;
import berlin.hu.cep.siddhi.*;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeployerConfig
{
    private ConnectClient kafkaconnect_config;
    private SiddhiClientConfig siddhi_config;

    public DeployerConfig(ConnectClient kafkaconnect_config, SiddhiClientConfig siddhi_config) {
        this.kafkaconnect_config = kafkaconnect_config;
        this.siddhi_config = siddhi_config;
    }

    public ConnectClient getKafkaconnect_config() {
        return kafkaconnect_config;
    }

    public SiddhiClientConfig getSiddhi_config() {
        return siddhi_config;
    }
}
