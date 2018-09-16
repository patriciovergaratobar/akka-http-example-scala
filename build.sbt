name := "ApiRestAkka"

version := "0.1"
lazy val akkaHttpVersion = "10.1.3"
lazy val jjwtVersion = "0.9.1"
lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization    := "com.pvergara",
      scalaVersion    := "2.12.6"
    )),
    name := "akka-series-http",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-http"   % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-stream" % "2.5.12",
      "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
      "io.jsonwebtoken" % "jjwt-impl" % "0.10.5",
      "io.jsonwebtoken" % "jjwt-api" % "0.10.5",
      "io.jsonwebtoken" % "jjwt-jackson" % "0.10.5",
      "org.apache.logging.log4j" % "log4j-api" % "2.11.1",
      "org.apache.logging.log4j" % "log4j-core" % "2.11.1",
      "org.apache.logging.log4j" %% "log4j-api-scala" % "11.0"

    )
  )