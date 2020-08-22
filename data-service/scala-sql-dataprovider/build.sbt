name := """scala-sql-dataprovider"""
organization := "com.aykhan"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.3"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test

libraryDependencies += "io.getquill" %% "quill-async-postgres" % "3.5.2"

libraryDependencies += "org.postgresql" % "postgresql" % "42.2.15"


// Adds additional packages into Twirl
//TwirlKeys.templateImports += "scala-sql-dataprovider:com.aykhan.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "scala-sql-dataprovider:com.aykhan.binders._"
