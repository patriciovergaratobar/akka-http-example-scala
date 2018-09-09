package com.pvergara.lab.akkahttp.http

import com.pvergara.lab.akkahttp.model.AuthAccessModel

import scala.collection.mutable.HashMap

object SessionStore {

  def defined[T,U](hash: HashMap[T, U], key: T) = hash.contains(key)
  val storeSession = new HashMap[String, AuthAccessModel]

  def validateToken(token: String): Boolean = storeSession contains token
  def saveToken(auth: AuthAccessModel): Unit = storeSession put(auth.token, auth)
  def removeToken(token: String): Unit = storeSession remove(token)
}
