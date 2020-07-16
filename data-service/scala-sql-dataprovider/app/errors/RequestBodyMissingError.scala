package errors

case class RequestBodyMissingError() extends GeneralError(message = "Request body is missing", status = 400, None)


