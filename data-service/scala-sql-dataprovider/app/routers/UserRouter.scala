package routers

import controllers.UserController
import javax.inject.{Inject, Singleton}
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._

@Singleton
class UserRouter @Inject()(userController: UserController)
    extends SimpleRouter {
  override def routes: Routes = {
    case GET(p"" ? q"username=$username") =>
      userController.getUserByName(username)
    case GET(p"/me") => userController.getMe
    case POST(p"")   => userController.addUser
    case POST(p"/save")   => userController.saveLogo
    case PUT(p"")    => userController.updateUser
    case DELETE(p"") => userController.deleteUser
  }
}
