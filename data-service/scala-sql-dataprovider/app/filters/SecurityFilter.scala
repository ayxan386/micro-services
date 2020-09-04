package filters

import akka.stream.Materializer
import error.{HeaderNotProvidedError, InvalidToken}
import javax.inject.{Inject, Singleton}
import play.api.mvc.{Filter, RequestHeader, Result}
import typedKeys.TypedKeys
import utils.JwtUtils

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class SecurityFilter @Inject()(jwtUtils: JwtUtils)(
    implicit val mat: Materializer,
    implicit val ex: ExecutionContext)
    extends Filter {
  private val HEADER_NAME = "Authorization"
  private val BEARER = "Bearer "
  override def apply(nextFilter: RequestHeader => Future[Result])(
      rh: RequestHeader): Future[Result] = {
    if (rh.method.contains("Option")) {
      nextFilter(rh)
    } else {
      rh.headers
        .get(HEADER_NAME)
        .filter(_.startsWith(BEARER))
        .orElse(throw HeaderNotProvidedError())
        .map(_.substring(BEARER.length))
        .filter(jwtUtils.isValid)
        .orElse(throw InvalidToken())
        .map(jwtUtils.getUsername)
        .map(rh.addAttr(TypedKeys.userType, _))
        .map(nextFilter(_))
        .get
    }
  }
}
