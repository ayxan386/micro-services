package errors

case class HeaderNotValidError() extends Exception("Auth header does not match the required pattern")
