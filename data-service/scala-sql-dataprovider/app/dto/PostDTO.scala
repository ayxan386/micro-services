package dto

import java.time.ZonedDateTime

import models.Post
import play.api.libs.json.{Json, JsonConfiguration, OptionHandlers}

case class PostDTO(id: Option[Long] = None,
                   attachment: Option[String] = None,
                   body: Option[String] = None,
                   title: Option[String] = None,
                   author: Option[UserDTO] = None,
                   comments: Seq[CommentDTO] = Nil,
                   updatedOn: Option[ZonedDateTime] = None)

object PostDTO {
  def fromEntity(ent: Post): PostDTO = PostDTO(
    id = Option(ent.id),
    attachment = ent.attachment,
    body = ent.body,
    title = ent.title,
    author = UserDTO.fromEntity(ent.author),
    comments = ent.comments.map(CommentDTO.fromEntity),
    updatedOn = ent.updatedOn
  )

  implicit val writes = Json.writes[PostDTO]
  implicit val reads = Json.reads[PostDTO]
  implicit val testFormat = Json.format[PostDTO]
  implicit val config = JsonConfiguration(optionHandlers = OptionHandlers.Default)
}