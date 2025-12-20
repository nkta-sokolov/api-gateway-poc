FROM eclipse-temurin:21-jre

RUN apt-get update \
 && apt-get install -y --no-install-recommends curl \
 && rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY build/libs/hash-unlocker.jar hash-unlocker.jar

ENTRYPOINT [ "sh", "-c", "exec java $JAVA_OPTS -Duser.timezone=UTC -jar hash-unlocker.jar" ]
