package berlin.hu.cep.siddhi;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

public class SiddhiClientMain {

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        SiddhiClientConfig clientconfig = mapper.readValue(new File("/home/lindnerm/IdeaProjects/SiddhiRestClient/src/main/resources/examplejson.json"),SiddhiClientConfig.class);
        SiddhiRestClient restclient = new SiddhiRestClient(clientconfig);
        restclient.deployFiles();
        Thread.sleep(4000);
        System.out.println("Delete Tests");
        System.out.println(restclient.deleteApplication("Test1"));
        System.out.println(restclient.deleteApplication("Test2"));
        Thread.sleep(4000);
        System.out.println(restclient.deleteApplication("Test2"));
    }

}
