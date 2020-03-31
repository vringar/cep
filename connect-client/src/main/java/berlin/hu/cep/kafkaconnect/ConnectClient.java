package berlin.hu.cep.kafkaconnect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConnectClient
{
    private String connector_host;
    private int connector_port;
    private String mongoDB_url;
    private String mongoDB_database;
    private String zeebe_client_broker_contactPoint;
    private List<ZeebeSourceConfig> source_configs;
    private List<ZeebeSinkConfig> sink_configs;

    @JsonCreator
    public ConnectClient(
            @JsonProperty("sink_configs") List<ZeebeSinkConfig> sink_configs,
            @JsonProperty("source_configs") List<ZeebeSourceConfig> source_configs,
            @JsonProperty("zeebe_client_broker_contactPoint") String zeebe_client_broker_contactPoint,
            @JsonProperty("connector_host") String connector_host,
            @JsonProperty("connector_port") int connector_port) {
        this.sink_configs = sink_configs;
        this.source_configs = source_configs;
        this.zeebe_client_broker_contactPoint = zeebe_client_broker_contactPoint;
        this.connector_host = connector_host;
        this.connector_port = connector_port;
    }

    @JsonCreator
    public ConnectClient(
            @JsonProperty("connector_host") String connector_host,
            @JsonProperty("connector_port") int connector_port,
            @JsonProperty("mongoDB_url") String mongoDB_url,
            @JsonProperty("mongoDB_database") String mongoDB_database,
            @JsonProperty("zeebe_client_broker_contactPoint") String zeebe_client_broker_contactPoint,
            @JsonProperty("source_configs") List<ZeebeSourceConfig> source_configs,
            @JsonProperty("sink_configs") List<ZeebeSinkConfig> sink_configs) {
        this.connector_host = connector_host;
        this.connector_port = connector_port;
        this.mongoDB_url = mongoDB_url;
        this.mongoDB_database = mongoDB_database;
        this.zeebe_client_broker_contactPoint = zeebe_client_broker_contactPoint;
        this.source_configs = source_configs;
        this.sink_configs = sink_configs;
    }

    private String get_connector_url() {
        return "http://" + connector_host + ":" + connector_port;
    }

    public void deploy() throws Exception {
        ObjectMapper oj = new ObjectMapper();

        //Deploy sink_configs
        for(ZeebeSinkConfig sink_config : sink_configs){
            sink_config.setZeebe_client_broker_contactPoint(zeebe_client_broker_contactPoint);
            ConnectorPostObject config = new ConnectorPostObject(sink_config.getName(), sink_config);
            String config_json = oj.writeValueAsString(config);
            post(config_json, get_connector_url());
            if(sink_config.isMongoDB_logging()){
                MongoDBConnectConfig mongo_config = new MongoDBConnectConfig(
                        mongoDB_url,
                        sink_config.getTopics(),
                        mongoDB_database,
                        config.getName());
                ConnectorPostObject post_object = new ConnectorPostObject(config.getName() + "-LOGGING", mongo_config);
                String json = oj.writeValueAsString(post_object);
                post(json, get_connector_url());
            }
        }

        //Deploy source configs
        for(ZeebeSourceConfig source_config : source_configs){
            source_config.setZeebe_client_broker_contactPoint(zeebe_client_broker_contactPoint);

            ConnectorPostObject config = new ConnectorPostObject(source_config.getName(), source_config);
            String config_json = oj.writeValueAsString(config);
            post(config_json, get_connector_url());

            if(source_config.isMongoDB_logging()){
                MongoDBConnectConfig mongo_config = new MongoDBConnectConfig(
                        mongoDB_url,
                        source_config.getJob_header_topics(),
                        mongoDB_database,
                        config.getName());
                ConnectorPostObject post_object = new ConnectorPostObject(config.getName() + "-LOGGING", mongo_config);
                String json = oj.writeValueAsString(post_object);
                post(json, get_connector_url());
            }
        }
    }


    public void delete() throws Exception
    {
        for(ZeebeSinkConfig sink_config : sink_configs){
            delete(sink_config.getName(), get_connector_url());
            if(sink_config.isMongoDB_logging()){
                delete(sink_config.getName() + "-LOGGING", get_connector_url());
            }
        }
        for(ZeebeSourceConfig source_config : source_configs){
            delete(source_config.getName(), get_connector_url());
            if(source_config.isMongoDB_logging()){
                delete(source_config.getName() + "-LOGGING", get_connector_url());
            }
        }
    }

    private static void post(String json, String connector_url)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(connector_url)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        RestAPI restAPI = retrofit.create(RestAPI.class);

        try {
            Call<String> call = restAPI.postJson(json);
            Response<String> response = call.execute();
            String apiResponse = response.body();
            if(apiResponse != null)
            {
                System.out.println(apiResponse);
            }
            else
            {
                System.out.println(response.message());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private static void delete(String name, String connector_url) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(connector_url)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RestAPI restAPI = retrofit.create(RestAPI.class);

        try {
            Call<String> call = restAPI.delete(name);
            Response<String> response = call.execute();
            String apiResponse = response.body();
            if(apiResponse != null)
            {
                System.out.println(apiResponse);
            }
            else
            {
                System.out.println(response.message());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main( String[] args ) throws Exception
    {
        final String json_configuration =
                "{\n" +
                        "\"connector_host\": \"localhost\", \n" +
                        "\"connector_port\": \"8083\", \n" +
                        "\"mongoDB_url\" : \"mongodb://mongo:27017\", \n" +
                        "\"mongoDB_database\" : \"Ping_Pong\", \n" +
                        "    \"zeebe_client_broker_contactPoint\": \"zeebe:26500\",\n" +
                        "    \"source_configs\":\n" +
                        "    [\n" +
                        "        {\n" +
                        "            \"name\": \"Zeebe_source\",\n" +
                        "            \"mongoDB_logging\": \"true\"\n" +
                        "        }\n" +
                        "    ],  \n" +
                        "    \"sink_configs\":\n" +
                        "    [\n" +
                        "        {\n" +
                        "            \"name\": \"Zeebe_sink\",\n" +
                        "            \"mongoDB_logging\": \"true\",\n" +
                        "            \"topics\": \"some_topic_for_me\",\n" +
                        "            \"message_path_messageName\": \"$.variablesAsMap.name\",\n" +
                        "            \"message_path_correlationKey\": \"$.variablesAsMap.key\",\n" +
                        "            \"message_path_variables\": \"$.variablesAsMap.payload\",\n" +
                        "            \"message_path_timeToLive\": \"$.variablesAsMap.ttl\"\n" +
                        "        }\n" +
                        "    ]\n" +
                        "}";

        ObjectMapper object_mapper = new ObjectMapper();
        ConnectClient cc = object_mapper.readValue(json_configuration, ConnectClient.class);
        cc.deploy();
        cc.delete();
    }

}
