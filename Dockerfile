FROM maven:3.6.3-openjdk-11 AS build
WORKDIR /usr/src/topchik
COPY pom.xml .

WORKDIR /usr/src/topchik/topchik-model
COPY ./topchik-model/pom.xml .

WORKDIR /usr/src/topchik/topchik-fetcher
COPY ./topchik-fetcher/pom.xml .

WORKDIR /usr/src/topchik/topchik-aggregator
COPY ./topchik-aggregator/pom.xml .

WORKDIR /usr/src/topchik/topchik-service
COPY ./topchik-service/pom.xml .

WORKDIR /usr/src/topchik
RUN mvn clean install

WORKDIR /usr/src/topchik/topchik-model
COPY ./topchik-model/src ./src

WORKDIR /usr/src/topchik/topchik-fetcher
COPY ./topchik-fetcher/src ./src

WORKDIR /usr/src/topchik/topchik-aggregator
COPY ./topchik-aggregator/src ./src

WORKDIR /usr/src/topchik/topchik-service
COPY ./topchik-service/src ./src
COPY ./topchik-service/src/etc ./src/etc

WORKDIR /usr/src/topchik
RUN adduser --disabled-password --gecos '' topchik
RUN chown topchik /usr/src/topchik -R
USER topchik
RUN mvn clean install

FROM openjdk:11-jdk AS run
USER root
COPY ./scripts/sh/back_run_docker.sh /usr/local/bin/
COPY ./scripts/sh/back_run_cron.sh /usr/local/bin/
COPY --from=build /usr/src/topchik/topchik-fetcher/ \
 /usr/local/bin/topchik-fetcher
COPY --from=build /usr/src/topchik/topchik-aggregator/target/topchik-aggregator-1.0-SNAPSHOT-jar-with-dependencies.jar \
 /usr/local/bin/topchik-aggregator-1.0-SNAPSHOT-jar-with-dependencies.jar
COPY --from=build /usr/src/topchik/topchik-service/target/topchik-service-1.0-SNAPSHOT.jar /usr/src/topchik/topchik-service/target/
COPY --from=build /usr/src/topchik/topchik-service/src/etc/service/ /usr/src/topchik/topchik-service/src/etc/service/

RUN chmod 777 /usr/local/bin/ -R

WORKDIR /usr/src/topchik/topchik-service
EXPOSE 8080
CMD ["sh", "/usr/local/bin/back_run_docker.sh"]
