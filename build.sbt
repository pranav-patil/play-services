name := """play-microservices"""
organization := "com.emprovise"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.2"

libraryDependencies ++= Seq(ehcache)
libraryDependencies += filters
libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.3" % Test

libraryDependencies ++= Seq(
  "mysql" % "mysql-connector-java" % "8.0.19",
  "org.json4s" %% "json4s-jackson" % "3.6.7",
  "org.json4s" %% "json4s-ext" % "3.6.7",
  "com.typesafe.play" %% "play-slick" % "5.0.0",
  "com.typesafe.play" %% "play-slick-evolutions" % "5.0.0",
  "com.typesafe.play" %% "play-ws" % "2.8.0",
  "com.softwaremill.sttp" %% "core" % "1.7.2",
  "com.softwaremill.sttp" %% "async-http-client-backend-future" % "1.7.2" exclude("org.slf4j", "slf4j-api"),
  "org.scalatest" %% "scalatest" % "3.1.1" % "test",
  "com.cwbase" % "logback-redis-appender" % "1.1.2"
)

scalacOptions ++= Seq(
  "-feature",
  "-deprecation",
  "-Xfatal-warnings"
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.emprovise.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.emprovise.binders._"
