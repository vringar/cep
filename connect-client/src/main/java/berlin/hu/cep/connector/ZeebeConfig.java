package berlin.hu.cep.connector;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Abstract superclass for <strong>Zeebe</strong> configurations.
 * <p>{@link ZeebeSinkConfig ZeebeSinkConfig} and {@link ZeebeSourceConfig ZeebeSourceConfig} share a lot of common attributes and methods. They can be found in this abstract superclass.</p>
 *
 * @author Lukas Gehring
 * @author Leon Haussknecht
 * @author Maurice Lindner
 * @author Jost Hermann Triller
 * @author Stefan Zabka
 * @see <a href="https://github.com/zeebe-io/kafka-connect-zeebe">Kafka Connect connector for Zeebe</a>
 * */
public abstract class ZeebeConfig extends ConnectorConfig{

    private String name;
    private boolean mongoDB_logging;
    private int zeebe_client_requestTimeout = 10000;
    private boolean zeebe_client_security_plaintext = true; //TODO: Set to false, maybe?
    private String zeebe_client_broker_contactPoint;

    /**
     * Constructor of a ZeebeConfiguration.
     * It sets all attrivutes which needs to be set by the constructors of the child classes.
     * @param connector_class The name of the connectorclass where the <strong>Kafka Connect</strong> connector is implemented.
     * @param name The name of the connector.
     * @param mongoDB_logging Enables logging of all related events into a <strong>MongDB</strong> database.
     */
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
     * Sets the address of the Zeebe broker to which the sink connector connects to.
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
     * It's value is set to true //TODO: Set to false?
     * @return //TODO: connection to gateways are plaintext?
     */
    @JsonGetter("zeebe.client.security.plaintext")
    public boolean isZeebe_client_security_plaintext() {
        return zeebe_client_security_plaintext;
    }

    /**
     * mongoDB_logging tells us if the events for this connector should be logged to <strong>MongoDB</strong>.
     * This property is not included into generated json, because it is not part of the offical <em>ZeebeConnector</em> specification.
     * @return if the events for this connector get logged
     */
    @JsonIgnore
    public boolean isMongoDB_logging() {
        return mongoDB_logging;
    }

    /**
     * Returns the name of the configuration.
     * The property is not included into generated json, because it is not part of the offical <em>ZeebeConnector</em> specification.
     * @return the name of the configuration
     */
    @JsonIgnore
    public String getName() {
        return name;
    }

}
