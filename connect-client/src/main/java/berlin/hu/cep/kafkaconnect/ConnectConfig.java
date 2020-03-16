package berlin.hu.cep.kafkaconnect;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConnectConfig
{
    public String kafka_host;
    public String zeebe_client_broker_contactPoint;
    public List<SourceConfig> source_configs;
    public List<SinkConfig> sink_configs;
}
