package models

case class Cart(id: Long,productID: Long,quantity: Int )

object Cart {
  implicit val cartFormat = Json.format[Cart]
}