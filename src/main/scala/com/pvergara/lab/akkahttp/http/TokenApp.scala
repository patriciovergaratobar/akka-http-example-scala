package com.pvergara.lab.akkahttp.http

import java.util.Date

import com.pvergara.lab.akkahttp.model.{AuthAccessModel, AuthModel, UserModel}
import io.jsonwebtoken.{JwtException, Jwts, SignatureAlgorithm}
import io.jsonwebtoken.security.Keys


trait TokenApp {

  val key = Keys.secretKeyFor(SignatureAlgorithm.HS256)

  def createToken(statusSession: Boolean, auth: AuthModel, user: UserModel): AuthAccessModel = {

    try{

      if (statusSession) {

        val time = System.currentTimeMillis()
        val jwt = Jwts.builder()
                    .setId(user.id.toString)
                    .setSubject(auth.username)
                    .setSubject(auth.appName)
                    .setIssuedAt(new Date(time))
                    .signWith(key)
                    .compact

        val session = AuthAccessModel("SUCCESS", jwt, user.id)
        SessionStore.saveToken(session)
        session
    } else {

        AuthAccessModel("FAILURE", "NO_LOGIN", -1)
    }
    }catch {
      case jwt: JwtException => AuthAccessModel("ERROR", jwt.toString, -1)
    }
  }

}
