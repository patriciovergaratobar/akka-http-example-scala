package com.pvergara.lab.akkahttp.core.controller

import com.pvergara.lab.akkahttp.core.persistence.Persistence.{AuthBasePersistence, BasePersistence}
import com.pvergara.lab.akkahttp.model.{AuthModel, UserModel}
import org.apache.logging.log4j.scala.Logging

object Controller extends Logging  {

  abstract class AuthBaseController[A](implicit val persistence : AuthBasePersistence[A]) {

    def validate(auth: A): Boolean
    def getUserByAuth(auth: A): UserModel
  }

  abstract class BaseController[A](implicit val persistence: BasePersistence[A]) {

    def getAll(): List[A]
    def get(id: Int): A
    def create(data: A): Boolean
    def update(data: A): Boolean
    def detele(id: Int): Boolean
  }

  implicit object AuthUserController extends AuthBaseController[AuthModel] {

    override def validate(auth: AuthModel): Boolean = persistence.validate(auth)

    override def getUserByAuth(auth: AuthModel): UserModel = {

      logger.info(s"Get User by Auth ${auth.username} ")
      val user = persistence.getUserByAuth(auth)
      if (user.nonEmpty) user.get else UserModel()
    }
  }

  implicit object UserController extends BaseController[UserModel] {

    override def getAll(): List[UserModel] = {

      persistence.getAll()
    }

    override def get(id: Int): UserModel = {

      val user = persistence.get(id)
      if (user.nonEmpty) user.get else UserModel()
    }

    override def create(data: UserModel): Boolean = persistence.create(data)

    override def update(data: UserModel): Boolean = persistence.update(data)

    override def detele(id: Int): Boolean = persistence.detele(id)
  }

}
