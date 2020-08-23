package error

case class HeaderNotProvidedError()
  extends CommonHttpError(message = "Authentication token not provided",
    status = 401) {}
