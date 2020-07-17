package errors.notfound

import errors.GeneralError

case class MissingValue(paramName: String) extends GeneralError(
  message = s"Missing paramenter ${paramName}",
  status = 400,
  cause = None)
