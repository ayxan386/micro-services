package dto

import models.User
import play.api.libs.json.Json

case class UserDTO(
                    id: Long,
                    email: Option[String] = None,
                    name: Option[String] = None,
                    nickname: Option[String] = None,
                    profilePicture: Option[String] = None,
                    surname: Option[String] = None)


object UserDTO {
  implicit val writes = Json.writes[UserDTO]
  implicit val reads = Json.reads[UserDTO]

  def fromEntity(ent: User): UserDTO = {
    UserDTO(
      id = ent.id,
      email = ent.email,
      name = ent.name,
      nickname = ent.nickname,
      profilePicture = ent.profilePicture,
      surname = ent.surname
    )
  }

  def fromEntity(ento: Option[User]): Option[UserDTO] = {
    ento match {
      case Some(ent) => Option(fromEntity(ent))
      case None => None
    }
  }
}
