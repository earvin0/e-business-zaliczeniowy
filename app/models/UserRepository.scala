package models

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class UserRepository @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  private val dbConfig = dbConfigProvider.get[JdbcProfile]


  private class UserTable(tag: Tag) extends Table[User](tag,"user"){

    def id = column[Long]("id", O.PrimaryKey,O.AutoInc)

    def name = column[String]("name")

    def * = (id, name) <> ((User.apply _).tupled, User.unapply)


  }

  private val users = TableQuery[UserTable]

  def create(name: String): Future[User] = db.run {
    ( users.map( p => (p.name))
      returning orders.map(_.id)
      into { case ((name),id) => Order(id,name)}
      ) += User(id,name)
  }

  def list(): Future[Seq[User]] = db.run{
    user.result
  }
}