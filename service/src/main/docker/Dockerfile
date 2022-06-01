# Java 17
FROM gcr.io/distroless/java17@sha256:e942c49fc2e6f7eacaf4ea6edf12f40f1a2c81454ba18c51df78c90b34df24b1
ARG EMPTYDIR=target/emptydir
ARG DEPLOYMENT_DIR=/deployment
ENV LANG='en_US.UTF-8' LANGUAGE='en_US:en'

COPY --chown=185 ${EMPTYDIR} ${DEPLOYMENT_DIR}
COPY --chown=185 target/quarkus-app/lib/ ${DEPLOYMENT_DIR}/lib/
COPY --chown=185 target/quarkus-app/*.jar ${DEPLOYMENT_DIR}/
COPY --chown=185 target/quarkus-app/app/ ${DEPLOYMENT_DIR}/app/
COPY --chown=185 target/quarkus-app/quarkus/ ${DEPLOYMENT_DIR}/quarkus/

USER 185
WORKDIR ${DEPLOYMENT_DIR}

ENTRYPOINT [ "java", "-jar", "quarkus-run.jar" ]