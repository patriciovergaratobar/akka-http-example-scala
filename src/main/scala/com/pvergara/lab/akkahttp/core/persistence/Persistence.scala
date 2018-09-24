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
    def get(id: Int): Option[A]
    def create(data: A): Boolean
    def update(data: A): Boolean
    def detele(id: Int): Boolean
  }

  implicit object AuthUserPersistence extends AuthBasePersistence[AuthModel] {

    val au = AuthModelMapper.syntax("au")
    val u = UserModelMapper.syntax("uu")

    override def validate(auth: AuthModel): Boolean = withSQL {
      select.from(AuthModelMapper as au)
        .where.eq(au.username, auth.username)
        .and.eq(au.password, auth.password)}
      .map(AuthModelMapper(au.resultName))
      .single.apply()
      .nonEmpty


    override def getUserByAuth(auth: AuthModel): Option[UserModel] = withSQL {
      select.from(UserModelMapper as u)
        .where.eq(u.userName, auth.username)}
      .map(UserModelMapper(u.resultName))
      .single.apply()

  }

  implicit object UserPersistence extends BasePersistence[UserModel] {

    val u = UserModelMapper.syntax("uu")

    override def getAll(): List[UserModel] =  withSQL {
      select.from(UserModelMapper as u)}
      .map(UserModelMapper(u.resultName))
      .list.apply()

    override def get(id: Int): Option[UserModel] = withSQL {
      select.from(UserModelMapper as u)
        .where.eq(u.id, id)}
      .map(UserModelMapper(u.resultName))
      .single.apply()

    override def create(data: UserModel): Boolean = sql"insert into user (username, password, email, name, last_name, age, phono, perfil) values (${data.userName}, ${data.userName},  ${data.mail}, ${data.name}, ${data.lastName}, ${data.age}, ${data.phono}, ${data.perfil})"
        .update.apply().==(1)

    override def update(data: UserModel): Boolean = false

    override def detele(id: Int): Boolean =  sql"delete from user WHERE user_id = ${id}".update.apply().==(1)
  }

}
