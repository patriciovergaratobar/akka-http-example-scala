package com.pvergara.lab.akkahttp.http.routes

import akka.http.scaladsl.server.Directives.{put, _}
import com.pvergara.lab.akkahttp.model.{BasicResponseModel, UserModel, UsersModel}
import com.pvergara.lab.akkahttp.core.controller.Controller.BaseController
import com.pvergara.lab.akkahttp.http.JsonSupport

/**
  * Class Route WS Users Data
  * @param controller type UserModel
  */
class UserRoute(implicit controller: BaseController[UserModel]) extends JsonSupport {

  val route = pathPrefix("user") {
    path("id" / Segment) { pathParam =>
      get {
        complete(controller.get(pathParam.toInt))
      }
    } ~ get {
      complete(UsersModel(controller.getAll()))
    } ~ post {
      entity(as[UserModel]) { user =>
        complete(BasicResponseModel(controller.create(user), ""))
      }
    } ~ put {
      entity(as[UserModel]) { user =>
        complete(BasicResponseModel(controller.update(user), ""))
      }
    } ~ path(Segment) { pathParam =>
      delete {
        complete(BasicResponseModel(controller.detele(pathParam.toInt), s"User ${pathParam.toString} deleted."))
      }
    }
  }
}
