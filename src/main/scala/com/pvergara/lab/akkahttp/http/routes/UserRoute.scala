package com.pvergara.lab.akkahttp.http.routes

import akka.http.scaladsl.model.{ContentTypes, HttpEntity, StatusCodes}
import akka.http.scaladsl.server.Directives.{put, _}
import com.pvergara.lab.akkahttp.model.{BasicResponseModel, UserModel, UsersModel}
import com.pvergara.lab.akkahttp.core.controller.Controller.BaseController
import com.pvergara.lab.akkahttp.http.JsonSupport

/**
  * Class Route WS Users Data
  * @param controller type UserModel
  */
class UserRoute(implicit controller: BaseController[UserModel])
  extends JsonSupport {

  val route = pathPrefix("user") {
    path("id" / Segment) { pathParam =>
      get {
        complete(
          StatusCodes.OK ->
            HttpEntity(
              ContentTypes.`application/json`,
              userJsonFormat.write(controller.get(pathParam.toInt)).toString()
            )
        )
      }
    } ~ get {
      complete(
        StatusCodes.OK ->
          HttpEntity(
            ContentTypes.`application/json`,
            usersJsonFormat.write(UsersModel(controller.getAll())).toString()
          )
      )
    } ~ post {
      entity(as[UserModel]) { user =>
        complete(
          StatusCodes.OK -> HttpEntity(
            ContentTypes.`application/json`,
            basicResponseJson
              .write(BasicResponseModel(controller.create(user), ""))
              .toString()
          )
        )
      }
    } ~ put {
      entity(as[UserModel]) { user =>
        complete(
          StatusCodes.OK -> HttpEntity(
            ContentTypes.`application/json`,
            basicResponseJson
              .write(BasicResponseModel(controller.update(user), ""))
              .toString()
          )
        )
      }
    } ~ path(Segment) { pathParam =>
      delete {
        complete(
          StatusCodes.OK ->
            HttpEntity(
              ContentTypes.`application/json`,
              basicResponseJson
                .write(
                  BasicResponseModel(controller.detele(pathParam.toInt), ""))
                .toString()
            )
        )
      }
    }
  }
}
