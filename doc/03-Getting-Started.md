# Getting Started

## Systemanforderungen

* JavaSDK >11
* Maven >3.61
* docker-compose version >1.21.0
* Docker version >19.03.6

## Setupbeschreibung

* Lade das Git-Repo herunter (zum Beispiel als ZIP-Datei oder mit git)

    ```bash
    git clone https://github.com/vringar/cep.git
    ```

* Installieren der Systemanforderungen, beispielhaft für Ubuntu:

    ```bash
    sudo apt install openjdk-11-jdk docker docker-compose maven
    ```

* `JAVA_HOME` setzen, zum Beispiel

    ```bash
    echo "
    export JAVA_HOME="/usr/lib/jvm/java-11-openjdk-amd64"
    " >> ~/.bashrc
    source ~/.bashrc
    ```

* Starten von Docker (eventuell muss `sudo` benutzt werden):

    ```bash
    systemctl restart docker
    ```

* Komplilieren des Deployers im Wurzelverzeichnis des Repositories:

    ```bash
    mvn clean package
    ```

Bei Erfolg sollte nun `deployer/target/deployer-1.0-jar-with-dependencies.jar` existieren.

#### Ausführung des Beispiels *Pingpong*

* Kompilieren des Clients im Verzeichnis `pingpongExample`:

    ```bash
    mvn clean package
    ```
  
    Bei Erfolg sollte nun `pingpongExample/target/pingpong-client-1.0-SNAPSHOT-jar-with-dependencies.jar` existieren.
* `docker-compose up -d` im Verzeichnis `infra` ausführen (eventuell muss `sudo` benutzt werden)
* Ein bisschen warten bis alle Systeme hochgefahren sind, 5 Minuten sollten ausreichen.
* Den Deployer im Wurzelverzeichnis ausführen, so dass `pingpongExample/deployer_config.json` deployed wird:

    ```bash
    java -jar deployer/target/deployer-1.0-jar-with-dependencies.jar -deploy pingpongExample/deployer_config.json
    ```

* Den Client starten:

    ```bash
    java -jar pingpongExample/target/pingpong-client-1.0-SNAPSHOT-jar-with-dependencies.jar
    ```

Nun ist alles fertig konfiguriert und wenn der Nutzer nun eine Nummer im Client eingibt, so wird diese über Kafka an Siddhi geleitet, wo sie dann gezählt und wieder zurückgeschickt wird. Der Nutzer wird darüber in einer Lognachricht im Client informiert.

Gleichzeitig kann man jeden Schritt auch über das Controll Center für Kafka und in Camunda Operate für den Workflow beobachten.