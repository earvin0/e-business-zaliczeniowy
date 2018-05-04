
package models

case class Keyword(word: String, occurrences : Int )

object Keyword {
  implicit val keywordFormat = Json.format[Keyword]
}