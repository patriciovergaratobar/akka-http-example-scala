name := "ApiRestAkka"

version := "0.1"
lazy val akkaHttpVersion = "10.1.3"

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
      "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion
    )
  )