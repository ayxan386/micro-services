package errors

import play.api.http.HttpErrorHandler
import play.api.libs.json.Json
import play.api.mvc.Results._
import play.api.mvc._

import scala.concurrent.Future

class ErrorHandler extends HttpErrorHandler {
  override def onClientError(request: RequestHeader, statusCode: Int, message: String): Future[Result] = {
    Future.successful(
      Status(statusCode)(Json.toJson(CommonError(message, statusCode)))
    )
  }

  override def onServerError(request: RequestHeader, exception: Throwable): Future[Result] = {
    Future.successful(
      exception match {
        case cs: GeneralError => Status(cs.status)(Json.toJson(CommonError(cs.message, cs.status)))
        case x: Throwable => Status(500)(Json.toJson(CommonError(x.getMessage, 500)))
      })
  }
}
