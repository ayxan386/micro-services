package models

import java.time.LocalDateTime

case class Post(id: Long,
                title: String,
                body: String,
                authorId: Long,
                attachment: Option[String],
                createdOn: Option[LocalDateTime],
                updatedOn: Option[LocalDateTime])
