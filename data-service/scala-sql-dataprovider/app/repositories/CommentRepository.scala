package repositories

import io.getquill.{PostgresAsyncContext, SnakeCase}
import javax.inject.{Inject, Singleton}
import models.Comment

import scala.concurrent.ExecutionContext

@Singleton
class CommentRepository @Inject()(implicit ex: ExecutionContext) {
  lazy val ctx = new PostgresAsyncContext[SnakeCase](SnakeCase, "db.default")

  import ctx._

  private val simpleComment = quote {
    querySchema[Comment]("t_comment")
  }

  def save(comment: Comment) = {
    val q = quote { c: Comment =>
      simpleComment.insert(c).returningGenerated(_.id)
    }
    ctx.run(q(lift(comment))).map(id => comment.copy(id = id))
  }
}
