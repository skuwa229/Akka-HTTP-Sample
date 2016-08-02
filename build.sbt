name := """akka-http-sample"""

organization := "com.skuwa229"

version := "0.0.1"

scalaVersion := "2.11.8"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

libraryDependencies ++= {
  val akkaVersion = "2.4.7"
  val skinnyVersion = "2.1.2"

  Seq(
    "com.typesafe.akka" %% "akka-http-core" % akkaVersion,
    "com.typesafe.akka" %% "akka-http-experimental" % akkaVersion,
    "com.typesafe.akka" %% "akka-http-spray-json-experimental" % akkaVersion,
    "com.typesafe.akka" %% "akka-http-xml-experimental" % akkaVersion,
    "org.skinny-framework" %% "skinny-orm" % skinnyVersion,
    "org.skinny-framework" %% "skinny-task" % skinnyVersion,
    "postgresql" % "postgresql" % "9.1-901.jdbc4",
    "org.scalatest"     %% "scalatest" % "2.2.5" % "test",
    "com.typesafe.akka" %% "akka-testkit" % akkaVersion % "test",
    "ch.qos.logback"       %  "logback-classic"   % "1.1.+"
  )
}

// these commands will be executed when invoking `sbt console`
initialCommands := """
import scalikejdbc._
import skinny.orm._
skinny.DBSettings.initialize()
implicit val dbSession = AutoSession
"""
