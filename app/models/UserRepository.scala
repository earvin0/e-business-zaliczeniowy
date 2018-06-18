package models

import java.util.UUID

import javax.inject.{ Inject, Singleton }
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ Future, ExecutionContext }

@Singleton
class UserRepository @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  class UserTable(tag: Tag) extends Table[User2](tag, "user") {

    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def name = column[String]("name")

    def * = (id, name) <> ((User2.apply _).tupled, User2.unapply)

  }

  val users = TableQuery[UserTable]

  def create(name: String): Future[User2] = db.run {
    (users.map(p => (p.name))
      returning users.map(_.id)
      into { case ((name), id) => User2(id, name) }) += (name)
  }

  def list(): Future[Seq[User2]] = db.run {
    users.result
  }
}