package models

import play.api.libs.json._

case class User(id: Long, firstName: String, lastName: String, age: Int, email: String)


object User {
  implicit val userFormat = Json.format[User]
}
