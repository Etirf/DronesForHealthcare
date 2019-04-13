# DronesForHealthcare
Functional data programming

FONTAINE Louis
UZAN Jérémie

Project

1)Open 2 cmd and type:
zkserver
./bin/windows/kafka-server-start.bat ./config/server.properties

2)Créer les topics
./bin/windows/kafka-topics.bat --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 5 --topic Drones-topic
./bin/windows/kafka-topics.bat --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 5 --topic drones-filtered

3)Start
-producer
-alertStream
-consumer
-sparkAnalysis

----

-Après sparkQueries



##DEBUG
./bin/windows/kafka-topics.bat --delete --bootstrap-server localhost:9092 --topic Drones-topic
./bin/windows/kafka-topics.bat --alter --bootstrap-server localhost:9092 --topic drones-filtered --partitions 5

##INFO
./bin/windows/kafka-topics.bat --describe --bootstrap-server localhost:9092



