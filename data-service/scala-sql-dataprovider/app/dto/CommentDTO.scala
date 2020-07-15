package dto

import models.Comment
import play.api.libs.json.Json

case class CommentDTO(
                       id: Long,
                       body: Option[String] = None,
                       postId: Option[Long] = None,
                       authorId: Option[Long] = None)


object CommentDTO {
  implicit val writes = Json.writes[CommentDTO]
  implicit val reads = Json.reads[CommentDTO]

  def fromEntity(ent: Comment): CommentDTO = CommentDTO(id = ent.id, body = ent.body, authorId = ent.authorId, postId = ent.postId)
}
