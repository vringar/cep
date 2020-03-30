package berlin.hu.cep.connectclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ConnectClient
{

    public static void main( String[] args ) throws Exception
    {
        String json =
                "{\n" +
                        "    \"kafka_host\": \"http://localhost:8083/\",\n" +
                        "    \"zeebe_client_broker_contactPoint\": \"zeebe:26500\",\n" +
                        "    \"source_configs\":\n" +
                        "    [\n" +
                        "        {\n" +
                        "            \"name\": \"some_ping_name\"\n" +
                        "        }\n" +
                        "    ],  \n" +
                        "    \"sink_configs\":\n" +
                        "    [\n" +
                        "        {\n" +
                        "            \"name\": \"some_pong_name\",\n" +
                        "            \"topics\": \"some_topic_for_me\",\n" +
                        "            \"message_path_messageName\": \"$.variablesAsMap.name\",\n" +
                        "            \"message_path_correlationKey\": \"$.variablesAsMap.key\",\n" +
                        "            \"message_path_variables\": \"$.variablesAsMap.payload\",\n" +
                        "            \"message_path_timeToLive\": \"$.variablesAsMap.ttl\"\n" +
                        "        }\n" +
                        "    ]\n" +
                        "}";

        ObjectMapper object_mapper = new ObjectMapper();
        ConnectConfig config = object_mapper.readValue(json, ConnectConfig.class);
        ConnectClient cc = new ConnectClient(config);
        cc.deploy();
        cc.delete();
    }

    public ConnectClient(ConnectConfig config)
    {
        m_config = config;
    }

    public void deploy() throws Exception
    {
        for(SinkConfig sink_config : m_config.sink_configs)
        {
            post(sink_config.getJson(m_config.zeebe_client_broker_contactPoint), m_config.kafka_host);
        }
        for(SourceConfig source_config : m_config.source_configs)
        {
            post(source_config.getJson(m_config.zeebe_client_broker_contactPoint), m_config.kafka_host);
        }
    }
    public void delete() throws Exception
    {
        for(SinkConfig sink_config : m_config.sink_configs)
        {
            delete(sink_config.name, m_config.kafka_host);
        }
        for(SourceConfig source_config : m_config.source_configs)
        {
            delete(source_config.name, m_config.kafka_host);
        }
    }

    private ConnectConfig m_config;

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


    private static void delete(
            String name,
            String connector_url
    ) {
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
}
