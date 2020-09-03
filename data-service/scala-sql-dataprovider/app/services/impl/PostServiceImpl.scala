package services.impl

import dtos.post
import dtos.post.{PostRequest, PostResponse}
import error.{PostNotFoundException, UserNotFoundException}
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

  override def getAllPaged(page: Int,
                           pageSize: Int): Future[List[PostResponse]] =
    postRepository
      .getAllPaged(page, pageSize)
      .map(list => list.map(completeModelWithUser))
      .map(lf => Future.sequence(lf))
      .flatMap(f => f)

  override def getById(id: Int): Future[PostResponse] =
    postRepository
      .getById(id)
      .map(op => op.getOrElse(throw PostNotFoundException()))
      .map(completeModelWithUser)
      .flatMap(f => f)

  override def update(req: PostRequest): Future[PostResponse] =
    postRepository
      .updatePost(reqToModel(req))
      .map(completeModelWithUser)
      .flatMap(f => f)

  private def reqToModel(res: PostRequest) =
    Post(id = res.id.getOrElse(-1).toLong,
         title = res.title,
         body = res.body,
         authorId = -1L,
         None,
         None,
         None)

  private def modelToResponse(post: Post) =
    PostResponse(id = post.id,
                 title = post.title,
                 body = post.body,
                 author = None)

  private def completeModelWithUser(m: Post) =
    userService
      .getById(m.authorId)
      .map(uRes => modelToResponse(m).copy(author = Some(uRes)))
}
