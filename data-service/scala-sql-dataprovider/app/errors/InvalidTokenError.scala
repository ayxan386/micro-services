package errors

case class InvalidTokenError() extends Exception("Token is invalid")
