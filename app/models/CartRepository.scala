package models

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class CartRepository @Inject() (dbConfigProvider: DatabaseConfigProvider, productRepository: ProductRepository, userRepository: UserRepository)(implicit ec: ExecutionContext) {
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  private class CartTable(tag: Tag) extends Table[Cart](tag,"cart"){

    def userID = column[Long]("userID")

    def userID_fk = foreignKey("userID_fk",userID,users)(_.id)

    def productID = column[Long]("productID")

    def productID_fk = foreignKey("productID_fk",productID,products)(_.id)

    def quantity = column[Int]("quantity")

    def * = (userID, productID, quantity) <> ((Cart.apply _).tupled, Cart.unapply)


  }

  private val users = TableQuery[UserTable]
  private val products = TableQuery[ProductTable]
  private val cart = TableQuery[CartTable]

  def create(userID: Long, productID: Long, quantity: Int): Future[Cart] = db.run {
    cart += Cart(userID,productID,quantity)
  }

  def list(): Future[Seq[Cart]] = db.run{
    cart.result
  }
}