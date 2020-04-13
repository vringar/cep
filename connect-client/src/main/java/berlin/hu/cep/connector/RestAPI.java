package berlin.hu.cep.connector;

import retrofit2.Call;
import retrofit2.http.*;

/**
 * This interface gets used by Retrofit to provide an HTTP interface
 *
 * @author Lukas Gehring
 * @author Leon Haussknecht
 * @author Maurice Lindner
 * @author Jost Hermann Triller
 * @author Stefan Zabka
 *
 * @see https://square.github.io/retrofit/
 */
public interface RestAPI {

    /**
     * Generate Call instance that allows executing an HTTP POST message with json body to kafka connect
     *
     * @param body is the string that will be send as http-body
     * @return returns a Retrofit Call instance, that when executed sends the http POST message
     * */
    @Headers({
            "Content-Type: application/json",
            "Accept: application/json"
            })
    @POST("connectors")
    Call<String> postJson(@Body String body);

    /**
     * Generate Call instance that allows executing an HTTP DELETE message to kafka connect
     *
     * @param name is the name of the Kafka Connect config that needs to be deleted
     * @return returns a Retrofit Call instance, that when executed sends the http DELETE message
     * */
    @DELETE("connectors/{name}")
    Call<String> delete(@Path("name") String name);
}
