package models

case class User(id: Long,
                email: Option[String],
                name: Option[String],
                nickname: String,
                profilePicture: Option[String],
                surname: Option[String]) {}
