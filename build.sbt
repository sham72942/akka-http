ThisBuild / version := "1.0"

ThisBuild / scalaVersion := "2.13.12"

lazy val root = (project in file("."))
  .settings(
    name := "scala http poc"
  )

val AkkaVersion = "2.7.0"
val AkkaHttpVersion = "10.5.2"
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % AkkaHttpVersion,

  "ch.qos.logback" % "logback-classic" % "1.2.3"
)
