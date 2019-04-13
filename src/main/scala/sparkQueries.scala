import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions.col

object sparkQueries extends App{

  val ss = SparkSession
    .builder()
    .appName("Queries and analysis")
    .master("local[*]")
    .getOrCreate()

  val df = ss.read.json("checkpoints/*.json")
  df.printSchema
  df.createOrReplaceTempView("Drones")

  //df.show()

  //template =   batteryBetween  (y > x) (df, battery%1, battery%2)
  batteryBetween(df, 60, 50)
  batteryBetween(df, 15, 70)

  //template =   versionAndStatus  (y > x) (df, battery%1, battery%2)
  versionUnderAndStatus(df, 1.5.toFloat, "off")
  versionAboveAndStatus(df, 2.0.toFloat, "on")


  //template =   batteryDistance >> (greater than) (df, battery, distance)
  batteryDistanceGt(df, 10, 50)
  batteryDistanceGt(df, 2, 25)

  //template =   batteryDistance <<(less than) (df, battery, distance)
  batteryDistanceLt(df, 10, 50)
  batteryDistanceLt(df, 2, 25)



  def versionUnderAndStatus(df: DataFrame, version: Float, status: String){
    val x = df.filter(col("version") <= version &&
      col("status") === status ).count()
    println("There are " + x + " drones running below the version v"+ version + " and that are " + status)
  }

  def versionAboveAndStatus(df: DataFrame, version: Float, status: String){
    val x = df.filter(col("version") >= version &&
      col("status") === status ).count()
    println("There are " + x + " drones running above the version v"+ version + " and that are " + status)
  }

  def batteryBetween(df: DataFrame, a: Int, b: Int){
    val x = df.filter(col("battery") >= a &&
      col("battery") <= b ).count()

    println("There are " + x + " drones that have their battery between " + a + " and " + b)
  }

  def batteryDistanceGt(df: DataFrame, dist: Int, bat: Int){
    val x = df.filter(col("battery") >= bat &&
      col("distance") >= dist ).count()

    println("There are " + x + " drones that have more than " + bat + "% of their battery and must more " +
      "distance that is more than of " + dist + "km")

  }

  def batteryDistanceLt(df: DataFrame, dist: Int, bat: Int){
    val x = df.filter(col("battery") <= bat &&
      col("distance") <= dist ).count()

    println("There are " + x + " drones that have more than " + bat + "% of their battery and must more " +
      "distance that is more than of " + dist + "km")

  }


}