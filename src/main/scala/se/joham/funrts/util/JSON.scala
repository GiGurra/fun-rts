package se.joham.funrts.util

import org.json4s.JsonAST.JString
import org.json4s.{CustomSerializer, Extraction, ShortTypeHints}
import se.joham.funrts.model._
import org.json4s.jackson.JsonMethods.{compact, parse}
import org.json4s.jackson.JsonMethods.{pretty => prty}
import Extraction.{decompose, extract}

import scala.collection.mutable

/**
  * Created by johan on 2016-06-12.
  */
object JSON {

  implicit def fmts: org.json4s.Formats = defaultFormats

  def read[T : Manifest](input: String): T = {
    extract[T](parse(input))
  }

  def write[T : Manifest](input: T, pretty: Boolean = false): String = {
    if (pretty) prty(decompose(input))
    else compact(decompose(input))
  }

  //////////////////////////////////////////////////////////////////////////////////////

  object CESToreSerializer extends CustomSerializer[CEStore](_ => ({
    case json => CEStore(extract[Map[CESystemId, CESystem[Component]]](json)) },{
    case store: CEStore => decompose(store.systems)
  }))

  object CESystemSerializer extends CustomSerializer[CESystem[Component]](_ => ({
    case json => extract[CeSystemSerialized](json).toCeSystem },{
    case system: CESystem[_] => decompose(CeSystemSerialized(system))
  }))

  object EntitySerializer extends CustomSerializer[Entity](_ => ({
    case id: JString => Entity(id.s) },{
    case x: Entity => JString(x.id)
  }))

  object MeshSerializer extends CustomSerializer[Mesh](_ => ({
    case json => extract[MeshSerialized](json).toMesh },{
    case mesh: Mesh => decompose(MeshSerialized(mesh))
  }))

  case class MeshSerialized(nx: Int, ny: Int, base64Tiles: String) {
    def toMesh: Mesh = new Mesh(nx, ny, Base64.decodeBinary(base64Tiles))
  }
  object MeshSerialized {
    def apply(mesh: Mesh): MeshSerialized = new MeshSerialized(mesh.nx, mesh.ny, Base64.encodeString(mesh.tiles))
  }

  case class CeSystemSerialized(clsName: String, entries: Map[String, Component]) {
    def toCeSystem: CESystem[Component] = {
      val cls = Class.forName(clsName)
      val arg = new mutable.HashMap[EntityId, Component] ++ entries.map(p => Entity(p._1) -> p._2)
      val ctor = cls.getConstructor(classOf[mutable.Map[_,_]])
      ctor.newInstance(arg).asInstanceOf[CESystem[Component]]
    }
  }
  object CeSystemSerialized {
    def apply[T <: Component](system: CESystem[T]): CeSystemSerialized = {
      CeSystemSerialized(system.getClass.getName, system.entries.map(p => p._1.id -> p._2).toMap)
    }
  }

  lazy val defaultFormats =
    org.json4s.DefaultFormats +
      CESToreSerializer +
      CESystemSerializer +
      EntitySerializer +
      MeshSerializer +
      ShortTypeHints(Component.classes) +
      ShortTypeHints(Action.classes) +
      ShortTypeHints(Team.classes)
}
