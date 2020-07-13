package service.impl

import dto.PostDTO
import javax.inject.Singleton
import models.Post
import service.PostService

@Singleton
class PostServiceImpl extends PostService {
  override def getAll: Seq[PostDTO] = Post.findAll().map(PostDTO.fromEntity)

  override def getById(req: PostDTO): PostDTO = ???

  override def add(req: PostDTO): PostDTO = ???

  override def update(req: PostDTO): PostDTO = ???

  override def delete(req: PostDTO): PostDTO = ???

  override def getAllPaged(page: Int, pageSize: Int): Seq[PostDTO] = Post.findAllPaged(page, pageSize).map(PostDTO.fromEntity)
}
