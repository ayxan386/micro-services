package controllers

import dtos.ResponseDTO
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

  val SUCCESS_MESSAGE: String = "success"

  def getAll(page: Int, pageSize: Int) = Action.async { implicit request =>
    postService
      .getAllPaged(page, pageSize)
      .map(list => ResponseDTO.wrapIn(list, SUCCESS_MESSAGE))
      .map(res => Ok(Json.toJson(res)))
  }

  def getById = Action.async { implicit request =>
    request.body.asJson match {
      case Some(jsonBody) =>
        val id = (jsonBody \ "id").as[Int]
        postService
          .getById(id)
          .map(p => ResponseDTO.wrapIn(p, SUCCESS_MESSAGE))
          .map(res => Ok(Json.toJson(res)))
      case None => throw BodyNotProvided()
    }
  }

  def add = Action.async { implicit request =>
    request.body.asJson match {
      case Some(jsonBody) =>
        jsonBody
          .asOpt[PostRequest]
          .map(req => (req, request.attrs.get(TypedKeys.userType).get))
          .map(tup => postService.add(tup._1, tup._2))
          .getOrElse(throw UnExpectedError())
          .map(res => ResponseDTO.wrapIn(res, SUCCESS_MESSAGE))
          .map(res => Ok(Json.toJson(res)))
      case None => throw BodyNotProvided()
    }
  }
}
