package dtos.comment

import play.api.libs.json.Json

case class CommentRequest(postId: Long, body: String) {}

object CommentRequest {
  implicit val reads = Json.reads[CommentRequest]
}
