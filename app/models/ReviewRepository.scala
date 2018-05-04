package models

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ReviewRepository @Inject() (dbConfigProvider: DatabaseConfigProvider, productRepository: ProductRepository, userRepository: UserRepository)(implicit ec: ExecutionContext) {
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  private class ReviewTable(tag: Tag) extends Table[Review](tag,"review"){

    def userID = column[Long]("userID")

    def userID_fk = foreignKey("userID_fk",userID,users)(_.id)

    def productID = column[Long]("productID")

    def productID_fk = foreignKey("productID_fk",productID,products)(_.id)

    def grade = column[Short]("grade")

    def review = column[String]("review")

    def * = (userID, productID, grade,review) <> ((Review.apply _).tupled, Review.unapply)


  }

  private val users = TableQuery[UserTable]
  private val products = TableQuery[ProductTable]
  private val review = TableQuery[ReviewTable]

  def create(userID: Long, productID: Long, grade: Short, review: String): Future[Review] = db.run {
    review += Review(userID,productID,grade,review)
  }

  def list(): Future[Seq[Review]] = db.run{
    review.result
  }
}