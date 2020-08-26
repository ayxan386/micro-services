package controllers

import dtos.UserRequest
import error.BodyNotProvided
import javax.inject.{Inject, Singleton}
import play.api.Logger
import play.api.libs.json.Json
import play.api.mvc.{
  AbstractController,
  Action,
  AnyContent,
  ControllerComponents
}
import services.UserService
import typedKeys.TypedKeys

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class UserController @Inject()(
    cc: ControllerComponents,
    userService: UserService)(implicit ex: ExecutionContext)
    extends AbstractController(cc) {

  val logger = Logger(this.getClass)

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

  def deleteUser: Action[AnyContent] = ???

  def updateUser = Action.async { implicit request =>
    request.body.asJson match {
      case Some(jsonBody) =>
        val req = jsonBody.as[UserRequest]
        userService
          .update(req)
          .map(req => Ok(Json.toJson(req)))
      case None => throw BodyNotProvided()
    }
  }

  def addUser = Action.async { implicit request =>
    request.body.asJson match {
      case Some(jsonBody) =>
        val req = jsonBody.as[UserRequest]
        userService
          .add(req)
          .map(req => Ok(Json.toJson(req)))
      case None => throw BodyNotProvided()
    }
  }
}
