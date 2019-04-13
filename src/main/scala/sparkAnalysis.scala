import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.streaming.Trigger
import org.apache.spark.sql.types.{DataTypes, StructType}


object sparkAnalysis extends App{


  val ss = SparkSession.builder()
    .appName("Kafka-getter")
    .master("local[*]")
    .getOrCreate()

  import ss.implicits._


  // reading coordinate kafka data
  val inputDf = ss.readStream
    .format("kafka")
    .option("kafka.bootstrap.servers", "localhost:9092")
    .option("subscribe", "Drones-topic")
    .load()

  val droneJsonDf = inputDf.selectExpr("CAST(value AS STRING)")

  val droneStruct = new StructType()
    .add("id", DataTypes.IntegerType)
    .add("streetName", DataTypes.StringType)
    .add("streetNumber", DataTypes.IntegerType)
    .add("distance", DataTypes.FloatType)
    .add("battery", DataTypes.IntegerType)
    .add("version", DataTypes.FloatType)
    .add("status", DataTypes.StringType)


  val queries = droneJsonDf.select(from_json($"value", droneStruct).as("drone"))
    .selectExpr("drone.id", "drone.streetName", "drone.streetNumber", "drone.distance", "drone.battery",
      "drone.version", "drone.status")
    .writeStream
    .outputMode("append")
    .format("json")
    .option("path", "./checkpoints")
    .option("checkpointLocation", "./checkpoints")
    .trigger(Trigger.ProcessingTime("10 seconds")) //time to wait before getting the data from the stream
    .start()


  queries.awaitTermination()

}
