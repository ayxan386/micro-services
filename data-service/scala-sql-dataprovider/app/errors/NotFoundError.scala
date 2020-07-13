package errors

import play.api.libs.json.Json

case class NotFoundError(msg: String, source: Option[String]) extends GeneralError(msg, 404, source)

object NotFoundError {
  implicit val writes = Json.writes[NotFoundError]
}
