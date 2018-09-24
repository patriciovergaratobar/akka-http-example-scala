package com.pvergara.lab.akkahttp.http

import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import com.pvergara.lab.akkahttp.http.routes.{AuthRoute, UserRoute}
import org.apache.logging.log4j.scala.Logging


trait HttpRoute extends JsonSupport with Logging {

  private val userRoute = new UserRoute()
  private val authRoute = new AuthRoute()

  def hasAdminPermissions(token: String): Boolean =  SessionStore.validateToken(token)

  val route =
    pathSingleSlash {
      get {
        complete(StatusCodes.OK -> HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1> :) Chucrut Server to akka-http</h1>"))
      }
    } ~
    pathPrefix("api"){

      authRoute.route ~
        pathPrefix("private") {

          headerValueByName("X-User-Token") { token =>

            authorize(hasAdminPermissions(token)) {

              userRoute.route
            }
          }
        }
    }
}
