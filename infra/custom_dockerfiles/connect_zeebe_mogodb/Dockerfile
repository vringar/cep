FROM confluentinc/cp-kafka-connect:5.3.0 as kafka-connect-zeebe
RUN ["/usr/bin/confluent-hub", "install", "--no-prompt", "zeebe-io/kafka-connect-zeebe:0.22.0"]
RUN ["/usr/bin/confluent-hub", "install", "--no-prompt", "mongodb/kafka-connect-mongodb:1.0.1"]
