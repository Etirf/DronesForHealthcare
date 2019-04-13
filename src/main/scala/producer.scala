import java.util.Properties

import drone.Drone
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, _}
import org.apache.kafka.common.serialization.StringSerializer


object producer extends App {

  val props = new Properties()


  props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
  props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, classOf[StringSerializer])
  props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, classOf[StringSerializer])


  val producer = new KafkaProducer[String, String](props)
  val IDs = 100 to 1000
  val topicToSend = "Drones-topic"

  for (id <- IDs) {
    val d = new Drone(id)

    val value =
      "{\"id\": " + d.id.toString + "," +
        "\"streetName\":" + "\"" + d.streetName.toString + "\"" + "," +
        "\"streetNumber\":" + d.streetNumber.toString + "," +
        "\"distance\":" + d.distance.toString + "," +
        "\"battery\":" + d.battery.toString + "," +
        "\"version\":" + d.version.toString +
        ",\"status\":" + "\"" + d.status.toString +"\"" +
        "}"


    producer.send(new ProducerRecord[String, String](topicToSend, id.toString, value))
    println(value)
    Thread.sleep(200)
  }
  producer.close()


}