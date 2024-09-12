ARG TEMURIN_IMAGE="docker.io/eclipse-temurin:21.0.4_7-jre-alpine@sha256:3f716d52e4045433e94a28d029c93d3c23179822a5d40b1c82b63aedd67c5081"

FROM ${TEMURIN_IMAGE} AS runner
ARG APP_DIR=/deployment
ARG UID=1001

USER root

WORKDIR ${APP_DIR}
RUN chmod -R 777 ${APP_DIR}
COPY \
  --chmod=555 \
  target/quarkus-app/lib/ ${APP_DIR}/lib/
COPY \
  --chmod=444 \
  target/quarkus-app/*.jar ${APP_DIR}/
COPY \
  --chmod=555 \
  target/quarkus-app/app/ ${APP_DIR}/app/
COPY \
  --chmod=555 \
  target/quarkus-app/quarkus/ ${APP_DIR}/quarkus/

ENV LANGUAGE='en_US:en'
USER ${UID}:${UID}
ENTRYPOINT [ \
  "java", \
  "-jar", \
  "quarkus-run.jar" \
]

HEALTHCHECK \
  --interval=30s \
  --retries=3 \
  --timeout=5s \
  CMD [ \
    "/bin/sh", \
    "-c", \
    "wget \
      --quiet \
      --spider \
      --tries=1 \
      localhost:8080/q/health \
      || exit 1"\
  ]

