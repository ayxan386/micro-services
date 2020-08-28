package dtos

import play.api.libs.json.Json.toJson
import play.api.libs.json.{
  JsArray,
  JsNumber,
  JsObject,
  JsString,
  JsValue,
  Json,
  Writes
}

class ResponseDTO[T](val data: T, val message: String) {}

object ResponseDTO {
  implicit def writes[T](implicit fmt: Writes[T]): Writes[ResponseDTO[T]] =
    new Writes[ResponseDTO[T]] {
      def writes(ts: ResponseDTO[T]) =
        JsObject(
          Seq(
            "data" -> Json.toJson(ts.data),
            "message" -> JsString(ts.message)
          ))
    }
  def wrappedIn[T](data: T, message: String) = new ResponseDTO(data, message)
}
