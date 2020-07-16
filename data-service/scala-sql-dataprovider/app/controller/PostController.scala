package controller

import dto.{PostDTO, UserDTO}
import errors.{NotFoundError, RequestBodyMissingError}
import javax.inject.{Inject, Singleton}
import models.User
import play.api.libs.json.Json
import play.api.mvc._
import service.PostService
import util.MyAttrs

@Singleton
class PostController @Inject()(cc: ControllerComponents, postService: PostService) extends AbstractController(cc) {

  def getAllPosts(page: Int = 0, pageSize: Int = 20): Action[AnyContent] = Action { request =>
    val posts = postService.getAllPaged(page, pageSize)
    posts match {
      case Nil => throw NotFoundError("Posts list is empty", None)
      case _ => Ok(Json.toJson(posts))
    }
  }

  def savePost(): Action[AnyContent] = Action { request: Request[AnyContent] =>
    request.body.asJson match {
      case Some(jsonBody) =>
        val postDTO = PostDTO(title = (jsonBody \ "title").asOpt[String], body = (jsonBody \ "body").asOpt[String])
        val username = request.attrs.get(MyAttrs.username).orNull
        val o = postDTO.copy(author = UserDTO.fromEntity(User.findByUsername(username)))
        val res = postService.add(o)
        Ok(Json.toJson(res))
      case None => throw RequestBodyMissingError()
    }
  }

  def updatePost(): Action[AnyContent] = Action {
    request =>
      request.body.asJson match {
        case Some(jsonBody) =>
          val postDTO = PostDTO(
            id = (jsonBody \ "id").asOpt[Long].orElse((jsonBody \ "id").asOpt[String].map(_.toLong)),
            body = (jsonBody \ "body").asOpt[String],
            title = (jsonBody \ "title").asOpt[String])
          Ok(Json.toJson(postService.update(postDTO)))
        case None => throw RequestBodyMissingError()
      }
  }
}
