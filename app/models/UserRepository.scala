package models

import java.util.UUID

import javax.inject.{ Inject, Singleton }
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ ExecutionContext, Future }

import com.mohiva.play.silhouette.api.{ Identity, LoginInfo }

@Singleton
class UserRepository @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  class UserTable(tag: Tag) extends Table[User](tag, "user") {

    def userID = column[UUID]("id", O.PrimaryKey, O.AutoInc)
    def loginInfo = column[LoginInfo]("loginInfo")
    def firstName = column[String]("firstName")
    def lastName = column[String]("lastName")
    def fullName = column[String]("fullName")
    def email = column[String]("email")
    def avatarURL = column[String]("avatarURL")
    def activated = column[Boolean]("activated")

    def * = (userID, loginInfo, firstName, lastName, fullName, email, avatarURL, activated) <> ((User.apply _).tupled, User.unapply)

  }

  val users = TableQuery[UserTable]

  def create(name: String): Future[User] = db.run {
    (users.map(p => (p.name))
      returning users.map(_.id)
      into { case ((name), id) => User(id, name) }) += (name)
  }

  def list(): Future[Seq[User]] = db.run {
    users.result
  }
}