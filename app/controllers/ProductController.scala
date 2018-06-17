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

class ProductController @Inject()(productsRepo: ProductRepository, categoryRepo: CategoryRepository,
                                  cc: MessagesControllerComponents
                                )(implicit ec: ExecutionContext)
  extends MessagesAbstractController(cc) {

  /**
   * The mapping for the person form.
   */
  val productForm: Form[CreateProductForm] = Form {
    mapping(
      "name" -> nonEmptyText,
      "description" -> nonEmptyText,
      "category" -> number,
    )(CreateProductForm.apply)(CreateProductForm.unapply)
  }

  /**
   * The index action.
   */

      /*
      .onComplete{
      case Success(categories) => Ok(views.html.index(productForm,categories))
      case Failure(t) => print("")
    }
  }*/

  /**
   * The add person action.
   *
   * This is asynchronous, since we're invoking the asynchronous methods on PersonRepository.
   */
/*
  def addProduct = Action.async { implicit request =>
    Ok(views.html.addproduct())
  }
*/

  val searchForm: Form[CreateSearchForm] = Form {
  mapping(
    "keyword" -> nonEmptyText
  )(CreateSearchForm.apply)(CreateSearchForm.unapply)
}

  def addProduct(): Action[AnyContent] = Action.async { implicit request =>

    val prod = productForm.bindFromRequest.get
    productsRepo.create(prod.name, prod.description, prod.category).map( createdCategory =>
      Ok(prod.name))

  }


  /**
   * A REST endpoint that gets all the people as JSON.
   */
  def getProducts = Action.async { implicit request =>
    productsRepo.list().map { products =>
      Ok(Json.toJson(products))
    }
  }

  def getByKeyword() = Action.async { implicit request =>

    val keywordForm = searchForm.bindFromRequest.get
    println(keywordForm.keyword)
    productsRepo.listByKeyword(keywordForm.keyword).map { product =>
      Ok(Json.toJson(product))
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
case class CreateProductForm(name: String, description: String, category: Int)
case class CreateSearchForm(keyword: String)