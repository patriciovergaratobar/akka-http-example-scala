package com.pvergara.lab.akkahttp

import java.io.File
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.pvergara.lab.akkahttp.http.HttpRoute
import com.typesafe.config.ConfigFactory

import scala.io.StdIn

import scalikejdbc.config._

object WebServer extends App with HttpRoute  {

  val DEFAULT_CONF = "src/main/resources/application.conf"
  val ENV_FILE_CONF = "AKKA_SERVER_CONF"

  val env = if (System.getenv(ENV_FILE_CONF) == null) DEFAULT_CONF else System.getenv(ENV_FILE_CONF)

  val conf = ConfigFactory.parseFile(new File(env))

  //Clase modelo de la respuesta
  final case class DatoModel(name: String, id: Long)
  //Se define implicitamente el formato el json.
  implicit val datoFormat = jsonFormat2(DatoModel);
  implicit val system = ActorSystem("my-system")
  implicit val materializer = ActorMaterializer()
  // necesario para el futuro flatMap / onComplete al final
  implicit val executionContext = system.dispatcher

  val host = conf.getString("http.host")
  val port = conf.getInt("http.port")

  val bindingFuture = Http().bindAndHandle(route, host, port)

  //Se abre la conexion tomando los parametros  que se encuentran en application.conf
  logger.info("Start connection DB")
  DBs.setupAll()

  logger.info("Start Server")
  logger.info(s"Server online at http://${host}:${port}/ \nPress RETURN to stop...")

  /**
    * Se activa cuando Ctrl+c
    */
  sys.ShutdownHookThread {

    /*Se finaliza el proceso*/
    // desencadenar la desvinculación del puerto
    bindingFuture.flatMap(_.unbind()).onComplete(_ => {
      DBs.closeAll()
      system.terminate()
    }) // y apagado cuando esté hecho
    println("exiting")
  }


}
