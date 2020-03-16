package berlin.hu.cep.siddhi;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;

import javax.net.ssl.*;
import java.io.File;
import java.security.cert.CertificateException;

public class SiddhiRestClient {

    private String hostaddress = "https://127.0.0.1:9443/"; //Default
    private String[] files;

    public SiddhiRestClient(SiddhiClientConfig client){
        this.hostaddress = client.hostaddress;
        this.files = client.siddhiFiles;
    }

    public void deployFiles(){
        for(String filename:this.files) {
            try {
                System.out.println(this.update(filename));
            }catch(Exception e){
                System.out.println("Error while deploying File: " + filename);
                System.out.println(e.getMessage());
            }
        }
    }


    public String update(String filename) throws Exception {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(hostaddress)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getUnsafeOkHttpClient()).build();
        SiddhiAPI api = retrofit.create(SiddhiAPI.class);
        File f = new File(filename);
        RequestBody body = RequestBody.create(MediaType.parse("text"), f);
        System.out.println(f.exists());

        Call<SiddhiResponse> call = api.updateFile(body);
        try {
            retrofit2.Response<SiddhiResponse> response = call.execute();

            return ("Type: "+response.body().type + " // Message: " + response.body().message);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    public String deleteApplication(String appName) throws Exception {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(hostaddress)
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .client(getUnsafeOkHttpClient()).build();
        SiddhiAPI api = retrofit.create(SiddhiAPI.class);

        Call<SiddhiResponse> call = api.deleteApplication(appName);
        try {
            retrofit2.Response<SiddhiResponse> response = call.execute();
            System.out.println(response.code());
            if(response.body() != null) {
                System.out.println("not null -> file doesnt exist");
                return ("Type: "+response.body().type + " // Message: " + response.body().message);
            }else{
                return "Successfully undeployed";
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }





    private static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.addInterceptor(new BasicAuthInterceptor("admin","admin"));
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            return builder.build();
            } catch (Exception e) {
                throw new RuntimeException(e);
        }
    }

}