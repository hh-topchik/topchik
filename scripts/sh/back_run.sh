cd ../../ && mvn clean install
cd ./topchik-fetcher/target/ && java -jar topchik-fetcher-1.0-SNAPSHOT-jar-with-dependencies.jar
cd ../../topchik-aggregator/target/ && java -jar topchik-aggregator-1.0-SNAPSHOT-jar-with-dependencies.jar
cd ../../topchik-service && mvn exec:java
