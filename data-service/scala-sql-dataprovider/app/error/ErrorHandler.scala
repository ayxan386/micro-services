package error

import play.api.http.HttpErrorHandler
import play.api.libs.json.Json
import play.api.mvc.Results.Status
import play.api.mvc.{RequestHeader, Result}

import scala.concurrent.Future

case class ErrorResponse(message: String, stackTrace: Seq[String], status: Int)

object ErrorResponse {
  implicit val writes = Json.writes[ErrorResponse]
}

class ErrorHandler extends HttpErrorHandler {
  override def onClientError(request: RequestHeader,
                             statusCode: Int,
                             message: String): Future[Result] =
    Future.successful(buildResponse(message, statusCode, Seq.empty))

  override def onServerError(request: RequestHeader,
                             exception: Throwable): Future[Result] = {
    exception match {
      case e: CommonHttpError =>
        Future.successful(buildResponse(e.message, e.status, Seq.empty))
      case _ =>
        Future.successful(
          buildResponse(exception.getLocalizedMessage,
                        500,
                        exception.getStackTrace.map(s => s.toString)))

    }
  }

  def buildResponse(message: String,
                    status: Int,
                    stackTrace: Iterable[String]) = {
    Status(status)(
      Json.toJson(buildErrorResponse(message, status, stackTrace.toSeq)))
  }

  def buildErrorResponse(message: String,
                         status: Int,
                         stackTrace: Seq[String]) =
    ErrorResponse(message, stackTrace, status)
}
