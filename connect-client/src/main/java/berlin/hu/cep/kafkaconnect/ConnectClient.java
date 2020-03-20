package berlin.hu.cep.kafkaconnect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonInclude;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConnectClient
{
    public String connector_host;
    public String connector_port;
    public String mongoDB_url;
    public String mongoDB_database;
    public String zeebe_client_broker_contactPoint;
    public List<SourceConfig> source_configs;
    public List<SinkConfig> sink_configs;

    private String get_connector_url() {
        return "http://" + connector_host + ":" + connector_port;
    }

    public void deploy() throws Exception
    {
        for(SinkConfig sink_config : sink_configs){
            post(sink_config.getJson(zeebe_client_broker_contactPoint), get_connector_url());
            if(sink_config.mongoDB_logging){
                MongoDBConnectConfig mongo_config = new MongoDBConnectConfig(
                        sink_config.name + "-LOGGING",
                        mongoDB_url,
                        sink_config.topics,
                        mongoDB_database,
                        sink_config.name);
                // post(mongo_config.getJson(), get_connector_url());
            }
        }
        for(SourceConfig source_config : source_configs){
            post(source_config.getJson(zeebe_client_broker_contactPoint), get_connector_url());
            if(source_config.mongoDB_logging){
                MongoDBConnectConfig mongo_config = new MongoDBConnectConfig(
                        source_config.name + "-LOGGING",
                        mongoDB_url,
                        source_config.job_header_topics,
                        mongoDB_database,
                        source_config.name);
                // post(mongo_config.getJson(), get_connector_url());
            }
        }
    }
    public void delete() throws Exception
    {
        for(SinkConfig sink_config : sink_configs){
            delete(sink_config.name, get_connector_url());
            if(sink_config.mongoDB_logging){
                delete(sink_config.name + "-LOGGING", get_connector_url());
            }
        }
        for(SourceConfig source_config : source_configs){
            delete(source_config.name, get_connector_url());
            if(source_config.mongoDB_logging){
                delete(source_config.name + "-LOGGING", get_connector_url());
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
                        "            \"name\": \"some_ping_name\",\n" +
                        "            \"mongoDB_logging\": \"true\"\n" +
                        "        }\n" +
                        "    ],  \n" +
                        "    \"sink_configs\":\n" +
                        "    [\n" +
                        "        {\n" +
                        "            \"name\": \"some_pong_name\",\n" +
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
        MongoDBConnectConfig mongo_config = new MongoDBConnectConfig("name", "dame", "krame", "habe", "sale");
        String r = object_mapper.writeValueAsString(mongo_config);
        System.out.println(r);
        // mongo_config.getJson();
        // cc.deploy();
        // cc.delete();
    }

}
