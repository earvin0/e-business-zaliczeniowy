
package models

case class Review(userID: Long,productID: Long,grade: Short,review: String )

object Review {
  implicit val reviewFormat = Json.format[Review]
}