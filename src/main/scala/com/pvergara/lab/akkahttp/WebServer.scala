package com.pvergara.lab.akkahttp

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import com.pvergara.lab.akkahttp.http.HttpRoute
import spray.json.DefaultJsonProtocol._

import scala.io.StdIn

object WebServer extends App with HttpRoute {

  //Clase modelo de la respuesta
  final case class DatoModel(name: String, id: Long)
  //Se define implicitamente el formato el json.
  implicit val datoFormat = jsonFormat2(DatoModel);

  implicit val system = ActorSystem("my-system")
  implicit val materializer = ActorMaterializer()
  // necesario para el futuro flatMap / onComplete al final
  implicit val executionContext = system.dispatcher

  val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)

  println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")

  StdIn.readLine() // déjalo funcionar hasta que el usuario presione regresar
    /*Se finaliza el proceso*/
  bindingFuture
      .flatMap(_.unbind()) // desencadenar la desvinculación del puerto
      .onComplete(_ => system.terminate()) // y apagado cuando esté hecho
}
