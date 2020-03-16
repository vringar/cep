package berlin.hu.cep.siddhi;

public class SiddhiResponse {
    public String type, message;
    public SiddhiResponse(String type, String message) {
        this.type = type;
        this.message = message;
    }
}
