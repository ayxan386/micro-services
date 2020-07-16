package errors

import play.api.libs.json.{JsValue, Json, Writes}

abstract class GeneralError(val message: String, val status: Int, val cause: Option[String]) extends Exception {

}

case class CommonError(msg: String, stat: Int) extends GeneralError(message = msg, status = stat, None)

object CommonError {
  implicit val writes = new Writes[CommonError] {
    override def writes(o: CommonError): JsValue = Json.obj(
      "message" -> o.msg,
      "status" -> o.stat
    )
  }
}