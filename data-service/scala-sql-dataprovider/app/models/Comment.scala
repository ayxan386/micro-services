package models

case class Comment(id: Long, body: String, postId: Long, authorId: Long)
