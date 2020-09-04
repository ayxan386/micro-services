package controllers

import dtos.ResponseDTO
import dtos.comment.CommentRequest
import error.{BodyNotProvided, UnExpectedError}
import javax.inject.{Inject, Singleton}
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents, Handler}
import services.CommentService
import typedKeys.TypedKeys

import scala.concurrent.ExecutionContext

@Singleton
class CommentController @Inject()(
    cc: ControllerComponents,
    commentService: CommentService)(implicit ex: ExecutionContext)
    extends AbstractController(cc) {

  private val SUCCESS_MESSAGE = "success"

  def add = Action.async { implicit request =>
    request.body.asJson match {
      case Some(jsonBody) =>
        jsonBody
          .asOpt[CommentRequest]
          .map(req => (req, request.attrs.get(TypedKeys.userType)))
          .map(tup => commentService.add(tup._1, tup._2))
          .getOrElse(throw UnExpectedError())
          .map(sr => ResponseDTO.wrapIn(sr, SUCCESS_MESSAGE))
          .map(res => Ok(Json.toJson(res)))
      case None => throw BodyNotProvided()
    }
  }
}
