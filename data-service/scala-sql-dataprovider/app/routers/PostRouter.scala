package routers

import controller.PostController
import javax.inject.Inject
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._

class PostRouter @Inject()(postController: PostController) extends SimpleRouter {
  override def routes: Routes = {
    case GET(p"/all" ? q"page=${page}" & q"pageSize=${pageSize}") => postController.getAllPosts(page.toInt, pageSize.toInt)
  }
}
