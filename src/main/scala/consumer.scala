import java.time.Duration
import java.util
import java.util.Properties

import DronJson.DroneJson
import Mail._
import net.liftweb.json.{DefaultFormats, parse}
import org.apache.kafka.clients.consumer.{ConsumerConfig, ConsumerRecords, KafkaConsumer}
import org.apache.kafka.common.serialization.StringDeserializer

import scala.collection.JavaConverters._


object consumer extends App {

  val props = new Properties()

  props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
  props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, classOf[StringDeserializer])
  props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, classOf[StringDeserializer])
  props.put(ConsumerConfig.GROUP_ID_CONFIG, "consumer1_problem-drones")
  props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true")
  props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "100")
  props.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest")
  props.put(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG, "org.apache.kafka.clients.consumer.RoundRobinAssignor")

  val consumer: KafkaConsumer[String, String] = new KafkaConsumer[String, String](props)

  consumer.subscribe(util.Collections.singletonList("filtered-drones"))

  while (true) {
    val records: ConsumerRecords[String, String] = consumer.poll(Duration.ofMillis(200))

    for (record <- records.asScala) {
      println("Drone number " + record.key() + " has raised an alert: " + problem(record.value()))
      if ((record.key()) == "100") {
        println("Sending alert for drone " + record.key())

        send a new Mail(
          from = ("an address", "name"),
          to = "an address",
          subject = "ALERT[" + record.key() + "]",
          message = "Drone N°" + record.key() + " raised an alert! The " + problem(record.value()) + "!"
        )
      }
    }
  }

  def problem(obj: String): String = {

    implicit val formats = DefaultFormats
    val drone = parse(obj).extract[DroneJson]

    if (drone.battery <= 20) {
      "battery is low (" + drone.battery + "%)"
    }
    else if (drone.status == "off"){
      "status is OFF"
    }
    else "Alert not found"
  }
}