# Systemgrenzen

## 8.1 Feste Standardkonfiguration und eingeschränkte Testmöglichkeiten

Unser Produkt fügt eine Reihe von komplexen und hochkonfigurierbaren Systemen zusammen. Um die Nutzbarkeit zu zeigen haben wir Einstellungen gewählt, die auf Heimcomputern gut funktionieren und das Konzept gut veranschaulichen. Um die Komplexität der benutzten Systeme für eine Projekt im Bachelorstudiengang Informatik handhabbar zu machen, sind viele Parameter in unserem Produkt nicht direkt veränderbar. (Sie können natürlich weiterhin, an den System direkt eingestellt werden.)

Spezifische Konfigurationen in der Kommunikation zwischen den einzelnen Komponenten teilweise fest im Code verankert und nur schwer einstellbar. Das Produkt liefert damit zwar eine zuverlässige Lösung zur Verbindung von Zeebe und Siddhi, kann aber Probleme verursachen in sehr speziellen Systemumgebungen.

## 8.2 Zeebe Kafka Record Exporter

Mit unserem System müssen alle Zeebe-Events explizit im BPMN-Workflow angegeben werden. Zeebe stellt in Exportern sogenannte Records zur Verfügung, die Informationen über den Zustand von Workflow-Instanzen geben (ob z.B. ein bestimmter Job beendet wurde). Diese Records könnten zusätzlich zu den expliziten Events über Kafka an Siddhi gesendet werden. Zeebe stellt sogar schon [eine einfache Implementation](https://github.com/zeebe-io/zeebe-kafka-exporter) für einen Zeebe-Exporter bereit, der Zeebe-Records in einem Protobuf Schema auf ein Kafka-Topic schreibt. Siddhi, bzw. die Siddhi-Files müssen dann zusätzlich noch konfiguriert werden, um Protobufs erkennen zu können.

## 8.3 Deployer Verbesserungen

Im Deployer und in den Client Bibliotheken wird nur bedingt auf Fehler getestet und nur primitive Fehlernachrichten ausgegeben. So könnten z.B. der Connect-Client und der Siddhi-Client noch bessere Fehleranalysen betreiben bezüglich der Verbindung zu dem Siddhi-Host/Kafka-Connect-Host. Außerdem könnte man in den Deployer einen Sanity-Check einbauen, welcher  offensichtliche Ungereimtheiten findet und meldet in der Konfiguration zwischen BPMN, Siddhi und Kafka-Connect. Zudem könnte man den Deployer noch um die Funktion erweitern auch die BPMN-Workflows zu deployen und zu verwalten.

## 8.4 Ausführliches Testen

Wir haben unser System nur in der bereitgestellten Zusammenstellung getestet. So bleibt zum Beispiel offen, ob es in der Praxis auch mit Kafka funktioniert, wenn Kafka nicht über docker-compose gestartet wurde.
