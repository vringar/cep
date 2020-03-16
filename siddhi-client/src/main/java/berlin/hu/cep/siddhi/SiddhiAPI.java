package berlin.hu.cep.siddhi;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface SiddhiAPI {
    @Headers({
            "accept: application/json",
            "Content-Type: text/plain"
    })
    @PUT("siddhi-apps")
    Call<SiddhiResponse> updateFile(@Body RequestBody file);

    @GET("siddhi-apps")
    Call<Object> getActiveApplications();

    @POST("post")
    Call<Object> testPost(@Body RequestBody content);

    @Headers({
            "accept: application/json",
            "Content-Type: application/json"
    })
    @DELETE("siddhi-apps/{appName}")
    Call<SiddhiResponse> deleteApplication(@Path("appName") String appName);


}
