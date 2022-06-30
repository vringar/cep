# Verwendete Technologien

## 5.1 Docker

[Docker](https://www.docker.com/) bietet eine einfache Möglichkeit zur Bereitstellung von Anwendungen durch sogenannte Container, die alle nötigen Pakete enthalten und so leicht als Dateien transportiert und installiert werden können. Zusätzlich wird dadurch eine gewisse Trennung der Rechnerverwaltung gewährleistet.
Docker wird von uns benutzt, um Zeebe, Kafka, Kafka-Connect, Siddhi und jeweils deren Abhängigkeiten zu starten.

## 5.2 Java

[Java](https://www.java.com/en/) ist eine objekorientierte Programmiersprache, die von einem Übersetzerprogramm in Bytecode übersetzt, der dann von einer Java-Laufzeitumgebung unabhänig von der ausführenden Hardware ausgeführt werden kann. Unser Deployer, der Siddhi-Client und der Connect-Client sind in Java geschrieben.

## 5.3 Apache Maven

[Maven](https://maven.apache.org/) ist ein Build-Management-Tool. Mit ihm kann man insbesondere Java-Programme standardisiert erstellen und verwalten. Wir benutzen Maven für den Build-Prozess unseres Deployers und den Bibliotheken dazu.

## 5.4 JSON

[JSON](https://www.json.org/json-de.html) (JavaScript Object Notation) ist ein Datenformat, das einfach für Menschen und Maschinen zu lesen und generieren ist. Es basiert auf einer Untermenge der JavaScript Programmiersprache. Die Konfiguration unseres Deployers wird über eine JSON Schnittstelle erledigt.

## 5.5 Retrofit

[Ein typensicherer HTTP-Client für Java.](https://square.github.io/retrofit/), unser Siddhi-Client und unser Connect-Client benutzen Retrofit, um mit Siddhi, bzw. Kafka-Connect zu kommunizieren.

## 5.6 Jackson

[Jackson](https://github.com/FasterXML/jackson) ist eine JSON-Bibliothek für Java. Unser Deployer nutzt sie um Konfigurationsdateien zu lesen und unsere Bibliotheken erstellen damit JSON-Konfigurationsdateien.

## 5.7 Zeebe

[Zeebe](https://zeebe.io/) ist ein in Java geschriebenes freies Workflow-Management-System, mit dem Geschäftsprozesse in BPMN 2.0 definiert und ausgeführt werden können.

## 5.8 Siddhi

[Siddhi](https://siddhi.io/) ist ein Complex-Event-Processing System, das erlaubt mit einer SQL-ähnlichen Scriptsprache komplexe Ereignisse zu definieren und aus Datenströmen diese zu erkennen.

## 5.9 Apache Kafka

[Kafka](https://kafka.apache.org/) ist ein Open-Source-Software-Projekt, das der Verarbeitung von Datenströmen dient. Kafka stellt eine Schnittstelle zum Laden und Exportieren von Datenströmen zu Drittsystemen bereit. Kafka ist in unserer Architektur der zentrale Kommunikation-Kanal zwischen Zeebe und Siddhi.

## 5.10 Kafka Connect

[Kafka Connect](https://docs.confluent.io/3.0.0/connect/) bietet eine Schnittstelle zum Laden/Exportieren von Daten aus/in Drittsysteme. Kafka Connect führt sogenannte Konnektoren aus, welche die eigentliche Kommunikation mit dem Drittsystem übernehmen. Es gibt bereits viele Konnektoren, die genutzt werden können.

## 5.11 MongoDB

[MongoDB](https://www.mongodb.com/) ist ein dokument-orientiertes Datenbankprogramm welches JSON-artige Dokumente speichert. Als **NoSQL** Datenbankprogramm bietet es den Vorteil kein Relationschema zu benötigen. Die Ordnung der Daten ist durch die Daten selbst gegeben, da diese im JSON-Format vorliegen. Aufwändige Designentscheidungen zum Datenbank Aufbau fallen dafür weg. Unser Produkt nutzt MongoDB um die Kommunikation zwischen Zeebe und Siddhi zu protokollieren.