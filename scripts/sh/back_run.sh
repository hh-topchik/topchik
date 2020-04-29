cd ../../ && mvn clean install
cd ./topchik-fetcher/target/ && java -jar topchik-fetcher/target/topchik-fetcher-1.0-SNAPSHOT-jar-with-dependencies.jar -f topchik-fetcher/src/main/resources/repo_data.csv
cd ../../topchik-aggregator/target/ && java -jar topchik-aggregator-1.0-SNAPSHOT-jar-with-dependencies.jar
cd ../../topchik-service/target/ && java -DsettingsDir=../src/etc/service -jar topchik-service-1.0-SNAPSHOT.jar
