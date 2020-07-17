package errors.notfound

import errors.GeneralError

case class PostNotFoundError() extends GeneralError(message = "Post with that id could not be found", status = 404, None) {

}
