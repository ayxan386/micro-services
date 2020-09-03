package routers

import controllers.PostController
import javax.inject.{Inject, Singleton}
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._

@Singleton
class PostRouter @Inject()(postController: PostController) extends SimpleRouter {
  override def routes: Routes = {
    case POST(p"") => postController.add
    case GET(p"")  => postController.getById
    case GET(p"/all" ? q"page=$page" & q"pageSize=$pageSize") =>
      postController.getAll(page.toInt, pageSize.toInt)
    case PUT(p"") => postController.updatePost
  }
}
