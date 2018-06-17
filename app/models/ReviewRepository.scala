package models

import javax.inject.{ Inject, Singleton }
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ Future, ExecutionContext }

@Singleton
class ReviewRepository @Inject() (dbConfigProvider: DatabaseConfigProvider, productRepository: ProductRepository, userRepository: UserRepository)(implicit ec: ExecutionContext) {
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  private class ReviewTable(tag: Tag) extends Table[Review](tag, "review") {

    def userID = column[Long]("userID")

    def userID_fk = foreignKey("userID_fk", userID, users)(_.id)

    def productID = column[Long]("productID")

    def productID_fk = foreignKey("productID_fk", productID, products)(_.id)

    def grade = column[Int]("grade")

    def review = column[String]("review")

    def * = (userID, productID, grade, review) <> ((Review.apply _).tupled, Review.unapply)

  }

  import userRepository.UserTable
  import productRepository.ProductTable

  private val users = TableQuery[UserTable]
  private val products = TableQuery[ProductTable]
  private val reviews = TableQuery[ReviewTable]

  def create(userID: Long, productID: Long, grade: Int, review: String): Future[Int] = db.run {
    reviews += Review(userID, productID, grade, review)
  }

  def list(): Future[Seq[Review]] = db.run {
    reviews.result
  }
}