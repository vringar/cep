# Systemumfang

Das System beinhaltet eine docker-compose Datei, um die verschiedenen Module hochzufahren und zu verbinden, sowie ein Deployer-Programm, welches die Möglichkeit bereitstellt Siddhi-Files hochzufahren und mit Zeebe-Workflows über [Apache Kafka](https://kafka.apache.org/) zu verbinden.
Zudem stellt der **Deployer** Java-Libraries zu Verfügung, um Siddhi-Files in der Laufzeit dynamisch hochzufahren und herunterzufahren.  
Der **Deployer** enthält außerdem die Möglichkeit die Kommunikation zwischen Zeebe und Siddhi in einer [MongoDB](https://www.mongodb.com/) zu speichern. Damit stehen die Daten für eine Analyse oder Fehlersuche zu einem späteren Zeitpunkt zu Verfügung.  
Des Weiteren enthält das System eine Machbarkeitsstudie in Form einer realisierten Anbindung eines einfachen Workflows mit einem Siddhi-File.
