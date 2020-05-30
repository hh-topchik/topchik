#!/bin/bash
cd /usr/local/bin/topchik-fetcher/target && java -jar topchik-fetcher-1.0-SNAPSHOT-jar-with-dependencies.jar
cd /usr/local/bin && java -jar topchik-aggregator-1.0-SNAPSHOT-jar-with-dependencies.jar
cd /usr/src/topchik/topchik-service/target && java -DsettingsDir=../src/etc/service -jar topchik-service-1.0-SNAPSHOT.jar
