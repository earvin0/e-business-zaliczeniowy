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

class CartController @Inject()(cartRepo: CartRepository,productsRepo: ProductRepository, userRepo: UserRepository,
                                  cc: MessagesControllerComponents
                                 )(implicit ec: ExecutionContext)
  extends MessagesAbstractController(cc) {

  /**
    * The mapping for the person form.
    */

 /* def addToCart = Action.async { implicit request =>
    // Bind the form first, then fold the result, passing a function to handle errors, and a function to handle succes.
  }*/


  /**
    * A REST endpoint that gets all the people as JSON.
    */
  def getCart = Action.async { implicit request =>
    cartRepo.list().map { products =>
      Ok(Json.toJson(products))
    }
  }

}

/**
  * The create person form.
  *
  * Generally for forms, you should define separate objects to your models, since forms very often need to present data
  * in a different way to your models.  In this case, it doesn't make sense to have an id parameter in the form, since
  * that is generated once it's created.
  */
