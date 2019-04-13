package drone
import scala.io.Source

class Drone(val idGiven: Int) {
  val streetName: String = setStreetName()
  val streetNumber: Int = setStreetNumber()
  val distance: Float = setDistance()
  val battery: Int = setBattery()
  val version: Float = setVersion()
  val status: String = setStatus()

  val id: Int = idGiven

  def setStreetName() = {
    val bufferedSource = Source.fromFile("./src/main/resources/Street_Names.csv")
    val nbLines = Source.fromFile("./src/main/resources/Street_Names.csv").getLines.size
    val street = bufferedSource.getLines.drop(scala.util.Random.nextInt(nbLines)).take(1).toList
    bufferedSource.close
    street(0)
  }

  def setStreetNumber() = {
    scala.util.Random.nextInt(300)
  }

  def setDistance() = {
    scala.util.Random.nextFloat() + scala.util.Random.nextInt(15)
  }

  def setBattery() = {
    scala.util.Random.nextInt(100)
  }

  def setVersion() = {
    scala.util.Random.nextFloat() + scala.util.Random.nextInt(2)
  }

  def setStatus() = {
    if (scala.util.Random.nextInt(100) >= 95) "off"
    else "on"
  }

}


