package repositories

import javax.inject.{Inject, Singleton}

import scala.concurrent.ExecutionContext

@Singleton
class PostRepository @Inject()(implicit ex: ExecutionContext) {
  lazy val ctx = new PostgresAsyncContext[SnakeCase](SnakeCase, "db.default")

  import ctx._

  private val simplePost = quote{
    querySchema[Post]("t_post")
  }
}
