package berlin.hu.cep.connectclient;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SourceConfig
{
    public String name;
    public String connector_class = "io.zeebe.kafka.connect.ZeebeSourceConnector";
    public int tasks_max = 1;
    public String key_converter = "org.apache.kafka.connect.json.JsonConverter";
    public boolean key_converter_schemas_enable = false;
    public String value_converter = "org.apache.kafka.connect.json.JsonConverter";
    public boolean value_converter_schemas_enable = false;
    public int zeebe_client_requestTimeout = 10000;
    public boolean zeebe_client_security_plaintext = true;
    public String zeebe_client_job_worker = "kafka-connector";
    public int zeebe_client_worker_maxJobsActive = 100;
    public int zeebe_client_job_pollinterval = 2000;
    public int zeebe_client_job_timeout = 5000;
    public String job_types = "sendMessage";
    public String job_header_topics = "kafka-topic";

    public String getJson(String zeebe_client_broker_contactPoint)
    {
        return "{\n" +
        "  \"name\": \""+name+"\",\n" +
        "  \"config\": {\n" +
        "    \"connector.class\": \""+ connector_class +"\",\n" +
        "    \"tasks.max\": \""+tasks_max+"\",\n" +
        "    \"key.converter\": \""+key_converter+"\",\n" +
        "    \"key.converter.schemas.enable\": "+key_converter_schemas_enable+",\n" +
        "    \"value.converter\": \""+value_converter+"\",\n" +
        "    \"value.converter.schemas.enable\": "+value_converter_schemas_enable+",\n" +
        "    \"zeebe.client.broker.contactPoint\": \""+zeebe_client_broker_contactPoint+"\",\n" +
        "    \"zeebe.client.requestTimeout\": \""+zeebe_client_requestTimeout+"\",\n" +
        "    \"zeebe.client.security.plaintext\": "+zeebe_client_security_plaintext+",\n" +
        "    \"zeebe.client.job.worker\": \""+zeebe_client_job_worker+"\",\n" +
        "    \"zeebe.client.worker.maxJobsActive\": \""+zeebe_client_worker_maxJobsActive+"\",\n" +
        "    \"zeebe.client.job.pollinterval\": \""+zeebe_client_job_pollinterval+"\",\n" +
        "    \"zeebe.client.job.timeout\": \""+zeebe_client_job_timeout+"\",\n" +
        "    \"job.types\": \""+job_types+"\",\n" +
        "    \"job.header.topics\": \""+job_header_topics+"\"\n" +
        "  }\n" +
        "}";
    }
}
