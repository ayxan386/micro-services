package dtos.post

import play.api.libs.json.Json

case class PostRequest(body: String, title: String) {}

object PostRequest {
  implicit val reads = Json.reads[PostRequest]
}
