package services

import com.google.inject.ImplementedBy
import dtos.post.{PostRequest, PostResponse}
import services.impl.PostServiceImpl

import scala.concurrent.Future

@ImplementedBy(classOf[PostServiceImpl])
trait PostService {

  def add(req: PostRequest, username: String): Future[PostResponse]

}
