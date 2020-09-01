package repositories

import dtos.post.PostResponse
import io.getquill.{PostgresAsyncContext, SnakeCase}
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

  def getAllPaged(page: Int, pageSize: Int): Future[List[Post]] = {
    val q = quote { (page: Int, pageSize: Int) =>
      simplePost.drop(page * pageSize).take(pageSize)
    }
    ctx.run(q(lift(page), lift(pageSize)))
  }

  def save(post: Post): Future[Post] = {
    val q = quote { post: Post =>
      simplePost.insert(post).returningGenerated(_.id)
    }
    ctx.run(q(lift(post))).map(id => post.copy(id = id))
  }
}
