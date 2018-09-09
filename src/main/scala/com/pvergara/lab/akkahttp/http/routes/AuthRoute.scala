package com.pvergara.lab.akkahttp.http.routes

import akka.http.scaladsl.server.Directives.{put, _}
import com.pvergara.lab.akkahttp.core.controller.Controller.{AuthBaseController, BaseController}
import com.pvergara.lab.akkahttp.http.{JsonSupport, TokenApp}
import com.pvergara.lab.akkahttp.model.{AuthModel}

class AuthRoute(implicit controller: AuthBaseController[AuthModel])  extends JsonSupport with TokenApp {

  val route = pathPrefix("login") {
    post {
      entity(as[AuthModel]) { auth =>
        complete(createToken(controller.validate(auth),auth,controller.getUserByAuth(auth)))
      }
    }
  }

}
