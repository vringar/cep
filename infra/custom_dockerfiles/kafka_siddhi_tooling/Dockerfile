FROM siddhiio/siddhi-tooling AS siddhi

ARG KAFKA_HOME=/usr/share/java/kafka
ARG HOST_BUNDLES_DIR=./files/bundles
ARG HOST_JARS_DIR=./files/jars
ARG JARS=${RUNTIME_SERVER_HOME}/jars
ARG BUNDLES=${RUNTIME_SERVER_HOME}/bundles

COPY --from=confluentinc/cp-enterprise-kafka:5.3.0  --chown=siddhi_user:siddhi_io ${KAFKA_HOME}/scala-library-*.jar ${BUNDLES}/
COPY --from=confluentinc/cp-enterprise-kafka:5.3.0  --chown=siddhi_user:siddhi_io ${KAFKA_HOME}/zkclient-*.jar ${BUNDLES}/
COPY --from=confluentinc/cp-enterprise-kafka:5.3.0 --chown=siddhi_user:siddhi_io ${KAFKA_HOME}/zookeeper-*.jar ${BUNDLES}/

COPY --from=confluentinc/cp-enterprise-kafka:5.3.0 --chown=siddhi_user:siddhi_io ${KAFKA_HOME}/kafka-clients-*.jar ${JARS}/
COPY --from=confluentinc/cp-enterprise-kafka:5.3.0 --chown=siddhi_user:siddhi_io ${KAFKA_HOME}/kafka_2.*.jar ${JARS}/
COPY --from=confluentinc/cp-enterprise-kafka:5.3.0 --chown=siddhi_user:siddhi_io ${KAFKA_HOME}/metrics-core-*.jar ${JARS}/
