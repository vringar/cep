package berlin.hu.cep;

import berlin.hu.cep.connectclient.*;
import berlin.hu.cep.siddhi.*;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeployerConfig
{
    public ConnectConfig kafkaconnect_config;
    public SiddhiClientConfig siddhi_config;
}
