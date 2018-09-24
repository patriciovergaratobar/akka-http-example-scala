package com.pvergara.lab.akkahttp.core.persistence

import com.pvergara.lab.akkahttp.model.{AuthModel, AuthModelMapper, UserModel, UserModelMapper}
import scalikejdbc._

object Persistence {

  //Este valor implicito con tiene la session y es necesario para que se ejecute una query.
  implicit val session = AutoSession

  trait AuthBasePersistence[A] {

    def validate(auth: A): Boolean
    def getUserByAuth(auth: A): Option[UserModel]
  }

  trait BasePersistence[A] {

    def getAll(): List[A]
    def get(id: Int): A
    def create(data: A): Boolean
    def update(data: A): Boolean
    def detele(id: Int): Boolean
  }

  implicit object AuthUserPersistence extends AuthBasePersistence[AuthModel] {

    override def validate(auth: AuthModel): Boolean = {
      val u = AuthModelMapper.syntax("uu")
      val validate: Option[AuthModel] = withSQL { select.from(AuthModelMapper as u).where.eq(u.username, auth.username).and.eq(u.password, auth.password)}.map(AuthModelMapper(u.resultName)).single.apply()
      validate.nonEmpty
    }

    override def getUserByAuth(auth: AuthModel): Option[UserModel] = {
      val u = UserModelMapper.syntax("uu")
      withSQL { select.from(UserModelMapper as u).where.eq(u.userName, auth.username)}.map(UserModelMapper(u.resultName)).single.apply()
    }
  }




}
