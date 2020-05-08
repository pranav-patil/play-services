package controllers

import javax.inject._
import models._
import play.api.Logger
import play.api.libs.json.{JsError, Json}
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class UserController @Inject()(repo: UserRepository, cc: ControllerComponents)(implicit ec: ExecutionContext)
  extends AbstractController(cc) {

  private val logger = Logger(this.getClass)

  def getUsers = Action.async { implicit request =>
    repo.list().map { users =>
      Ok(Json.toJson(users))
    }
  }

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
    repo.get(id) map { users =>
      Ok(Json.toJson(users))
    }
  }

  def deleteUser(id : Long) = Action.async { implicit request =>
    repo.delete(id) map { res =>
      Ok(Json.obj("status" -> "success")).as("application/json")
    }
  }
}
