package actions

import akka.http.scaladsl.model.headers.Authorization
import akka.stream.Materializer
import errors.{HeaderNotFoundError, InvalidTokenError}
import javax.inject.{Inject, Singleton}
import play.api.libs.typedmap.TypedKey
import play.api.mvc.{Filter, RequestHeader, Result}
import util.JwtUtils

import scala.concurrent.Future

@Singleton
class JwtFilter @Inject()(matV: Materializer, jwtUtils: JwtUtils) extends Filter {

  override implicit def mat: Materializer = matV

  override def apply(f: RequestHeader => Future[Result])(rh: RequestHeader): Future[Result] = {
    val header = rh.headers.get(Authorization.name)
    val token = header.fold(
      //      CompletableFuture.completedFuture(forbidden(Json.toJson(HeaderNotFoundError())))
      throw HeaderNotFoundError()
    ) {
      auth => jwtUtils.extractToken(auth)
    }
    if (jwtUtils.isValid(token)) {
      f.apply(rh.addAttr(TypedKey[String]("username"), jwtUtils.getUserName(token)))
    } else {
      throw InvalidTokenError()
    }
  }

}