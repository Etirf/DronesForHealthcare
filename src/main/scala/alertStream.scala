import java.util.Properties

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.{KafkaStreams, StreamsBuilder, StreamsConfig}
import net.liftweb.json._
import DronJson.DroneJson
package DronJson {

  case class DroneJson(
                        streetName: String,
                        streetNumber: Int,
                        distance: Float,
                        battery: Int,
                        version: Float,
                        status: String
                      )

}

object alertStream extends App {

  val props = new Properties()
  props.put(StreamsConfig.APPLICATION_ID_CONFIG, "Drones-Filtered-application")
  props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
  props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass.getName)
  props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass.getName)
  props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")

  val builder = new StreamsBuilder()
  val textLines = builder.stream[String, String]("Drones-topic")

  val droneFiltered = textLines.filter((k: String, v: String) => testProblem(k, v))
    .to("filtered-drones")


  val streams: KafkaStreams = new KafkaStreams(builder.build(), props)
  streams.start()

  def testProblem(key: String, obj: String): Boolean = {
    println(key + " => " + obj)
    implicit val formats = DefaultFormats
    val drone = parse(obj).extract[DroneJson]

    if (drone.battery <= 20) {
      println("battery low : " + drone.battery + "%")
      true
    }
    else if ((drone.status == "off")){
      println("Status " + drone.status)
      true
    }
    else false
  }
}