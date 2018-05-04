package models

import javax.inject.{ Inject, Singleton }
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ Future, ExecutionContext }

@Singleton
class OrderedProductRepository @Inject() (dbConfigProvider: DatabaseConfigProvider, productRepository: ProductRepository, orderRepository: OrderRepository)(implicit ec: ExecutionContext) {
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  private class OrderedProductTable(tag: Tag) extends Table[OrderedProduct](tag,"orderedProduct"){

    def orderID = column[Long]("orderID")

    def uorderID_fk = foreignKey("orderID_fk",orderID,orders)(_.id)

    def productID = column[Long]("productID")

    def productID_fk = foreignKey("productID_fk",productID,products)(_.id)

    def quantity = column[Int]("quantity")

    def * = (orderID, productID, quantity) <> ((OrderedProduct.apply _).tupled, OrderedProduct.unapply)


  }

  import orderRepository.OrderTable
  import productRepository.ProductTable

  private val orders = TableQuery[OrderTable]
  private val products = TableQuery[ProductTable]
  private val orderProducts = TableQuery[OrderedProductTable]


  def create(orderID: Long, productID: Long, quantity: Int): Future[Int] = db.run {
    orderProducts += OrderedProduct(orderID,productID,quantity)
  }

  def list(): Future[Seq[OrderedProduct]] = db.run{
    orderProducts.result
  }
}