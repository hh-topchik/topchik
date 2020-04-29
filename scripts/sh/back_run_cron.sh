#!/bin/bash
cd /usr/local/bin/topchik-fetcher/target && java -jar topchik-fetcher-1.0-SNAPSHOT-jar-with-dependencies.jar
cd /usr/local/bin && java -jar topchik-aggregator-1.0-SNAPSHOT-jar-with-dependencies.jar
