package utils

import java.util.Date

import io.jsonwebtoken.{Claims, Jwts}
import javax.inject.Singleton

@Singleton
class JwtUtils {
  private val SECRET_KEY = "secret"
  def extractBody(token: String): Claims =
    Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody

  def isExpired(token: String): Boolean =
    extractBody(token).getExpiration.before(new Date())

  def isValid(token: String): Boolean = !isExpired(token)

  def getUsername(token: String): String = extractBody(token).getSubject
}
