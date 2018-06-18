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

class UserController @Inject() (userRepo: UserRepository, cc: MessagesControllerComponents)(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {

  val userForm: Form[CreateUserForm] = Form {
    mapping(
      "name" -> nonEmptyText)(CreateUserForm.apply)(CreateUserForm.unapply)
  }

  /*def index = Action { implicit request =>
    Ok(views.html.user(userForm))
  }*/

  /*def addUser = Action { implicit request =>

    userForm.bindFromRequest.fold(
      formWithErrors => {
        Redirect(routes.UserController.index).flashing("success" -> "Failed to add user!")
      },

      user => {
        val userID = userRepo.create(user.name)
        Redirect(routes.UserController.index).flashing("success" -> "User saved!")
      })

  }*/

  def getUsers = Action.async { implicit request =>
    userRepo.list().map { users =>
      Ok(Json.toJson(users))
    }
  }

}

case class CreateUserForm(name: String)