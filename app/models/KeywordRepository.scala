package models

import scala.concurrent.{ExecutionContext, Future}


@Singleton
class KeywordRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  private class KeywordTable(tag: Tag) extends Table[Keyword](tag,"keyword"){

    def word = column[String]("word",O.PrimaryKey)

    def occurrences = column[Int]("occurrences")

    def * = (word, occurrences) <> ((Keyword.apply _).tupled, Keyword.unapply)


  }

  private val keyword = TableQuery[Keyword]

  def create(word: String, occurrences: Int): Future[Keyword] = db.run {
    keyword += Keyword(word, occurrences)
  }

  def list(): Future[Seq[Keyword]] = db.run{
    keyword.result
  }
}