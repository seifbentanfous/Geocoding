name := """PlayWithCassandra"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.8"

crossScalaVersions := Seq("2.12.8", "2.11.12")
libraryDependencies += guice
libraryDependencies ++= Seq(
  guice,
  "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.2" % Test,
  "com.datastax.oss" % "java-driver-core" % "4.0.1",
  "org.apache.spark" %% "spark-core" % "2.4.3",
  "org.postgresql" % "postgresql" % "42.2.5",
  "org.apache.spark" %% "spark-mllib" % "2.4.1" % Provided,
  "mysql" % "mysql-connector-java" % "8.0.16",
  "org.apache.spark" %% "spark-sql" % "2.4.3"
)
excludeDependencies ++= Seq(
  "org.slf4j" % "log4j-over-slf4j",
)
dependencyOverrides ++= Seq(
  //"io.netty" % "netty-all" % "4.1.35.Final",
  "com.google.guava" % "guava" % "16.0.1",
  "com.fasterxml.jackson.core" % "jackson-core" % "2.9.8",
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.9.8",
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.9.8"
)