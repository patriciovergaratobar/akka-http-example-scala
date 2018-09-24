package com.pvergara.lab.akkahttp.core.controller

import com.pvergara.lab.akkahttp.core.persistence.Persistence.{AuthBasePersistence, BasePersistence}
import com.pvergara.lab.akkahttp.model.{AuthModel, UserModel}

object Controller {

  abstract class AuthBaseController[A](implicit val persistence : AuthBasePersistence[A]) {

    def validate(auth: A): Boolean
    def getUserByAuth(auth: A): UserModel
  }

  abstract class BaseController[A] {

    def getAll(): List[A]
    def get(id: Int): A
    def create(data: A): Boolean
    def update(data: A): Boolean
    def detele(id: Int): Boolean
  }

  implicit object AuthUserController extends AuthBaseController[AuthModel] {

    override def validate(auth: AuthModel): Boolean = persistence.validate(auth)

    override def getUserByAuth(auth: AuthModel): UserModel = {

      val user = persistence.getUserByAuth(auth)
      if (user.nonEmpty) user.get else new UserModel()
    }
  }

  implicit object UserController extends BaseController[UserModel] {

    override def getAll(): List[UserModel] = {

      val dato1 = UserModel(1, "pvergara", "Patricio",  "Vergara", 16, "pvergara@test.cl", "8988827", "ADMIN")
      val dato2 = UserModel(2, "pvergara", "Patricio",  "Vergara", 16, "pvergara@test.cl", "8988827", "ADMIN")

      List(dato1, dato2)
    }

    override def get(id: Int): UserModel = {
      //Model(nameUser: String, name: String, lastName: String, age: Int, mail: String, phono: Int, perfil: String)

      val response = UserModel(id, "pvergara", "Patricio",  "Vergara", 16, "pvergara@test.cl", "8988827", "ADMIN")
      response
    }

    override def create(data: UserModel): Boolean = true

    override def update(data: UserModel): Boolean = true

    override def detele(id: Int): Boolean = true
  }

}
