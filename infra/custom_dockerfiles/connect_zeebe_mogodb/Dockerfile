FROM maven as build
# ADD https://api.github.com/repos/github/vringar/compare/master...HEAD /dev/null
RUN git clone --depth 1 https://github.com/vringar/kafka-connect-zeebe.git kafka-connect-zeebe
WORKDIR kafka-connect-zeebe/
RUN ["mvn", "clean", "install"]

FROM confluentinc/cp-kafka-connect:5.3.0 as kafka-connect-zeebe
COPY --from=build kafka-connect-zeebe/target/*uber.jar /etc/kafka-connect/jars/
RUN ["/usr/bin/confluent-hub", "install", "--no-prompt", "mongodb/kafka-connect-mongodb:1.0.1"]
