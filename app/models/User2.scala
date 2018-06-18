package models

import play.api.libs.json._

case class User2(id: Long, name: Option[String], email: Option[String], token: String)

object User2 {
  implicit val userFormat = Json.format[User2]
}