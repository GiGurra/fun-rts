package se.joham.funrts.util

import org.json4s.JsonAST.JString
import org.json4s.{CustomSerializer, Extraction, ShortTypeHints}
import se.joham.funrts.model.{Entity => _, _}
import se.joham.funrts.model.v2._
import org.json4s.jackson.JsonMethods

import scala.collection.mutable

/**
  * Created by johan on 2016-06-12.
  */
object JSON {

  def read[T : Manifest](input: String): T = {
    val parsed = JsonMethods.parse(input)
    Extraction.extract[T](parsed)
  }

  def write[T : Manifest](input: T, pretty: Boolean = false): String = {
    val jval = Extraction.decompose(input)
    if (pretty) JsonMethods.pretty(jval)
    else JsonMethods.compact(jval)
  }

  //////////////////////////////////////////////////////////////////////////////////////

  case class CESystemSerialized(entries: Map[Id, Component]) {
    def system: CESystem[Component] = new CESystem[Component](new mutable.HashMap[Entity, Component] ++ entries.map(p => Entity(p._1) -> p._2))
  }

  case class CEStoreSerialized(systems: Map[Id, CESystemSerialized]) {
    def store: CEStore = CEStore(new mutable.HashMap[Id, CESystem[Component]] ++ systems.mapValues(_.system))
  }

  def sys2serializable(system: CESystem[_]) = CESystemSerialized(system.asInstanceOf[CESystem[Component]].entries.toMap.map(p => p._1.id -> p._2))
  def store2serializable(store: CEStore) = CEStoreSerialized(store.systems.toMap.mapValues(sys2serializable))

  object EntitySerializer extends CustomSerializer[Entity](formats => ({ case id: JString => Entity(id.s) },{ case x: Entity => JString(x.id) } ))

  object CESToreSerializer extends CustomSerializer[CEStore](formats => ({
    case json => Extraction.extract[CEStoreSerialized](json)(formats, implicitly[Manifest[CEStoreSerialized]]).store },{
    case store: CEStore => Extraction.decompose(store2serializable(store))(formats)
  }))

  object CESystemSerializer extends CustomSerializer[CESystem[Component]](formats => ({
    case json => Extraction.extract[CESystemSerialized](json)(formats, implicitly[Manifest[CESystemSerialized]]).system },{
    case system: CESystem[_] => Extraction.decompose(sys2serializable(system))(formats)
  }))

  val jsonTypeHints =
    ShortTypeHints(Component.classes) +
    ShortTypeHints(Action.classes) +
    ShortTypeHints(Team.classes)

  implicit val formats = org.json4s.DefaultFormats + EntitySerializer + CESToreSerializer + CESystemSerializer + jsonTypeHints

}
