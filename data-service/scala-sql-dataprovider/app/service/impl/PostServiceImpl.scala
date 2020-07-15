package service.impl

import java.time.ZonedDateTime

import dto.PostDTO
import javax.inject.Singleton
import models.Post
import play.api.Logger
import service.PostService

@Singleton
class PostServiceImpl extends PostService {
  override def getAll: Seq[PostDTO] = Post.findAll().map(PostDTO.fromEntity)

  val log = Logger.apply(classOf[PostServiceImpl])

  override def getById(req: PostDTO): PostDTO = ???

  override def add(req: PostDTO): PostDTO = {
    val post = Post
      .fromDto(req)
    println(s"post entity before save ${post}")
    val saved = Post.create(
      id = post.id,
      attachment = post.attachment,
      body = post.body,
      title = post.title,
      authorId = post.author.map(_.id),
      createdOn = Option(ZonedDateTime.now()),
      updatedOn = Option(ZonedDateTime.now())
    )
    println(s"post after saving ${saved}")
    PostDTO.fromEntity(
      saved
    )
  }

  override def update(req: PostDTO): PostDTO = ???

  override def delete(req: PostDTO): PostDTO = ???

  override def getAllPaged(page: Int, pageSize: Int): Seq[PostDTO] = Post.findAllPaged(page, pageSize).map(PostDTO.fromEntity)
}
