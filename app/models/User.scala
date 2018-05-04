
package models

case class User(id: Long, name: String)

object User {
  implicit val userFormat = Json.format[User]
}