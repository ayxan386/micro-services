name := "scalasqldataprovider"

version := "1.0"

lazy val `scalasqldataprovider` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"

resolvers += "Akka Snapshot Repository" at "https://repo.akka.io/snapshots/"

scalaVersion := "2.12.2"

libraryDependencies ++= Seq(jdbc, ehcache, ws, specs2 % Test, guice)

libraryDependencies += "org.postgresql" % "postgresql" % "42.2.14"
libraryDependencies ++= Seq(
  "org.scalikejdbc" %% "scalikejdbc" % "3.4.0",
  "org.scalikejdbc" %% "scalikejdbc-config" % "3.4.0",
  "org.scalikejdbc" %% "scalikejdbc-play-initializer" % "2.8.0-scalikejdbc-3.4"
)

enablePlugins(ScalikejdbcPlugin)


unmanagedResourceDirectories in Test <+= baseDirectory(_ / "target/web/public/test")

      