package com.pvergara.lab.akkahttp.model

import scalikejdbc._

//#user-registry-actor
import akka.actor.{ Actor, ActorLogging, Props }


//Clase modelo de la respuesta
case class BasicResponseModel(status: Boolean, description: String)
case class AuthModel(username: String, password: String, appName: String = "")
case class AuthAccessModel(status: String, token: String, userId: Int)
case class UsersModel(users: Seq[UserModel])

// Modelo de datos que representan la tabla user
case class UserModel(id: Int = 0, userName: String = "", name: String = "", lastName: String = "", age: Int = 0, mail: String = "", phono: String = "", perfil: String = "")


/*
MAPPERS OF THE APP
*/

object AuthModelMapper extends SQLSyntaxSupport[AuthModel] {

  override val tableName = "user"

  override val columns = Seq("username", "password")

  def apply(a: SyntaxProvider[AuthModel])(rs: WrappedResultSet): AuthModel = apply(a.resultName)(rs)
  def apply(a: ResultName[AuthModel])(rs: WrappedResultSet) = new AuthModel(
    rs.nString(a.username),
    rs.nString(a.password)
  )

}

//Mapper de UserModel con Tabla user
object UserModelMapper extends SQLSyntaxSupport[UserModel] {

  //Nombre de la tabla
  override val tableName = "user"

  //Columnas de la tabla
  override val columns = Seq("user_id", "username", "name", "last_name", "age", "email", "phono", "perfil")

  //Converters de los nombre de atributos del modelo en scala con los nombres de las columnas
  //Solo se indican los que son distintos entre "^NOMBRE_ATRIBUTO_DE_LA_CLASE$" -> "NOMBRE_COLUMNA"
  override val nameConverters = Map(
    "^id$" -> "user_id",
    "^userName$" -> "username",
    "^lastName$" -> "last_name",
    "^mail$" -> "email"
  )

  def apply(u: SyntaxProvider[UserModel])(rs: WrappedResultSet): UserModel = apply(u.resultName)(rs)

  def apply(u: ResultName[UserModel])(rs: WrappedResultSet) = new UserModel(

    rs.int(u.id),
    rs.nString(u.userName), rs.nString(u.name), rs.nString(u.lastName), rs.int(u.age),
    rs.nString(u.mail), rs.nString(u.phono), rs.nString(u.perfil)
  )

}

