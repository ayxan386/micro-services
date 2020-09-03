package dtos.post

import java.time.LocalDateTime

import dtos.user.UserResponseDTO
import play.api.libs.json.Json

case class PostResponse(id: Long,
                        title: String,
                        body: String,
                        author: Option[UserResponseDTO],
                        updatedOn: Option[LocalDateTime])

object PostResponse {
  implicit val writes = Json.writes[PostResponse]
}
