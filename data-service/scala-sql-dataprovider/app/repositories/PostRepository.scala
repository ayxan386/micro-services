package repositories

import java.time.LocalDateTime

import dtos.post.PostResponse
import error.PostNotFoundException
import io.getquill.{Ord, PostgresAsyncContext, SnakeCase}
import javax.inject.{Inject, Singleton}
import models.Post

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class PostRepository @Inject()(implicit ex: ExecutionContext) {

  lazy val ctx = new PostgresAsyncContext[SnakeCase](SnakeCase, "db.default")

  import ctx._

  private val simplePost = quote {
    querySchema[Post]("t_post")
  }

  def getById(id: Int): Future[Option[Post]] = {
    val q = quote { id: Int =>
      simplePost.filter(_.id == id)
    }
    ctx.run(q(lift(id))).map(q => q.headOption)
  }

  def getAllPaged(page: Int, pageSize: Int): Future[List[Post]] = {
    val q = quote { (page: Int, pageSize: Int) =>
      simplePost
        .sortBy(p => p.createdOn)(ord = Ord.descNullsLast)
        .drop(page * pageSize)
        .take(pageSize)
    }
    ctx.run(q(lift(page), lift(pageSize)))
  }

  def save(post: Post): Future[Post] = {
    val q = quote { post: Post =>
      simplePost.insert(post).returningGenerated(_.id)
    }
    ctx
      .run(
        q(lift(post.copy(createdOn = Some(LocalDateTime.now()),
                         updatedOn = Some(LocalDateTime.now())))))
      .map(id => post.copy(id = id))
  }

  def updatePost(post: Post): Future[Post] = {
    val q = quote { post: Post =>
      simplePost
        .filter(_.id == post.id)
        .update(post)
    }
    getById(post.id.toInt)
      .map(op => op.getOrElse(throw PostNotFoundException()))
      .map(
        p =>
          p.copy(body = post.body,
                 title = post.title,
                 updatedOn = Some(LocalDateTime.now())))
      .map(p => ctx.run(q(lift(p))).map(r => p))
      .flatMap(f => f)
  }
}
