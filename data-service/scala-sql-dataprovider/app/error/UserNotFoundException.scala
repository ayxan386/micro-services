package error

case class UserNotFoundException()
  extends CommonHttpError("User not found", 404) {}
