package dto

import java.time.ZonedDateTime

import models.{Comment, Post, User}
import play.api.libs.json.Json

case class PostDTO(id: Long,
                   attachment: Option[String] = None,
                   body: Option[String] = None,
                   title: Option[String] = None,
                   author: Option[User] = None,
                   comments: Seq[Comment] = Nil,
                   updatedOn: Option[ZonedDateTime] = None)

object PostDTO {
  def fromEntity(ent: Post): PostDTO = PostDTO(
    id = ent.id,
    attachment = ent.attachment,
    body = ent.body,
    title = ent.title,
    author = ent.author,
    comments = ent.comments,
    updatedOn = ent.updatedOn
  )

  implicit val writes = Json.writes[PostDTO]
}