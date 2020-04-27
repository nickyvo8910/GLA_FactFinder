name := "WebsocketsExample"

version := "1.0"

lazy val `WebsocketsExample` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"

resolvers += "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/"

routesGenerator := InjectedRoutesGenerator

scalaVersion := "2.12.2"

libraryDependencies ++= Seq( jdbc , ehcache , ws, specs2 % Test )

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )

resolvers += "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"
libraryDependencies ++= Seq(
    "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.0" % "test"
)

libraryDependencies += ws
libraryDependencies += ehcache
   
libraryDependencies += "com.typesafe.play" %% "play-java" % "2.6.15"
libraryDependencies += "com.typesafe.play" %% "play-java-forms" % "2.6.15"
libraryDependencies += "io.netty" % "netty-all" % "4.1.17.Final"

libraryDependencies ++= Seq(
  ws, // Play's web services module
  guice,
  "com.typesafe.play" % "play-server_2.12" % "2.6.15",
  "org.scala-lang" % "scala-library" % "2.12.2",
  "org.mongodb" % "mongodb-driver-async" % "3.8.2",
  "edu.stanford.nlp" % "stanford-corenlp" % "3.9.2" artifacts (Artifact("stanford-corenlp", "models"), Artifact("stanford-corenlp")),
  "edu.stanford.nlp" % "stanford-parser" % "3.9.2",
  "org.terrier" % "terrier-core" % "5.1",
  "org.terrier" % "terrier-batch-indexers" % "5.1"
)

resolvers += "typesafe2" at "http://repo.typesafe.com/typesafe/maven-releases/"
resolvers += "aspectj" at "http://repo1.maven.org/maven2/"


  
