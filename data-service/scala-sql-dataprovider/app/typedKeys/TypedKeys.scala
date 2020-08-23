package typedKeys

import play.api.libs.typedmap.TypedKey

object TypedKeys {
  val userType: TypedKey[String] = TypedKey[String]("userType")
}
