package controllers

import javax.inject._
import play.api.libs.json.{JsError, JsValue, Json}
import play.api.mvc._
import play.api.Logger
import javax.inject._
import models._
import play.api.data.validation.Constraints._
import play.api.i18n._
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class UserController @Inject()(repo: UserRepository, cc: ControllerComponents)(implicit ec: ExecutionContext)
  extends AbstractController(cc) {

  private val logger = Logger(this.getClass)

  def getUsers = Action.async { implicit request =>
    logger.trace("index: ")
    repo.list().map { people =>
      Ok(Json.toJson(people))
    }
  }

//  def addUser:Future[String] = {
//    // Bind the form first, then fold the result, passing a function to handle errors, and a function to handle succes.
//    personForm.bindFromRequest.fold(
//      // The error function. We return the index page with the error form, which will render the errors.
//      // We also wrap the result in a successful future, since this action is synchronous, but we're required to return
//      // a future because the user creation function returns a future.
//      errorForm => {
//        Future.successful(Ok(views.html.index(errorForm)))
//      },
//      // There were no errors in the from, so create the user.
//      user => {
//        repo.create(user.name, user.age).map { _ =>
//
//          // If successful, we simply redirect to the index page.
//          Redirect(routes.HomeController.index).flashing("success" -> "user.created")
//        }
//      }
//    )
//  }

  def addUser = Action.async(parse.json) { request =>
    request.body.validate[User].map { user =>
      repo.create(user.firstName, user.lastName, user.age, user.email).map {
        result => Created(Json.obj("status" -> "success")).as("application/json")
      }.recoverWith {
        case e => Future { InternalServerError("ERROR: " + e )}
      }
    }.recoverTotal {
      e => Future { BadRequest( Json.obj("status" -> "fail", "data" -> JsError.toJson(e)) ) }
    }
  }

  def getUser(id: Long) = Action.async { implicit request =>
    repo.get(id) map { people =>
      Ok(Json.toJson(people))
    }
  }

  def deleteUser(id : Long) = Action.async { implicit request =>
    repo.delete(id) map { res =>
      Redirect(routes.HomeController.index).flashing("success" -> "user.deleted")
    }
  }
}
