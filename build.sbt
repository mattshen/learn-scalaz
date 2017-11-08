import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.example",
      scalaVersion := "2.12.3",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "Hello",
    libraryDependencies ++= Seq(
      "org.scalaz" %% "scalaz-core" % "7.2.16",
      "org.scalaz" %% "scalaz-concurrent"   % "7.2.16",
      "org.asynchttpclient" % "async-http-client" % "2.0.37",
      scalaTest % Test
    )
  )
