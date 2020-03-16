package berlin.hu.cep.siddhi;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SiddhiClientConfig {

    @JsonProperty("hostaddress")
    public String hostaddress;
    @JsonProperty("siddhi-files")
    public String[] siddhiFiles;
}
