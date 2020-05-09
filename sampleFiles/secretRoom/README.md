# Sample Room Demo

## door.py(WIP)

door.py erstellt interaktiv Testdaten für das secret Room Beispiel und schickt diese an Kafka-Topics.
door.py kann *Doorevents* und *Readerevents* erstellen. Die Türevents stellen das öffnen oder schließen einer bestimmten Tür da;
die Readerevents das anlegen einer bestimmten Schlüsselkarte an einen Reader.

### Spezifikation Doorevents

*Doorevents* werden an die Kafka-Topic **doorEvents** geschickt und folgen folgendem JSON-Schema:
```
{
    'doorEvent': int,
    'eventType': ('open'|'close')
}
```

### Spezifikation Readerevents

*Readerevents* werden an die Kafka-Topic **readerEvent** geschickt und folgen folgendem JSON-Schema:
```
{
    'readerID': int,
    'cardID': int
}
```
