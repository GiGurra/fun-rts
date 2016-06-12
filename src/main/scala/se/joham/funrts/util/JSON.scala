package se.joham.funrts.util

import org.json4s.{CustomKeySerializer, Extraction}
import se.joham.funrts.model.v2.Entity

/**
  * Created by johan on 2016-06-12.
  */
object JSON {

  object EntitySerializer extends CustomKeySerializer[Entity](_ => ({ case id => Entity(id) },{ case x: Entity => x.id } ))
  implicit val formats = org.json4s.DefaultFormats + EntitySerializer
  import org.json4s.jackson.JsonMethods

  def read[T : Manifest](input: String): T = {
    val parsed = JsonMethods.parse(input)
    Extraction.extract[T](parsed)
  }

  def write[T : Manifest](input: T, pretty: Boolean = false): String = {
    val jval = Extraction.decompose(input)
    if (pretty) JsonMethods.pretty(jval)
    else JsonMethods.compact(jval)
  }
}
