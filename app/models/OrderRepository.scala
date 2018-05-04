package models

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class OrderRepository @Inject() (dbConfigProvider: DatabaseConfigProvider, userRepository: UserRepository)(implicit ec: ExecutionContext) {
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  private class OrderTable(tag: Tag) extends Table[Order](tag,"order"){

    def id = column[Long]("id",O.PrimaryKey,O.AutoInc)

    def userID = column[Long]("userID")

    def userID_fk = foreignKey("userID_fk",userID,users)(_.id)

    def paid = column[Boolean]("paid",O.Default(0))

    def * = (id, userID, paid) <> ((Order.apply _).tupled, Order.unapply)


  }

  private val users = TableQuery[UserTable]
  private val orders = TableQuery[Order]

  def create(userID: Long, paid: Boolean): Future[Order] = db.run {
    ( orders.map( p => (p.userID, p.paid))
      returning orders.map(_.id)
      into { case ((userID,paid),id) => Order(id,userID,paid)}
    ) += Order(id, userID,paid)
  }

  def list(): Future[Seq[Order]] = db.run{
    orders.result
  }
}