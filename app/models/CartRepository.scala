package models

import javax.inject.{ Inject, Singleton }
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ Future, ExecutionContext }

@Singleton
class CartRepository @Inject() (dbConfigProvider: DatabaseConfigProvider, productRepository: ProductRepository, userRepository: UserRepository)(implicit ec: ExecutionContext) {
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  import userRepository.UserTable
  import productRepository.ProductTable

  private class CartTable(tag: Tag) extends Table[Cart](tag, "cart") {

    def userID = column[Long]("userID")

    def userID_fk = foreignKey("userID_fk", userID, users)(_.id)

    def productID = column[Long]("productID")

    def productID_fk = foreignKey("productID_fk", productID, products)(_.id)

    def quantity = column[Int]("quantity")

    def * = (userID, productID, quantity) <> ((Cart.apply _).tupled, Cart.unapply)

  }

  import userRepository.UserTable
  import productRepository.ProductTable

  private val users = TableQuery[UserTable]
  private val products = TableQuery[ProductTable]
  private val cart = TableQuery[CartTable]

  def create(userID: Long, productID: Long, quantity: Int): Future[Int] = db.run {
    cart += Cart(userID, productID, quantity)
  }

  def list(): Future[Seq[Cart]] = db.run {
    cart.result
  }
}