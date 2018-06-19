package controllers

import javax.inject._
import models._
import play.api.data.Form
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import play.api.i18n._
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.{ ExecutionContext, Future }
import scala.util.{ Failure, Success }

class ReviewController @Inject() (reviewRepo: ReviewRepository, userRepo: UserRepository, productRepo: ProductRepository, cc: MessagesControllerComponents)(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {

  val reviewForm: Form[CreateReviewForm] = Form {
    mapping(
      "userID" -> number,
      "productID" -> number,
      "grade" -> number,
      "review" -> nonEmptyText)(CreateReviewForm.apply)(CreateReviewForm.unapply)
  }

  val getReviewForm: Form[GetReviewForm] = Form {
    mapping(
      "productID" -> number,
      )(GetReviewForm.apply)(GetReviewForm.unapply)
  }

  def addReview(): Action[AnyContent] = Action.async { implicit request =>

    var form = reviewForm.bindFromRequest.get
    reviewRepo.create(form.userID, form.productID, form.grade, form.review).map(review =>
      Ok(form.review))
  }

  def getProductReviews() = Action.async { implicit request =>

    var form = getReviewForm.bindFromRequest.get
    reviewRepo.listByProductID(form.productID).map(reviews =>
      Ok(Json.toJson(reviews))
    )
  }

  def getReviews = Action.async { implicit request =>
    reviewRepo.list().map { review =>
      Ok(Json.toJson(review))
    }
  }
}

case class CreateReviewForm(userID: Int, productID: Int, grade: Int, review: String)
case class GetReviewForm( productID: Int)