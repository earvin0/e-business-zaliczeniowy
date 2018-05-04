package models

import javax.inject.{ Inject, Singleton }
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ Future, ExecutionContext }


@Singleton
class KeywordRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  private class KeywordTable(tag: Tag) extends Table[Keyword](tag,"keyword"){

    def word = column[String]("word",O.PrimaryKey)

    def occurrences = column[Int]("occurrences")

    def * = (word, occurrences) <> ((Keyword.apply _).tupled, Keyword.unapply)


  }

  private val keyword = TableQuery[KeywordTable]

  def create(word: String, occurrences: Int): Future[Int] = db.run {
    keyword += Keyword(word, occurrences)
  }

  def list(): Future[Seq[Keyword]] = db.run{
    keyword.result
  }
}