
package models

case class OrderedProduct(orderID: Long,productID: Long,quantity: Int )

object OrderedProduct {
  implicit val orderedProductFormat = Json.format[OrderedProduct]
}