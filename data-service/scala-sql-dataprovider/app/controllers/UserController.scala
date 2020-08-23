package controllers

import javax.inject.{Inject, Singleton}
import play.api.Logger
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}
import services.UserService
import typedKeys.TypedKeys

import scala.concurrent.ExecutionContext

@Singleton
class UserController @Inject()(
    cc: ControllerComponents,
    userService: UserService)(implicit ex: ExecutionContext)
    extends AbstractController(cc) {

  val logger = Logger(this.getClass)

  def saveUser = ???

  def getUserByName(username: String) = Action.async { implicit request =>
    userService
      .getByNickname(username)
      .map(u => Ok(Json.toJson(u)))
  }

  def getMe = Action.async { implicit request =>
    request.attrs
      .get(TypedKeys.userType)
      .map(username => userService.getByNickname(username))
      .get
      .map(u => Ok(Json.toJson(u)))
  }

}
