package routers

import controllers.CommentController
import javax.inject.{Inject, Singleton}
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._

@Singleton
class CommentRouter @Inject()(commentController: CommentController) extends SimpleRouter{
  override def routes: Routes = {
    case POST(p"") => commentController.add
  }
}
