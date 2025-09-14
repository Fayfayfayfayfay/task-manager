FROM ubuntu:latest
LABEL authors="Ariane"

RUN apt-get update &&  \
    apt-get install -q -y --no-install-recommends openjdk-17-jre &&  \
    apt-get clean &&  \
    rm -rf /var/lib/apt/lists/*

RUN mkdir /app

WORKDIR /app

COPY target/task-manager-*.jar task-manager.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/task-manager.jar"]

