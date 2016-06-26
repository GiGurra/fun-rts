package se.joham.funrts.util

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


  object TerrainSerializer extends CustomSerializer[Terrain](_ => ({
    case json => extract[TerrainSerializable](json).toTerrain },{
    case terrain: Terrain => decompose(new TerrainSerializable(terrain))
  }))
  case class TerrainSerializable(nx: Int, ny: Int, base64Tiles: String) {
    def this(terrain: Terrain) = this(terrain.nx, terrain.ny, Base64.encodeString(terrain.tiles))
    def toTerrain: Terrain = new Terrain(nx, ny, Base64.decodeBinary(base64Tiles))
  }
  object CESystemSerializer extends CustomSerializer[CESystem[Component]](_ => ({
    case json => extract[CeSystemSerializable](json).toCeSystem },{
    case system: CESystem[_] => decompose(CeSystemSerializable(system))
  }))
  case class CeSystemSerializable(clsName: String, entries: Map[Entity.Id, Component]) {
    def toCeSystem: CESystem[Component] = {
      CeSystemSerializable.getCtor(clsName)(new mutable.HashMap[Entity.Id, Component] ++ entries.map(p => p._1 -> p._2))
    }
  }
  object CeSystemSerializable {
    private val ctors = new scala.collection.concurrent.TrieMap[String, mutable.Map[_,_] => CESystem[Component]]
    private def getCtor(clsName: String): mutable.Map[_,_] => CESystem[Component] = {
      ctors.getOrElseUpdate(clsName, {
        val cls = Class.forName(clsName)
        val ctor = cls.getConstructor(classOf[mutable.Map[_,_]])
        input => ctor.newInstance(input).asInstanceOf[CESystem[Component]]
      })
    }

    def apply[T <: Component](system: CESystem[T]): CeSystemSerializable = {
      CeSystemSerializable(system.getClass.getName, system.toMap)
    }
  }

  lazy val defaultFormats =
    org.json4s.DefaultFormats +
      TerrainSerializer +
      CESystemSerializer +
      ShortTypeHints(Component.classes) +
      ShortTypeHints(Action.classes)
}
