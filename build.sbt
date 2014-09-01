name := "bbqcontroller"

version := "1.0-SNAPSHOT"

resolvers += "mongodb-repo" at "http://central.maven.org/maven2/"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "org.mongodb" % "mongo-java-driver" % "2.12.0",
  "org.json"%"json"%"20080701",
  "com.googlecode.json-simple" % "json-simple" % "1.1"
)     

play.Project.playJavaSettings
