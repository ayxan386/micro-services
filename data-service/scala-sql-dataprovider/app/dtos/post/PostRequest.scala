package dtos.post

import play.api.libs.json.Json

case class PostRequest(id: Option[Int], body: String, title: String) {}

object PostRequest {
  implicit val reads = Json.reads[PostRequest]
}
