logLevel := Level.Warn

resolvers += "Typesafe repository" at "https://repo.typesafe.com/typesafe/releases/"

addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.8.2")

libraryDependencies += "org.postgresql" % "postgresql" % "42.2.14"

addSbtPlugin("org.scalikejdbc" %% "scalikejdbc-mapper-generator" % "3.2.4")