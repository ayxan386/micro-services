package dtos.user

import play.api.libs.json.Json

case class UserRequest(nickname: String,
                       name: Option[String],
                       surname: Option[String],
                       profilePicture: Option[String],
                       email: Option[String])

object UserRequest{
  implicit val reads = Json.reads[UserRequest]
}