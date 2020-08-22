package controllers

import javax.inject.{Inject, Singleton}
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents, Handler}
import services.UserService

import scala.concurrent.ExecutionContext

@Singleton
class UserController @Inject()(
    cc: ControllerComponents,
    userService: UserService)(implicit ex: ExecutionContext)
    extends AbstractController(cc) {
  def saveUser = ???

  def getUserByName(username: String) = {
    userService
      .getByNickname(username)
      .map(u => Ok(Json.toJson(u)))
  }

  def getMe = ???

}
