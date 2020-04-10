/**
 * Provides the classes necessary to configure
 * a running <strong>Kafka Connect</strong> instance.
 * <p>
 * The package is build to read custom json configuration files and deploy them accordingly.
 * To handle json file the package relies on <a href="https://github.com/FasterXML/jackson">FasterXML's Jackson Project</a>
 * To configure <strong>Kafka Connect</strong> via it's REST-API this package uses <a href="https://square.github.io/retrofit/">Retrofit</a>.
 * </p><p>
 * The package should configure <em>Zeebe Source Connectors</em> and <em>Zeebe Sink Connectors</em> so
 * that <strong>Kafka</strong> can transport events between a <strong>zeebe</strong>
 * and a <strong>siddhi</strong> instance.
 * </p><p>
 * Optionally the package can configure <em>MongoDB Connectors</em> so that events get logged in a MongoDB instance.</p>
 *
 * @author Lukas Gehring
 * @author Jost Hermann Triller
 * @since 0.0
 * @see <a href="kafka.apache.org">Apache Kafka</a>
 * @see <a href="zeebe.io">Zeebe</a>
 * @see <a href="siddhi.io">Siddhi</a>
 * */
package berlin.hu.cep.connector;
