package controllers

import javax.inject._
import models._
import play.api.data.Form
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import play.api.i18n._
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

class ReviewController @Inject()(reviewRepo: ReviewRepository,userRepo: UserRepository,productRepo: ProductRepository, cc: MessagesControllerComponents)(implicit ec: ExecutionContext) extends MessagesAbstractController(cc){

  val reviewForm: Form[CreateReviewForm] = Form {
    mapping(
      "userID" -> number,
    "productID" -> number,
    "grade" -> number,
    "review" -> nonEmptyText
    )(CreateReviewForm.apply)(CreateReviewForm.unapply)
  }

  def index = Action.async { implicit request =>
    val products = productRepo.list()
    products.map(prod => Ok(views.html.review(reviewForm,prod)))

  }

  def addReview = Action { implicit request =>

    reviewForm.bindFromRequest.fold(
      formWithErrors => {
        Redirect(routes.ReviewController.index).flashing("success" -> "Failed to add user!")
      },

      review => {
        reviewRepo.create(review.userID,review.productID,review.grade,review.review)
        Redirect(routes.ReviewController.index).flashing("success" -> "User saved!")
      }
    )

  }
}



case class CreateReviewForm(userID: Int,productID: Int,grade: Int,review: String)