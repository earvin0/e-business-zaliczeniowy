package models

import play.api.libs.json._

case class Review(userID: Long, productID: Long, grade: Int, review: String)

object Review {
  implicit val reviewFormat = Json.format[Review]
}