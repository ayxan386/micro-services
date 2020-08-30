package controllers

import dtos.post.PostRequest
import error.{BodyNotProvided, UnExpectedError}
import javax.inject.{Inject, Singleton}
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents, Handler}
import play.libs.typedmap.TypedKey
import services.PostService
import sun.jvm.hotspot.debugger.Page
import typedKeys.TypedKeys

import scala.concurrent.ExecutionContext

@Singleton
class PostController @Inject()(
    cc: ControllerComponents,
    postService: PostService)(implicit ex: ExecutionContext)
    extends AbstractController(cc) {

  def getAll(page: Int, pageSize: Int) = ???

  def getById = ???

  def add = Action.async { implicit request =>
    request.body.asJson match {
      case Some(jsonBody) =>
        jsonBody
          .asOpt[PostRequest]
          .map(req => (req, request.headers.get(TypedKeys.userType).get))
          .map((req, username) => postService.add(req, username))
          .getOrElse(throw UnExpectedError())
          .map(res => Ok(Json.toJson(res)))
      case None => throw BodyNotProvided()
    }
  }
}
