package errors

import play.api.libs.json.Json

case class HeaderNotFoundError() extends Exception("Auth header not found")


