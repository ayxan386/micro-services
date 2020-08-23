package error

case class InvalidToken()
  extends CommonHttpError(message = "Provided token is not valid",
    status = 403) {}
