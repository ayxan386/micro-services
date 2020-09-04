package services

import com.google.inject.ImplementedBy
import dtos.comment.{CommentRequest, CommentResponse}
import services.impl.CommentServiceImpl

import scala.concurrent.Future

@ImplementedBy(classOf[CommentServiceImpl])
trait CommentService {
  def add(req: CommentRequest,
          nickname: Option[String]): Future[CommentResponse]

}
