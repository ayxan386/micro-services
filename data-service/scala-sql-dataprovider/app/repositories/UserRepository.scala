package repositories

import io.getquill.{PostgresAsyncContext, SnakeCase}
import javax.inject.{Inject, Singleton}
import models.User

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class UserRepository @Inject()(implicit ex: ExecutionContext) {
  lazy val ctx = new PostgresAsyncContext[SnakeCase](SnakeCase, "db.default")
  import ctx._

  private val simpleUser = quote {
    querySchema[User]("t_user")
  }

  def findFirstByNickname(username: String): Future[Option[User]] = {
    val q = quote { nickname: String =>
      simpleUser.filter(_.nickname == nickname)
    }
    ctx.run(q(lift(username))).map(_.headOption)
  }

}
