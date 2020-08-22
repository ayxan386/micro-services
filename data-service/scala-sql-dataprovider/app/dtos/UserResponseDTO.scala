package dtos

import play.api.libs.json.Json

case class UserResponseDTO(email: Option[String],
                           name: Option[String],
                           nickname: String,
                           profilePicture: Option[String],
                           surname: Option[String]) {}

object UserResponseDTO {
  implicit val writes = Json.writes[UserResponseDTO]
}
