name := "ApiRestAkka"

version := "0.1"
lazy val akkaHttpVersion = "10.1.3"
lazy val jjwtVersion = "0.10.5"
lazy val log4jVersion = "2.11.1"
lazy val scalikejdbcVersion = "3.3.0"
lazy val mySqlDriverVersion = "8.0.12"
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
      "io.jsonwebtoken" % "jjwt-impl" % jjwtVersion,
      "io.jsonwebtoken" % "jjwt-api" % jjwtVersion,
      "io.jsonwebtoken" % "jjwt-jackson" % jjwtVersion,
      "org.scalikejdbc" %% "scalikejdbc"          % scalikejdbcVersion,
      "org.scalikejdbc" %% "scalikejdbc-config"   % scalikejdbcVersion,
      "ch.qos.logback"  %  "logback-classic"      % "1.2.3",
      "mysql" % "mysql-connector-java" % mySqlDriverVersion,
      "org.apache.logging.log4j" % "log4j-api" % log4jVersion,
      "org.apache.logging.log4j" % "log4j-core" % log4jVersion,
      "org.apache.logging.log4j" %% "log4j-api-scala" % "11.0"

    )
  )