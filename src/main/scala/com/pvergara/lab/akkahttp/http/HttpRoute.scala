package com.pvergara.lab.akkahttp.http


import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import com.pvergara.lab.akkahttp.http.routes.UserRoute
import com.pvergara.lab.akkahttp.model.UserModel


trait HttpRoute extends JsonSupport {
  private val userRoute = new UserRoute()
  val route =
    pathSingleSlash {
      get {
        complete(StatusCodes.OK -> HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1> :) Chucrut Server to akka-http</h1>"))
      }
    } ~
    path("hellochucrut") {
      get {
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1> :) Say hello chucrut to akka-http</h1>"))
      }
    } ~
      userRoute.route

}
