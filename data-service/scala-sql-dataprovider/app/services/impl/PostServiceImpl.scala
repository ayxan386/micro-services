package services.impl

import dtos.post
import dtos.post.{PostRequest, PostResponse}
import error.UserNotFoundException
import javax.inject.{Inject, Singleton}
import models.Post
import repositories.{PostRepository, UserRepository}
import services.{PostService, UserService}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class PostServiceImpl @Inject()(
    postRepository: PostRepository,
    userRepository: UserRepository,
    userService: UserService)(implicit ex: ExecutionContext)
    extends PostService {

  override def add(req: PostRequest, username: String): Future[PostResponse] = {
    userRepository
      .findFirstByNickname(username)
      .map(op => op.getOrElse(throw UserNotFoundException()))
      .map(u => reqToModel(req).copy(authorId = u.id))
      .map(m => postRepository.save(m))
      .flatMap(f => f)
      .map(completeModelWithUser)
      .flatMap(f => f)
  }

  private def reqToModel(res: PostRequest) =
    Post(id = -1L,
         title = res.title,
         body = res.body,
         authorId = -1L,
         None,
         None,
         None)

  private def modelToResponse(post: Post) =
    PostResponse(title = post.title, body = post.body, author = None)

  private def completeModelWithUser(m: Post) =
    userService
      .getById(m.authorId)
      .map(uRes => modelToResponse(m).copy(author = Some(uRes)))
}
