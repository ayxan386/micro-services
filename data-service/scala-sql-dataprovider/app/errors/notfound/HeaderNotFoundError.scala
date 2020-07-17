package errors.notfound

case class HeaderNotFoundError() extends Exception("Auth header not found")
