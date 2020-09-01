package error

case class PostNotFoundException()
  extends CommonHttpError("Post not found", 404) {}
