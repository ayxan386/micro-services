package error

case class UnExpectedError()
  extends CommonHttpError(message = "Something unexpected happened",
    status = 500)
