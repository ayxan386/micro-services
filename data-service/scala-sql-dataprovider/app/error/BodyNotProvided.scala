package error

case class BodyNotProvided()
  extends CommonHttpError(message = "Request body is empty", status = 400) {}
