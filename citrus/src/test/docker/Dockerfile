# Java 17
FROM gcr.io/distroless/java17@sha256:e942c49fc2e6f7eacaf4ea6edf12f40f1a2c81454ba18c51df78c90b34df24b1
ARG EMPTYDIR=target/emptydir
ARG JAR=target/*-citrus-tests.jar
ARG DEPLOYMENT_DIR=/deployment
ENV LANG="en_US.UTF-8" LANGUAGE="en_US:en"

COPY --chown=185 ${EMPTYDIR} ${DEPLOYMENT_DIR}
COPY --chown=185 ${JAR} ${DEPLOYMENT_DIR}/app.jar

USER 185
WORKDIR ${DEPLOYMENT_DIR}

ENTRYPOINT [ "java", "-jar", "app.jar" ]

