package models

import play.api.libs.json._

case class Cart(id: Long,productID: Long,quantity: Int )

object Cart {
  implicit val cartFormat = Json.format[Cart]
}