package routers

import controllers.UserController
import javax.inject.Inject
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._

class UserRouter @Inject()(userController: UserController)
    extends SimpleRouter {
  override def routes: Routes = {
    case GET(p"" ? q"username=$username") =>
      userController.getUserByName(username)
    case GET(p"/me") => userController.getMe
    case POST(p"")   => userController.saveUser
  }
}
