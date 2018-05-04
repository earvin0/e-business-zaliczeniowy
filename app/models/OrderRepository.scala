package models

import javax.inject.{ Inject, Singleton }
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ Future, ExecutionContext }

@Singleton
class OrderRepository @Inject() (dbConfigProvider: DatabaseConfigProvider, val userRepository: UserRepository)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  import userRepository.UserTable

  class OrderTable(tag: Tag) extends Table[Order](tag,"order"){

    def id = column[Long]("id",O.PrimaryKey,O.AutoInc)

    def userID = column[Long]("userID")

    def userID_fk = foreignKey("userID_fk",userID,users)(_.id)

    def paid = column[Boolean]("paid",O.Default(false))

    def * = (id, userID, paid) <> ((Order.apply _).tupled, Order.unapply)


  }

  import userRepository.UserTable

  private val users = TableQuery[UserTable]
  private val orders = TableQuery[OrderTable]

  def create(userID: Long, paid: Boolean): Future[Order] = db.run {
    ( orders.map( p => (p.userID, p.paid))
      returning orders.map(_.id)
      into { case ((userID,paid),id) => Order(id,userID,paid)}
    ) += (userID,paid)
  }

  def list(): Future[Seq[Order]] = db.run{
    orders.result
  }
}