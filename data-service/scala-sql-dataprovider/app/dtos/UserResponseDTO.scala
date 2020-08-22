package dtos

import play.api.libs.json.Json

case class UserResponseDTO(email: String, name: String, nickname: String, profilePicture: String, surname: String) {

}

object UserResponseDTO{
  implicit val writes = Json.writes[UserResponseDTO]
}