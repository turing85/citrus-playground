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
COPY --chmod=755 ${JAR} ${DEPLOYMENT_DIR}/app.jar

ENTRYPOINT [ \
  "java", \
  "-jar", \
  "app.jar" \
]

