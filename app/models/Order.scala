
package models

case class Order(id: Long,userID: Long,paid: Boolean )

object Order {
  implicit val orderFormat = Json.format[Order]
}