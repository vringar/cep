package berlin.hu.cep.connector;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class ZeebeConfig extends ConnectorConfig{

    private String name;
    private boolean mongoDB_logging;
    private int zeebe_client_requestTimeout = 10000;
    private boolean zeebe_client_security_plaintext = true; //TODO: Set to false, maybe?
    private String zeebe_client_broker_contactPoint;

    public ZeebeConfig(String connector_class, String name, Boolean mongoDB_logging) {
        super(connector_class);
        this.name = name;
        this.mongoDB_logging = mongoDB_logging==null?false:mongoDB_logging; //If mongoDB_logging is not set in json
    }


    /**
     * The address of the Zeebe broker to connect to.
     * @return The address as host:port
     */
    @JsonGetter("zeebe.client.broker.contactPoint")
    public String getZeebe_client_broker_contactPoint() {
        return zeebe_client_broker_contactPoint;
    }

    /**
     * Sets the address of the Zeebe broker to which the sink connector should connect to.
     * @param zeebe_client_broker_contactPoint The broker address as host:port
     */
    public void setZeebe_client_broker_contactPoint(String zeebe_client_broker_contactPoint) {
        this.zeebe_client_broker_contactPoint = zeebe_client_broker_contactPoint;
    }

    /**
     * The timeout for requests to the Zeebe broker.
     * It's value is set to 10 seconds.
     * @return The timeout in milliseconds
     */
    @JsonGetter("zeebe.client.requestTimeout")
    public int getZeebe_client_requestTimeout() {
        return zeebe_client_requestTimeout;
    }

    /**
     * Disables secure connections to the gateway for local development setups.
     * It's value is set to false
     * @return Do connection to gateways are plaintext?
     */
    @JsonGetter("zeebe.client.security.plaintext")
    public boolean isZeebe_client_security_plaintext() {
        return zeebe_client_security_plaintext;
    }

    /**
     * mongoDB_logging tells us if the events for this sink connector should be loggend to <strong>MongoDB</strong>.
     * This is not a property of the <em>Zeebe Sink Connector</em>.
     * It will not be written to the json config file.
     * @return if the events for this connector get logged
     */
    @JsonIgnore
    public boolean isMongoDB_logging() {
        return mongoDB_logging;
    }

    @JsonIgnore
    public String getName() {
        return name;
    }

}
