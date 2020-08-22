package util

import java.util.Date

import errors.HeaderNotValidError
import io.jsonwebtoken.Jwts
import javax.inject.{Inject, Singleton}
import play.api.Configuration

@Singleton
class JwtUtils @Inject()(config: Configuration) {
  val secret: String = config.get[String]("jwt.secret.key")
  val headerName = "Bearer "

  def extractToken(str: String): String = {
    if (str.startsWith(headerName)) {
      str.substring(headerName.length)
    } else {
      throw HeaderNotValidError()
    }
  }

  def isValid(token: String): Boolean = {
    getClaims(token)
      .getExpiration
      .after(new Date())
  }

  def getUserName(token: String): String = {
    getClaims(token)
      .getSubject
  }

  private def getClaims(token: String) = {
    Jwts.
      parser()
      .setSigningKey(secret)
      .parseClaimsJws(token)
      .getBody
  }
}
