package controller

import errors.NotFoundError
import javax.inject.{Inject, Singleton}
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import service.PostService

@Singleton
class PostController @Inject()(cc: ControllerComponents, postService: PostService) extends AbstractController(cc) {

  def getAllPosts(page: Int = 0, pageSize: Int = 20): Action[AnyContent] = Action {
    val posts = postService.getAllPaged(page, pageSize)
    posts match {
      case Nil => NotFound(Json.toJson(NotFoundError("Posts list is empty", None)))
      case _ => Ok(Json.toJson(posts))
    }
  }
}
