package services.impl

import dtos.comment.{CommentRequest, CommentResponse}
import error.{HeaderNotProvidedError, UserNotFoundException}
import javax.inject.{Inject, Singleton}
import models.Comment
import repositories.{CommentRepository, UserRepository}
import services.{CommentService, UserService}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class CommentServiceImpl @Inject()(
    commentRepository: CommentRepository,
    userRepository: UserRepository,
    userService: UserService)(implicit ex: ExecutionContext)
    extends CommentService {
  override def add(req: CommentRequest,
                   nickname: Option[String]): Future[CommentResponse] = {
    nickname
      .map(userRepository.findFirstByNickname)
      .getOrElse(throw HeaderNotProvidedError())
      .map(op => op.getOrElse(throw UserNotFoundException()))
      .map(user => reqToModel(req).copy(authorId = user.id))
      .map(commentRepository.save)
      .flatMap(f => f)
      .map(modelToResponse)
      .flatMap(f => f)
  }

  override def getByPostId(postId: Long): Future[List[CommentResponse]] =
    commentRepository
      .getAllByPostId(postId)
      .map(lc => lc.map(modelToResponse))
      .map(lf => Future.sequence(lf))
      .flatMap(f => f)

  private def reqToModel(req: CommentRequest) =
    Comment(id = -1L, body = req.body, postId = req.postId, authorId = -1L)

  private def modelToResponse(m: Comment) = {
    userService
      .getById(m.authorId)
      .map(
        user =>
          CommentResponse(commentId = m.id,
                          postId = m.postId,
                          body = m.body,
                          author = user))
  }

}
