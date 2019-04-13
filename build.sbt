name := "DFH"

version := "0.1"

scalaVersion := "2.12.8"

val sparkVersion = "2.4.1"



dependencyOverrides += "com.fasterxml.jackson.core" % "jackson-core" % "2.6.5"

dependencyOverrides += "com.fasterxml.jackson.core" % "jackson-databind" % "2.6.5"


libraryDependencies ++= Seq(
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.6.5",
  "org.scala-lang" % "scala-library" % scalaVersion.value,
  "org.apache.kafka" % "kafka-streams" % "2.2.0",
  "io.spray" %%  "spray-json" % "1.3.5",
  "org.apache.commons" % "commons-email" % "1.3.1",
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "org.apache.spark" %% "spark-sql-kafka-0-10" % sparkVersion,
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "net.liftweb" %% "lift-json" % "3.3.0",

)



