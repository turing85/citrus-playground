# Java 17
FROM docker.io/library/eclipse-temurin:17.0.6_10-jre-alpine
ARG JAR=target/*-citrus-tests.jar
ARG DEPLOYMENT_DIR=/deployment
ENV LANG="en_US.UTF-8" LANGUAGE="en_US:en"

USER root
RUN adduser \
  -u 185 \
  -h ${DEPLOYMENT_DIR} \
  -H \
  -s /bin/sh \
  -D \
  runner

USER 185:185
WORKDIR ${DEPLOYMENT_DIR}
COPY --chmod=775 target/quarkus-app/lib/ ${DEPLOYMENT_DIR}/lib/
COPY --chmod=775 target/quarkus-app/*.jar ${DEPLOYMENT_DIR}/
COPY --chmod=775 target/quarkus-app/app/ ${DEPLOYMENT_DIR}/app/
COPY --chmod=775 target/quarkus-app/quarkus/ ${DEPLOYMENT_DIR}/quarkus/

ENTRYPOINT [ \
  "java", \
  "-jar", \
  "quarkus-run.jar" \
]