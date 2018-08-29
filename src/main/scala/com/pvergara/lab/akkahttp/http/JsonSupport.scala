package com.pvergara.lab.akkahttp.http

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.pvergara.lab.akkahttp.model
import com.pvergara.lab.akkahttp.model.{BasicResponseModel, UserModel, UsersModel}
import spray.json.DefaultJsonProtocol

trait JsonSupport extends SprayJsonSupport {
  // import the default encoders for primitive types (Int, String, Lists etc)
  import DefaultJsonProtocol._

  implicit val basicResponseJson = jsonFormat2(BasicResponseModel)

  implicit val userJsonFormat = jsonFormat8(UserModel)

  implicit val usersJsonFormat = jsonFormat1(UsersModel)



}
