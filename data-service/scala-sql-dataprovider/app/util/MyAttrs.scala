package util

import play.api.libs.typedmap.TypedKey

object MyAttrs {
  val username: TypedKey[String] = TypedKey("username")
}
