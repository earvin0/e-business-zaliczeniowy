package models

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class CartRepository @Inject() (dbConfigProvider: DatabaseConfigProvider, productRepository: ProductRepository, orderRepository: OrderRepository)(implicit ec: ExecutionContext) {
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  private class OrderedProductTable(tag: Tag) extends Table[OrderedProduct](tag,"orderedProduct"){

    def orderID = column[Long]("orderID")

    def uorderID_fk = foreignKey("orderID_fk",orderID,orders)(_.id)

    def productID = column[Long]("productID")

    def productID_fk = foreignKey("productID_fk",productID,products)(_.id)

    def quantity = column[Int]("quantity")

    def * = (orderID, productID, quantity) <> ((OrderedProduct.apply _).tupled, OrderedProduct.unapply)


  }

  private val orders = TableQuery[OrderTable]
  private val products = TableQuery[ProductTable]
  private val orderProducts = TableQuery[OrderedProductTable]


  def create(orderID: Long, productID: Long, quantity: Int): Future[Cart] = db.run {
    orderProducts += OrderedProduct(orderID,productID,quantity)
  }

  def list(): Future[Seq[OrderedProduct]] = db.run{
    orderProducts.result
  }
}