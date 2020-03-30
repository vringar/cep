package berlin.hu.cep.connectclient;

import retrofit2.Call;
import retrofit2.http.*;


public interface RestAPI {

    @Headers({
            "Content-Type: application/json",
            "Accept: application/json"
            })
    @POST("connectors")
    Call<String> postJson(@Body String body);

    @DELETE("connectors/{name}")
    Call<String> delete(@Path("name") String name);
}
