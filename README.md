# Meta <!-- omit in toc -->

* [Checkliste Dokumentation](#checkliste-dokumentation)
    * [Tutorial](#tutorial)
    * [Architektur](#architektur)
      * [Apache Kafka](#apache-kafka)
      * [Kafka-Siddhi-Verbindung](#kafka-siddhi-verbindung)
      * [Kafka-Zeebe-Verbindung](#kafka-zeebe-verbindung)
      * [Siddhi Konfiguration](#siddhi-konfiguration)
      * [Deployer](#deployer)
    * [Verwendete Technologien/Ansätze](#verwendete-technologienansätze)
      * [Docker](#docker)
      * [Java](#java)
      * [Apache Maven](#apache-maven)
      * [JSON/JavaScript Object Notation](#jsonjavascript-object-notation)
      * [Retrofit](#retrofit)
      * [Jackson](#jackson)
      * [Zeebe](#zeebe)
      * [Siddhi](#siddhi)
      * [Apache Kafka](#apache-kafka-1)
      * [Kafka Connect](#kafka-connect)
      * [MongoDB](#mongodb)
    * [Schnittstellen](#schnittstellen)
      * [Deployer](#deployer-1)
      * [Connect-Client](#connect-client)
      * [Siddhi-Client](#siddhi-client)
    * [Javadoc](#javadoc)
    * [Offene Punkte/Einschränkungen/Systemgrenzen](#offene-punkteeinschränkungensystemgrenzen)
      * [Statische Konfiguration](#statische-konfiguration)
      * [Zeebe Kafka Record Exporter](#zeebe-kafka-record-exporter)
      * [Deployer Verbesserungen (TODO: Zu komplexe Satzstrukturen)](#deployer-verbesserungen-todo-zu-komplexe-satzstrukturen)
      * [Ausführliches Testen](#ausführliches-testen)

Hier können wir alle möglichen MetaKram sammeln bevor wir alles in einzelne Repos aufsplitten.

## Checkliste Dokumentation

für eure Dokumentation würden wir euch bitte zwei potentielle Zielgruppe im Auge zu haben:
1.  Potentielle Endanwender
2. Entwickler die eure Software nutzen wollen.
_________________________________
Für 1.) solltet ihr folgende Dinge in eure Dokumentation integrieren:
_________________________________
- [ ] Produktvision
     - [ ] Funktionalität von Zeebe um CEP erweitern
     - [ ] Diese Funktionalität einfach nutzbar zu machen
     - [ ] Cloud Native
Workflow-Engines sind Softwaresysteme zur Überwachung und Steuerung von Prozessen basierend
auf einem Modell. Während man in der Vergangenheit häufig nur einfache Geschäftsprozesse über
Workflows abgewickelt hat, werden nach und nach die Anforderungen in Punkten wie
beispielsweise Geschwindigkeit und Parallelität immer höher durch neue Anwendungsgebiete.
Aus diesen Anwendungsgebieten gehen momentan leistungsfähigere Workflow-Engines, wie Zeebe,
hervor.
Diese Workflow-Engines sind allerdings auf sehr atomare Formen von Events beschränkt und
erfordern somit teils sehr umständliche Modellierungen, um komplexere Zusammenhänge zwischen
Events darzustellen. Um diese Komplexität besser handhaben zu können, kann man Complex Event
Processing (CEP) einsetzen. Dieses ermöglicht es Daten in sinnvolle Informationen umzuwandeln
und atomare Events in komplexe Events zu fassen.
Dieses Produkt setzt die Zusammenschaltung zwischen der Workflow-Engine Zeebe und der
CEP-Engine Siddhi um. Hierbei ermöglicht es auf eine einfache Weise CEP-Funktionalitäten in
Zeebe-Workflows einzusetzen. (**TODO**: Irgendwas zu Cloud Native, Talken wir auch über
Kafka als Möglichkeit neue Features zu integrieren?)
_________________________________


- [ ] Beschreibung des Systemumfangs
     - [ ] Machbarkeitsstudie
     - [ ] Docker-compose file + deployer als Ready-To-Deploy System
     - [ ] Deployer + Deployer Libraries

Das System beinhaltet eine docker-compose Datei, um die verschiedenen Module hochzufahren
und zu verbinden, sowie ein Deployer-Programm, welches die Möglichkeit bereitstellt
Siddhi-Files hochzufahren und mit Zeebe-Workflows über Kafka zu verbinden.
Zudem stellt der Deployer Java-Libraries zu Verfügung, um Siddhi-Files in der Laufzeit
dynamisch hochzufahren und herunterzufahren.
Des weiteren enthält das System eine Machbarkeitsstudie in Form einer realisierten Anbindung
eines einfachen Workflows mit einem Siddhi-File.
_________________________________

 - [ ] Mini-Tutorial -> Beispielhafte Darstellung der Anwendungsfälle mir Screenshots
(**TODO**: Mini-Tutorial eventuell erst nach Architektur-Doku?)
     - [ ] Beispiel zum laufen kriegen
     - [ ] Beispiel dokumentieren
     - [ ] Python (für Beispiel)

#### Tutorial

Um dem Tutorial zu folgen sollten zuerst alle benötigten Technologien installiert sein.
Diese sind:
 - Ein JavaSDK Version >11
 - Docker und Docker-Compose
  
Wenn diese installiert sind, sollte man nun folgendes tun:
1. Komplilieren des Deployers
   1. `mvn package` im Wurzelverzeichnis des Repositories ausführen
   2. Prüfen das `deployer/target/deployer-1.0-jar-with-dependencies.jar` existiert
2. Kompilieren des Clients
   1. `mvn package` in `sampleFiles/pingpong` ausführen
   2. Prüfen das in `sampleFiles/pingpong/target/pingpong-client-1.0-SNAPSHOT-jar-with-dependencies.jar` existiert
3. `docker-compose up -d` im Verzeichnis `infra` ausführen
4. Ein bisschen warten
5. Den Deployer im Wurzelverzeichnis ausführen, so das `sampleFiles/pingpong/deployer_config.json` deployed wird.  
      Dies geschieht mit dem Befehl
      `java -jar deployer/target/deployer-1.0-jar-with-dependencies.jar -deploy sampleFiles/pingpong/deployer_config.json`
6. Den Client starten  
     Dies geschieht mit dem Befehl
     `java -jar sampleFiles/pingpong/target/pingpong-client-1.0-SNAPSHOT-jar-with-dependencies.jar`

Nun ist alles fertig konfiguriert und wenn der Nutzer nun eine Nummer im Client eingibt, so wird
diese über Kafka an Siddhi geleitet, wo sie dann gezählt und wieder zurück geschickt wird.
Der Nutzer wird darüber in einer Lognachricht im Client informiert.

Gleichzeitig kann man jeden Schritt auch über das Controll Center für Kafka und in Camunda Operate für den Workflow beobachten.
   



_________________________________
Für 2.):
_________________________________

- [ ] Systemanforderungen
   - [ ] Docker
   - [ ] Java
   - [ ] welche Versionen
_________________________________
- [ ] Beschreibung des Setups
   - [ ] Startup skript ausbauen und dokumentieren
_________________________________
- [ ] Architekturdokumentation
    - [x] Diagramm
    - [x] Kafka erklären
    - [x] kafka-connect-zeebe
    - [ ] Deployer Struktur
    - [x] Clients als Library einzeln verwendbar

#### Architektur

![alt text](./cep_architecture.png "CEP Architektur")
Die Workflow-Engine Zeebe und die CEP-Engine Siddhi werden über Kafka verbunden.
Die nötigen Konfiguration, die über docker-compose hinausgeht, wird von unserer Anwendung Deployer übernommen.
##### Apache Kafka
Apache Kafka ist ein System, das eine Schnittstelle für Datenströme verschiedener Systeme bereitstellt.
Die Datenströme stellt es in sogenannten **Kafka-Topics** zur Verfügung. **Producer** schreiben Daten in ein oder mehrere **Kafka-Topics**, **Consumer** abonnieren ein oder mehrere **Kafka-Topics** und verarbeiten die darüber gesendeten Daten.
In unserem Fall werden explizite Zeebe Workflow Events und externe Events an Kafka übergeben,
um dann von Siddhi gelesen zu werden, sodass komplexe Events erkannt werden können.
Siddhi übergibt die erkannten komplexen Events an Kafka, von wo sie von Zeebe gelesen werden und
dann entsprechend in Workflowinstanzen behandelt werden.
Der Vorteil Kafka statt einer eigenen Lösung zur Verbindung von Siddhi und Zeebe zu nutzen, ist,
dass erstens für die verschiedenen notwendigen Operationen (Zeebe-zu-Kafka, Kafka-zu-Zeebe,
Siddhi-zu-Kafka, Kafa-zu-Siddhi) schon Lösungen existieren, die genutzt werden können.
Zweitens ist es ohne größeres Refactoring mögliche externe Systeme an die verschiedenen Datenströme
anzuschließen. So können z.B. erkannte komplexe Events in einer Datenbank geloggt werden.
##### Kafka-Siddhi-Verbindung
Siddhi benötigt nur noch einige JARs um die [siddhi-io-kafka](https://siddhi-io.github.io/siddhi-io-kafka/)
Extension benutzen zu können.
Die Verbindung zwischen Kafka und Siddhi wird deshalb über (**TODO**:docker-compose/docker-files?) gelöst.
##### Kafka-Zeebe-Verbindung
Die Verbindung zwischen Kafka und Zeebe wird mithilfe eines Kafka-Connect-Connectors realisiert.
[kafka-connect-zeebe](https://github.com/zeebe-io/kafka-connect-zeebe) erlaubt es Nachrichten von
Zeebe-Workflow-Instanzen auf ein Kafka-Topic zu schreiben.
Außerdem können Nachrichten von einem Kakfa-Topic an ein Workflow übergeben werden.
Dafür müssen von kafka-zeebe-connect benutzte Source- und Sink-Connectors entsprechend des Workflows und
der Siddhi-Files konfiguriert werden. Dafür wird von uns eine Library, den Connect-Client, bereitgestellt,
dessen Schnittstelle ausführlich unter **Schnittstellen, Connect-Client** beschrieben wird.
##### Siddhi Konfiguration
An Siddhi müssen die entsprechenden Siddhi-Files übergeben werden.
Dazu stellen wir eine Bibliothek, den Siddhi-Client zur Verfügung,
die es erlaubt einfach die zu nutzenden Siddhi-Files an Siddhi zu senden.
Die Schnittelle ist genauer unter **Schnittstellen, Siddhi-Client** beschrieben.
##### Deployer
In den meisten Anwendungfällen ist es ausreichend auf die Funktionalität von Connect-Client und Siddhi-Client
über den Deployer zuzugreifen.
Der Deployer ermöglicht es über eine einzelne, einfache JSON Textdatei sowohl Siddhi als auch
kafka-connect-zeebe zu konfigurieren. Die genaue Form der JSON-Datei ist auch
unter **Schnittstellen, Deployer** beschrieben.
_________________________________
- [ ] Verwendete Technologien/Ansätze
   - [ ] Apache Docker
   - [x] Apache Java
   - [x] Apache Maven
   - [x] Apache JSon
   - [x] Apache Retrofit
   - [x] Apache Zeebe
   - [x] Apache Siddhi
   - [x] Apache Kafka
   - [x] Apache Kafka Connect
   - [ ] (Apache Apache)
(**TODO**: alles noch um MongoDB ergänzen, falls wir das am Ende benutzen)

#### Verwendete Technologien/Ansätze
##### Docker
(**TODO**: das hier ist einfach ein wikipedia copy paste ACHTUNG)
[Docker](https://www.docker.com/) vereinfacht die Bereitstellung von Anwendungen, weil sich Container, die alle
nötigen Pakete enthalten, leicht als Dateien transportieren und installieren lassen. Container gewährleisten die
Trennung und Verwaltung der auf einem Rechner genutzten Ressourcen. Das beinhaltet laut Aussage der Entwickler:
Code, Laufzeitmodul, Systemwerkzeuge, Systembibliotheken – alles was auf einem Rechner installiert werden kann.
Docker wird von uns benutzt, um Zeebe, Kafka, Kafka-Connect, Siddhi und jeweils deren
Abhängigkeiten zu starten.
##### Java
Java ist eine objekorientierte Programmiersprache, die von einem Übersetzerprogramm in Bytecode übersetzt, der
dann von einer Java-Laufzeitumgebung unabhänig von der ausführenden Hardware ausgeführt werden kann.
Unser Deployer, der Siddhi-Client und der Connect-Client sind in Java geschrieben.
##### Apache Maven
[Maven](https://www.java.com/en/) ist ein Build-Management-Tool.
Mit ihm kann man insbesondere Java-Programme standardisiert erstellen und verwalten.
Wir benutzen Maven für den Build-Prozess unseres Deployers und den Bibliotheken dazu.
##### JSON/JavaScript Object Notation
[JSON](https://www.json.org/json-de.html) (JavaScript Object Notation) ist ein Datenformat, das einfach für Menschen
und Maschinen zu lesen und generieren ist.
Es basierd auf einer Untermenge der JavaScript Programmiersprache.
Die Konfiguration unseres Deployers wird über eine JSON Schnittstelle erledigt.
##### Retrofit
[Ein typensicherer HTTP-Client für Java.](https://square.github.io/retrofit/), unser
Siddhi-Client und unser Connect-Client benutzen Retrofit, um mit Siddhi, bzw. Kafka-Connect
zu kommunizieren.
##### Jackson
[Jackson](https://github.com/FasterXML/jackson) ist eine JSON-Bibliothek für Java.
Unser Deployer nutzt sie um Konfigurationsdateien zu lesen und unsere Bibliotheken erstellen damit JSON-Konfigurationsdateien.
##### Zeebe
[Zeebe](https://zeebe.io/) ist ein in Java geschriebenes freies Workflow-Management-System, mit dem Geschäftsprozesse
in BPMN 2.0 definiert und ausgeführt werden können.
##### Siddhi
[Siddhi](https://siddhi.io/) ist ein Complex-Event-Processing System, das erlaubt mit einer SQL-ähnlichen Scriptsprache komplexe
Ereignisse zu definieren und aus Datenströmen diese zu erkennen.
##### Apache Kafka
[Kafka](https://kafka.apache.org/) ist ein Open-Source-Software-Projekt, das der Verarbeitung von Datenströmen dient.
Kafka stellt eine Schnittstelle zum Laden und Exportieren von Datenströmen zu Drittsystemen bereit.
Kafka ist in unserer Architektur der zentrale Kommunikation-Kanal zwischen Zeebe und Siddhi.
##### Kafka Connect
[Kafka Connect](https://docs.confluent.io/3.0.0/connect/) bietet eine Schnittstelle zum Laden/Exportieren von Daten aus/in Drittsysteme.
Kafka Connect führt sogenannte Konnektoren aus, welche die eigentliche Kommunikation mit dem Drittsystem übernehmen.
Es gibt bereits viele Konnektoren, die genutzt werden können.
##### MongoDB
[MongoDB](https://www.mongodb.com/) ist ein dokument-orientiertes Datenbankprogramm welches JSON-artige Dokumente speichert.
Als **NoSQL** Datenbankprogramm bietet es den Vorteil kein Relationschema zu benötigen. Die Ordnung der Daten ist durch die Daten selbst gegeben, da diese im JSON-Format vorliegen. Aufwändige Designentscheidungen zum Datenbank Aufbau fallen dafür weg.
Unser Produkt nutzt MongoDB um die Kommunikation zwischen Zeebe und Siddhi zu protokollieren.
_________________________________


- [ ] Schnittstellenbeschreibung
   - [x] JSON-Deployer-Config
   - [x] Connect-Client Schnitstellen
   - [ ] Siddhi-Client Schnitstellen
#### Schnittstellen
##### Deployer
Der Deployer ist eine Java Anwendung, an die eine JSON Konfigurationsdatei übergeben wird, die Siddhi und
die Verbindung zwischen Zeebe und Kafka beschreibt.
Zum Erstellen einer Konfiguration:
```
java -jar ./deployer/target/deployer-0.0-SNAPSHOT.jar -deploy deployer_config.json
```
Zum Entfernen einer Konfiguration:
 ```
 java -jar ./deployer/target/deployer-0.0-SNAPSHOT.jar -remove deployer_config.json
```
Eine Config-Datei muss folgendermaßen aussehen (C-Style Kommentare beschreiben die einzelnen Felder,
müssen jedoch vor Benutzung entfernt werden): (**TODO**: Feldernamen anpassen)
```
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
Weitere speziellere Eigenschaften zu dem Kafka-Connect-Zeebe kann man hier finden:
https://github.com/zeebe-io/kafka-connect-zeebe
##### Connect-Client
Der Connect-Client kann auch über ein Java-Programm als Bibliothek benutzt werden.
Es wird eine Klasse `ConnectClient` bereitgestellt. Der Konstruktor nimmt eine Konfigurationsklasse
`ConnectConfig`, die z.B. mithilfe von [Jackson](https://github.com/FasterXML/jackson) aus einer
json-Textdatei befüllt werden kann, als Parameter.
Die beiden Memberfunktionen `deploy()` und `delete()` setzen diese Konfiguration ein, bzw. entfernen sie wieder
aus dem laufenden Kafka-Connect System.
Die Bedeutungen der verschiedenen Felder in der `ConnectConfig` stimmen mit den Bedeutungen überein,
die unter **Schnittstellen, Deployer** bezüglich des `"connector_config"` JSON-Felds genannt wurden.
Die Funktionsweise und API des Connect-Clients sind mit Javadoc detailiert dokumentiert. Siehe dazu **Javadoc**
##### Siddhi-Client
**TODO**
Der Sourcecode des [Deployers](./deployer/src/main/java/berlin/hu/cep/Deployer.java) soll hier als weitere Dokumentation dienen.
_________________________________
#### Javadoc
Der Connect-Client (**TODO**: vielleicht auch Siddhi-Client?) ist mit Javadoc dokumentiert.
Zur Generierung der Javadoc-Dateien muss im `monorepo`-Directory `mvn clean javadoc:aggregate`
ausgeführt werden. Die html-Dateien können dann unter `monorepo/target/site/apidocs/berlin/...`
gefunden werden.
_________________________________

- [ ] Offene Punkte/Einschränkungen/Systemgrenzen
   - [ ] Config zur Zeit nur statisch und nicht zur Laufzeit anpassbar
   - [x] Zeebe Exporter nach Siddhi schicken
       - [x] Protobuf in Siddhi
   - [ ] Immer nur mit Kafka im Compose File getestet
   - [x] Keine Tests im Deployer
   - [x] Zeebe Integration in den Deployer

#### Offene Punkte/Einschränkungen/Systemgrenzen

##### Statische Konfiguration
(**TODO**: Ergibt das Sinn? Könnte man nicht einfach während der Runtime zumindestens
mit dem Deployer eine neue Konfiguration pushen?/Worauf bezieht sich das "statisch"?)
Unser System baut darauf auf, die Konfiguration aller beteiligten Anwendungen ausschließlich
zu Beginn der Benutztung durchzuführen. Eine neue Konfiguration kann nicht z.B. durch Events
während des Betriebs eingeführt werden.

##### Zeebe Kafka Record Exporter
Mit unserem System müssen alle Zeebe-Events explizit im BPMN-Worklow angegeben werden.
Zeebe stellt in Exportern sogenannte Records zur Verfügung, die Informationen über den
Zustand von Workflow-Instanzen geben, ob z.B. ein bestimmter Job beendet wurde.
Diese Records könnten zusätzlich zu den expliziten Events über Kafka an Siddhi gesendet werden.
Zeebe stellt sogar schon [eine einfache Implementation](https://github.com/zeebe-io/zeebe-kafka-exporter)
für einen Zeebe-Exporter, der Zeebe-Records in einem Protobuf Schema auf ein Kafka-Topic
schreibt.
Siddhi, bzw. die Siddhi-Files müssen dann zusätzlich noch konfiguriert um Protobufs
erkennen zu können.

##### Deployer Verbesserungen (TODO: Zu komplexe Satzstrukturen)
Im Deployer und in den Client Bibliotheken wird nur bedingt auf Fehler getestet und nur
primitive Fehlernachrichten ausgegeben.
So könnten z.B. der Connect-Client und der Siddhi-Client noch bessere Fehleranalysen
betreiben bezüglich der Verbindung zu dem Siddhi-Host/Kafka-Connect-Host.
Eine weitere Verbesserung dieser Clienten wäre, mithilfe der BPMN-Workflow Dateien, die
eingesetzt werden sollen, einen Sanity-Check durchzuführen, um zu überprüfen, ob es
offensichtliche Ungereimtheiten in den Siddhi-Files oder in der Kafka-Connect Konfiguration
gibt. 
Eventuell kann es sinnvoll sein, den Deployer mit einem Zeebe-Clienten zu erweitern, der
sich um die BPMN-Workfows kümmert, was vor allem nützlich sein kann, wenn die oben
angesprochenen Überprüfung der Konfiguration anhand der BPMN-Workflows eingesetzt wird.

##### Ausführliches Testen
Wir haben unser System nur in der bereitgestellten Zusammenstellung getestet.
So bleibt zum Beispiel offen, ob es in der Praxis auch mit Kafka funktioniert, wenn Kafka nicht über
docker-compose gestartet wurde.
_________________________________

- [ ] Link zum Source Code
    - [ ] Umstrukturieren des Deployers zu einem Repo
    - [ ] Link zum diesem neuen Repo in die Doku
_________________________________
- [ ] Lizenzierung
    - [ ] haben wir irgendwas zu beachten mit den Sachen die wir benutzen?
    Eigentlich nicht, oder, ich meine wir haben ja nur config-files für docker und maven
    und halt unseren eigene Javaspaghettisuppe.
    - [ ] MIT?

**Begin license text.**

Copyright 2020 Spiderman(**TODO**)

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

**End license text.**
