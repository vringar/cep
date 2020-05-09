#! /usr/bin/env python3
from kafka import KafkaProducer
import json


def main():
    SERVER = input_server()
    PORT = 9092
    DOOR_EVENTS_TOPIC = 'doorEvents'
    READER_EVENTS_TOPIC = 'readerEvents'

    producer = KafkaProducer(
        bootstrap_servers=SERVER+':'+str(PORT),
        value_serializer=lambda v: json.dumps(v).encode('utf-8'))
    if(not producer.bootstrap_connected):
        print("Something went wrong")
    while(True):
        menu(producer, DOOR_EVENTS_TOPIC, READER_EVENTS_TOPIC)


def input_server():
    try:
        server = input('Kafka Bootstrap Server (127.0.0.1): ')
        if server == '':
            return '127.0.0.1'
        else:
            return server
    except EOFError:
        exit()
    except KeyboardInterrupt:
        exit()


def menu(producer, DOOR_EVENTS_TOPIC, READER_EVENTS_TOPIC):
    try:
        s = input('(d)oorEvent, (r)eaderEvent, (q)uit: ')
        if(s == 'q'):
            exit()
        elif s == 'd' or s == 'doorEvent':
            get_and_send_doorEvent(producer, DOOR_EVENTS_TOPIC)
        elif s == 'r' or s == 'readerEvent':
            get_and_send_readerEvent(producer, READER_EVENTS_TOPIC)
        else:
            print('Did not understand')
    except EOFError:
        exit()
    except KeyboardInterrupt:
        exit()


def read_doorEvent():
    correctInput = False
    while(not correctInput):
        doorID = input("Please insert the Door ID: ")
        try:
            doorID = int(doorID)
            correctInput = True
        except ValueError:
            print('Please insert a number')
    correctInput = False
    while(not correctInput):
        eventType = input('(o)pen or (c)lose: ')
        if(eventType != 'o' and eventType != 'open'
           and eventType != 'c' and eventType != 'close'):
            print('SyntaxError')
        else:
            correctInput = True

    return make_doorEvent(doorID, eventType)


def make_doorEvent(doorID, eventType):
    assert type(doorID) == int
    assert (eventType == 'o' or eventType == 'open' or
            eventType == 'c' or eventType == 'close')
    if(eventType == 'o' or eventType == 'open'):
        event = 'open'
    else:
        event = 'close'
    return {'doorID': doorID,
            'eventType': event}


def process_doorEvent(producer, TOPIC, doorEvent):
    print('Got Event: ' + json.dumps(doorEvent))
    assert isinstance(producer, KafkaProducer)
    assert (type(doorEvent['doorID']) == int and
            type(doorEvent['eventType']) == str)

    producer.send(TOPIC, doorEvent)
    producer.flush()
    print('Sended ' + json.dumps(doorEvent) + ' to Kafka Topic ' + TOPIC)


def get_and_send_doorEvent(producer, TOPIC):
    doorEvent = read_doorEvent()
    process_doorEvent(producer, TOPIC, doorEvent)


def read_readerEvent():
    correctInput = False
    while(not correctInput):
        readerID = input("Please insert the Reader ID: ")
        try:
            readerID = int(readerID)
            correctInput = True
        except ValueError:
            print('Please insert a number')
    correctInput = False
    while(not correctInput):
        cardID = input('Please insert Card ID: ')
        try:
            cardID = int(cardID)
            correctInput = True
        except ValueError:
            print('Please insert a number')

    return make_readerEvent(readerID, cardID)


def make_readerEvent(readerID, cardID):
    assert type(readerID) == int
    assert type(cardID) == int
    return {'readerID': readerID,
            'cardID': cardID}


def process_readerEvent(producer, TOPIC, readerEvent):
    print('Got Event: ' + json.dumps(readerEvent))
    assert isinstance(producer, KafkaProducer)
    assert (type(readerEvent['readerID']) == int and
            type(readerEvent['cardID']) == int)

    producer.send(TOPIC, readerEvent)
    producer.flush()
    print('Sended ' + json.dumps(readerEvent) + ' to Kafka Topic ' + TOPIC)


def get_and_send_readerEvent(producer, TOPIC):
    readerEvent = read_readerEvent()
    process_readerEvent(producer, TOPIC, readerEvent)


if __name__ == "__main__":
    main()
