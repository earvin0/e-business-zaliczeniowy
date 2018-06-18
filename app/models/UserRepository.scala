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

    def name = column[Option[String]]("name")

    def email = column[Option[String]]("email")

    def token = column[String]("token")

    def * = (id, name, email, token) <> ((User2.apply _).tupled, User2.unapply)

  }

  val users = TableQuery[UserTable]

  def create(name: Option[String], email: Option[String], token: String): Future[User2] = db.run {
    (users.map(p => (p.name, p.email, p.token))
      returning users.map(_.id)
      into { case ((name, email, token), id) => User2(id, name, email, token) }) += (name, email, token)
  }

  def getUserByEmail(email: Option[String]): Future[Option[User2]] = db.run {
    users.filter(_.email === email).result.headOption
  }

  def list(): Future[Seq[User2]] = db.run {
    users.result
  }
}