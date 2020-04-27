package berlin.hu.cep.connector;

import retrofit2.Call;
import retrofit2.http.*;

/**
 * An java interface which represents parts of the Kafka Connect Rest Interface.
 *
 * The Interface is implemented to be used by the Retrofit HTTP-Client.
 * Currently only a small part of the Kafka Connect interface is implemented.
 *
 * @author Lukas Gehring
 * @author Leon Haussknecht
 * @author Maurice Lindner
 * @author Jost Hermann Triller
 * @author Stefan Zabka
 *
 * @see <a href="https://square.github.io/retrofit/">The Retrofit documentaition</a>
 * @see <a href="https://docs.confluent.io/current/connect/references/restapi.html">Kafka Connect Rest Interface</a>
 *
 */
public interface RestAPI {

    /**
     *
     * Generates a new connector in Kafka Connect.
     * Accepts a Json as a String.
     *
     * @param body A Json formated string which is formated like a {@link ConnectorPostObject}. Should be converted via Jacksons {@code writeValueAsString()} method from a {@code ObjectMapper} instance.
     * @return A Retrofit Call instance, that when executed sends the http POST message
     * */
    @Headers({
            "Content-Type: application/json",
            "Accept: application/json"
            })
    @POST("connectors")
    Call<String> postJson(@Body String body);

    /**
     * Deletes a connector from Kafka Connect.
     *
     * @param name The name of the connector to be deleted
     * @return A Retrofit Call instance, that when executed sends the http DELETE message
     * */
    @DELETE("connectors/{name}")
    Call<String> delete(@Path("name") String name);
}
