cd ../../ && mvn clean install
java -jar topchik-fetcher/target/topchik-fetcher-1.0-SNAPSHOT-jar-with-dependencies.jar - u topchik-fetcher/src/main/resources/repo_data.csv
java -jar topchik-aggregator/target/topchik-aggregator-1.0-SNAPSHOT-jar-with-dependencies.jar
cd topchik-service && mvn exec:java
