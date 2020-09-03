package services

import com.google.inject.ImplementedBy
import dtos.post.{PostRequest, PostResponse}
import play.api.mvc.Result
import services.impl.PostServiceImpl

import scala.concurrent.Future

@ImplementedBy(classOf[PostServiceImpl])
trait PostService {
  def update(req: PostRequest): Future[PostResponse]

  def getById(id: Int): Future[PostResponse]

  def getAllPaged(page: Int, pageSize: Int): Future[List[PostResponse]]

  def add(req: PostRequest, username: String): Future[PostResponse]

}
