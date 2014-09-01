import sbt.Keys._
import sbt._

object MgfServer extends Build {
  val libVersion = "1.0.0"

  val springVersion = "4.0.3.RELEASE"
  val springLibs = Seq(
    "org.springframework" % "spring-core" % springVersion,
    "org.springframework" % "spring-beans" % springVersion,
    "org.springframework" % "spring-context-support" % springVersion,
    "org.springframework" % "spring-test" % springVersion,
    "org.springframework" % "spring-context" % springVersion
  )

  val sharedSettings = Seq(
    version := libVersion,
    organization := "com.weihui",
    crossScalaVersions := Seq("2.10.0", "2.11.1"),
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "2.1.6" % "test",
      "junit" % "junit" % "4.11",
      "org.slf4j" % "slf4j-api" % "1.7.5",
      "com.typesafe.play" %% "play-json" % "2.3.0",
      "commons-codec" % "commons-codec" % "1.9",
      "org.apache.httpcomponents" % "httpclient" % "4.3.5"
    ),
    resolvers ++= Seq(
      "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/",
      "Sonatype Repository" at "http://repository.sonatype.org/content/groups/public"
    ),
    scalacOptions ++= Seq("-encoding", "utf8"),
    scalacOptions += "-deprecation",
    javacOptions ++= Seq("-source", "1.6", "-target", "1.6"),
    javacOptions in doc := Seq("-source", "1.6")
  )

  lazy val mgfServer = Project(
    id = "mgf-scala",
    base = file("."),
    settings = Project.defaultSettings ++
      sharedSettings
  ).settings(
      name := "mgf-scala",
      libraryDependencies ++= springLibs
    )

}
