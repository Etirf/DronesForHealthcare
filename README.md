# DronesForHealthcare
Functional data programming

- FONTAINE Louis
- UZAN Jérémie
- LUBRANO DI SBARAGLIONE Etienne

Project

1) Open 2 cmd and type:
- zkserver
- ./bin/windows/kafka-server-start.bat ./config/server.properties

2) Create topics
- ./bin/windows/kafka-topics.bat --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 5 --topic Drones-topic
- ./bin/windows/kafka-topics.bat --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 5 --topic drones-filtered

3) Start
- producer
- alertStream
- consumer
- sparkAnalysis

---- For the analysis

- Après sparkQueries

4) Structure and how the project works

- "producer" produces drones
- They are consumed by "alertStream" (KafkaStream) and they produce drones in the filtered-drones topic (contains drones that are low on battery or that are offline)
- filtered-drones are consumed by "consumer" that sends an email (alert) and prints the drones that need to be alarmed
- sparkAnalysis transform the drones from the kafka stream to json files (continuously)
- sparkQueries does all the queries on the drones and show the statistics


##DEBUG
- ./bin/windows/kafka-topics.bat --delete --bootstrap-server localhost:9092 --topic Drones-topic
- ./bin/windows/kafka-topics.bat --alter --bootstrap-server localhost:9092 --topic drones-filtered --partitions 5

##INFO
- ./bin/windows/kafka-topics.bat --describe --bootstrap-server localhost:9092



