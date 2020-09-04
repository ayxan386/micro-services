package dtos.comment

import dtos.user.UserResponseDTO
import play.api.libs.json.Json

case class CommentResponse(postId: Long,
                           commentId: Long,
                           body: String,
                           author: UserResponseDTO)

object CommentResponse {
  implicit val writes = Json.writes[CommentResponse]
}
