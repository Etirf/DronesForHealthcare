# DronesForHealthcare
Functional data programming

DFH - Drones for Healthcare

This project illustrates an architecture based on Kafka & Spark in Scala to create and deliver health products using drones.
To do so, we had to implement something to check the drones status, battery and to be able to have statistics in order to know what to improve and to be able to reflect on our data.

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

3) Start order
- To produce and consume
  - producer
  - alertStream
  - consumer
  - sparkAnalysis

- For the analysis
  - Après sparkQueries

4) Structure and how the project works

We have Drone class that contains different information about the drone (Drone.scala)

- "producer" produces drones
- They are consumed by "alertStream" (KafkaStream) and they produce drones in the filtered-drones topic (contains drones that are low on battery or that are offline)
- filtered-drones are consumed by "consumer" that sends an email (alert) and prints the drones that need to be alarmed. You have to configure your own email account in order to make it work (using gmail api keys), don't forget to change it in the code!
- sparkAnalysis transform the drones from the kafka stream to json files (continuously)
- sparkQueries does all the queries on the drones and show the statistics


Notes aside -
1) Check the sbt file to check the validity of the dependencies
2) You can change the different paths used if there is an access issue.
3) This project was developped under windows 10 OS




## DEBUG
- ./bin/windows/kafka-topics.bat --delete --bootstrap-server localhost:9092 --topic Drones-topic
- ./bin/windows/kafka-topics.bat --alter --bootstrap-server localhost:9092 --topic drones-filtered --partitions 5

## INFO
- ./bin/windows/kafka-topics.bat --describe --bootstrap-server localhost:9092
