package com.pvergara.lab.akkahttp.http.routes

import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpMethod, StatusCodes}
import akka.http.scaladsl.server.Directive1
import akka.http.scaladsl.server.Directives._
import com.pvergara.lab.akkahttp.model.{BasicResponseModel, UserModel, UsersModel}
import com.pvergara.lab.akkahttp.core.controller.Controller.BaseController
import com.pvergara.lab.akkahttp.http.JsonSupport

import scala.concurrent.Future


class UserRoute(implicit controller: BaseController[UserModel]) extends JsonSupport {

  val route =
        path("user") {
          get {
            complete(
              StatusCodes.OK  ->
                HttpEntity(
                  ContentTypes.`application/json`,
                  usersJsonFormat.write(UsersModel(controller.getAll())).toString()
                )
            )
          }
        } ~ path("user" / Segment) {
          pathParam =>
            get {
              complete(
                StatusCodes.OK  ->
                  HttpEntity(
                    ContentTypes.`application/json`,
                    userJsonFormat.write(controller.get(pathParam.toInt)).toString()
                  )
              )
            }
        } ~ path("user") {
          post {
            entity(as[UserModel]) { user => complete(
                StatusCodes.OK  -> HttpEntity(
                  ContentTypes.`application/json`,
                  basicResponseJson.write(BasicResponseModel(controller.create(user), "")).toString()
                )
              )
            }
          }
        } ~ path("user") {
          put {
            entity(as[UserModel]) { user => complete(
              StatusCodes.OK  -> HttpEntity(
                  ContentTypes.`application/json`,
                  basicResponseJson.write(BasicResponseModel(controller.update(user), "")).toString()
                )
            )
          }
        } ~ path("user" / Segment) {
            pathParam =>
              delete {
                complete(
                  StatusCodes.OK  ->
                    HttpEntity(
                      ContentTypes.`application/json`,
                      basicResponseJson.write(BasicResponseModel(controller.detele(pathParam.toInt), "")).toString()
                    )
                )
              }
          }
      }
}
