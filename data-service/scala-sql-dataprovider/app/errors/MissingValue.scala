package errors

case class MissingValue(paramName: String) extends GeneralError(
  message = s"Missing paramenter ${paramName}",
  status = 400,
  cause = None)

