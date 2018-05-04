package models

import play.api.libs.json._

case class Keyword(word: String, occurrences : Int )

object Keyword {
  implicit val keywordFormat = Json.format[Keyword]
}