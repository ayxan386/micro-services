package dtos.post

import dtos.user.UserResponseDTO
import play.api.libs.json.Json

case class PostResponse(id: Long,
                        title: String,
                        body: String,
                        author: Option[UserResponseDTO])

object PostResponse {
  implicit val writes = Json.writes[PostResponse]
}
