# Schnittstellen

## 6.1 Deployer

Der Deployer ist eine Java Anwendung, an die eine JSON Konfigurationsdatei übergeben wird, die Siddhi und die Verbindung zwischen Zeebe und Kafka beschreibt.  
 Zum Erstellen einer Konfiguration:

```bash
java -jar ./deployer/target/deployer-0.0-SNAPSHOT.jar -deploy deployer_config.json
```

Zum Entfernen einer Konfiguration:

```bash
java -jar ./deployer/target/deployer-0.0-SNAPSHOT.jar -remove deployer_config.json
```

Eine Config-Datei muss folgendermaßen aussehen (C-Style Kommentare beschreiben die einzelnen Felder, müssen jedoch vor Benutzung entfernt werden): (**TODO**: Feldernamen anpassen)

```json
{
  "connector_config":
  {
    "connector_host": "localhost", //kafka-connect hostname
    "connector_port": "8083", //kafka-connect port
    "zeebe_client_broker_contactPoint": "zeebe:26500", // selbsterklärend, hostname:port
    "mongoDB_url": "mongodb://mongo:27017", //URL der MongoDB-instanz
    "mongoDB_database": "Ping_Pong", //Name der Datenbank in die protokolliert werden soll.
    "source_configs": // Array, das alle Kafka-Connect Zeebesource Konfigurationen enthält
    [
      { // Kafka-Connect Source Config
        "name": "ping", // eindeutiger Name dieser Konfiguration
        "job_types": "ping", // in einer bpmn-Datei muss dieses Feld mit dem type der "zeebe:taskDefinition" übereinstimmen
        "job_header_topics": "topic", // in einer bpmn-Datei unter einer taskDefinition mit dem type job_type, muss unter zeebe:taskHeaders bei einem zeebe:header mit key="kafka-topic" das value mit diesem Feld übereinstimmen
        "mongoDB_logging": true // Gibt an ob alle Daten die über diesen Connector gehgen protokolliert werden sollen.
      }
    ],
    "sink_configs":
    [
      {
        "name": "pong", // eindeutiger Name dieser Konfiguration
        "topics": "toZeebe", // Kafka-Topic, von dem gelesen wird
        "message_path_messageName": "$.name", // Die folgenden Felder beschreiben, wie eine Nachricht, die von dem oben benannten Topic gelesen wird, in bestimmte Objekte im Zeebe-Workflow gemappt werden
        "message_path_correlationKey": "$.key",
        "message_path_variables": "$.payload",
        "message_path_timeToLive": "$.ttl",
        "mongoDB_logging": true // Gibt an ob alle Daten die über diesen Connector gehgen protokolliert werden sollen.
      }
    ]
  },
  "siddhi_config":
  {
    "hostaddress": "https://localhost:9443/", // hostname:port von dem Siddhi-System
    "siddhi-files": ["sampleFiles/pingpong/KafkaTest.siddhi"] // Array aus Siddhi-Files, die geladen werden sollen
  }
}
```

Weitere speziellere Eigenschaften zu dem Kafka-Connect-Zeebe kann man hier finden: https://github.com/zeebe-io/kafka-connect-zeebe

## 6.2 Connect Client

Der Connect-Client kann auch über ein Java-Programm als Bibliothek benutzt werden. Es wird eine Klasse `ConnectClient` bereitgestellt. Der Konstruktor nimmt eine Konfigurationsklasse `ConnectConfig`, die z.B. mithilfe von [Jackson](https://github.com/FasterXML/jackson) aus einer json-Textdatei befüllt werden kann, als Parameter. Die beiden Memberfunktionen `deploy()` und `delete()` setzen diese Konfiguration ein, bzw. entfernen sie wieder aus dem laufenden Kafka-Connect System. Die Bedeutungen der verschiedenen Felder in der `ConnectConfig` stimmen mit den Bedeutungen überein, die [hier](/06.-Schnittstellen#61-deployer) bezüglich des `"connector_config"` JSON-Felds genannt wurden. Die Funktionsweise und API des Connect-Clients sind mit Javadoc detailliert dokumentiert. Siehe dazu [Javadoc](07.-Javadoc)

## 6.3 Siddhi Client

Der Siddhi-Client kann analog zum Connect Client über ein Java-Programm als Bibliothek benutzt werden. Es wird eine Klasse `SiddhiRestClient` bereitgestellt, der Konstruktor nimmt ein Konfigurationsklasse `SiddhiClientConfig`, die z.B. mithilfe von Jackson aus einer json-Textdatei befüllt werden kann, als Parameter.
Die Methode `deployFiles()` updatet, bzw. falls nicht vorhanden erstellt, die in der JSON Datei referenzierten SiddhiFiles. Entfernt werden kann ein spezielles Siddhi File mit der Methode `deleteApplication(String appname)`.

Der Sourcecode des [Deployers](../deployer/src/main/java/berlin/hu/cep/deployer/Deployer.java) soll hier als weitere Dokumentation dienen.
