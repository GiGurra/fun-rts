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

  object CESystemSerializer extends CustomSerializer[CESystem[Component]](_ => ({
    case json => extract[CeSystemSerialized](json).toCeSystem },{
    case system: CESystem[_] => decompose(CeSystemSerialized(system))
  }))

  object MeshSerializer extends CustomSerializer[Mesh](_ => ({
    case json => extract[MeshSerialized](json).toMesh },{
    case mesh: Mesh => decompose(new MeshSerialized(mesh))
  }))

  case class MeshSerialized(nx: Int, ny: Int, base64Tiles: String) {
    def this(mesh: Mesh) = this(mesh.nx, mesh.ny, Base64.encodeString(mesh.tiles))
    def toMesh: Mesh = new Mesh(nx, ny, Base64.decodeBinary(base64Tiles))
  }
  case class CeSystemSerialized(clsName: String, entries: Map[EntityId, Component]) {
    def toCeSystem: CESystem[Component] = {
      CeSystemSerialized.getCtor(clsName)(new mutable.HashMap[EntityId, Component] ++ entries.map(p => p._1 -> p._2))
    }
  }
  object CeSystemSerialized {
    private val ctors = new scala.collection.concurrent.TrieMap[String, mutable.Map[_,_] => CESystem[Component]]
    private def getCtor(clsName: String): mutable.Map[_,_] => CESystem[Component] = {
      ctors.getOrElseUpdate(clsName, {
        val cls = Class.forName(clsName)
        val ctor = cls.getConstructor(classOf[mutable.Map[_,_]])
        input => ctor.newInstance(input).asInstanceOf[CESystem[Component]]
      })
    }

    def apply[T <: Component](system: CESystem[T]): CeSystemSerialized = {
      CeSystemSerialized(system.getClass.getName, system.toMap)
    }
  }

  lazy val defaultFormats =
    org.json4s.DefaultFormats +
      CESystemSerializer +
      MeshSerializer +
      ShortTypeHints(Component.classes) +
      ShortTypeHints(Action.classes) +
      ShortTypeHints(Team.classes)
}
