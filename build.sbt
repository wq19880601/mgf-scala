name := """mgf-scala"""

version := "1.0"

scalaVersion := "2.11.1"

resolvers ++= Seq(
  "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/",
  "Sonatype Repository" at "http://repository.sonatype.org/content/groups/public"
)

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.1.6" % "test",
  "org.slf4j" % "slf4j-api" % "1.7.5",
  "com.typesafe.play" %% "play-json" % "2.3.0",
  "commons-codec" % "commons-codec" % "1.9",
  "org.apache.httpcomponents" % "httpclient" % "4.3.5",
  "org.springframework" % "spring-core" % "4.0.3.RELEASE",
  "org.springframework" % "spring-beans" % "4.0.3.RELEASE",
  "org.springframework" % "spring-context-support" % "4.0.3.RELEASE",
  "org.springframework" % "spring-test" % "4.0.3.RELEASE",
  "org.springframework" % "spring-context" % "4.0.3.RELEASE",
  "junit" % "junit" % "4.11"
)




