FROM eclipse-temurin:21-jre

RUN apt-get update \
 && apt-get install -y --no-install-recommends curl \
 && rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY build/libs/api-gateway.jar api-gateway.jar

ENTRYPOINT [ "sh", "-c", "exec java $JAVA_OPTS -Duser.timezone=UTC -jar api-gateway.jar" ]
