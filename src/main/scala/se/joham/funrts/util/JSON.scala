package se.joham.funrts.util

import org.json4s.{CustomSerializer, Extraction, ShortTypeHints}
import se.joham.funrts.model._
import Extraction.{decompose, extract}
import org.json4s.JsonAST.JValue
import se.gigurra.scalego.serialization.KnownSubTypes
import se.gigurra.scalego.serialization.json.JsonSerializer
import se.joham.funrts.model.FunRtsECS.{ECS, IdTypes}
import org.json4s.jackson.JsonMethods.{compact, parse}
import org.json4s.jackson.JsonMethods.{pretty => prty}

/**
  * Created by johan on 2016-06-12.
  */
object JSON {

  def writeAst(ecs: ECS): JValue = ecsSerializer.SerializableOps(ecs).toJsonAst
  def    write(ecs: ECS, pretty: Boolean): String = ecsSerializer.SerializableOps(ecs).toJson(pretty)
  def writeAst(terrain: Terrain): JValue = decompose(terrain)
  def    write(terrain: Terrain, pretty: Boolean): String = if (pretty) prty(writeAst(terrain)) else compact(writeAst(terrain))

  def     readEcsAst(ecs: ECS, json: JValue): Unit = ecsSerializer.SerializableOps(ecs).appendJsonAst(json)
  def        readEcs(ecs: ECS, json: String): Unit = ecsSerializer.SerializableOps(ecs).appendJson(json)
  def readTerrainAst(json: JValue): Terrain = extract[Terrain](json)
  def    readTerrain(json: String): Terrain = readTerrainAst(parse(json))


  //////////////////////////////////////////////////////////////////////////////////////

  object TerrainSerializer extends CustomSerializer[Terrain](_ => ({
    case json => extract[TerrainSerializable](json).toTerrain },{
    case terrain: Terrain => decompose(new TerrainSerializable(terrain))
  }))

  case class TerrainSerializable(nx: Int, ny: Int, base64Tiles: String) {
    def this(terrain: Terrain) = this(terrain.nx, terrain.ny, Base64.encodeString(terrain.tiles))
    def toTerrain: Terrain = Terrain(nx, ny, Base64.decodeBinary(base64Tiles))
  }

  implicit lazy val jsonFormats = org.json4s.DefaultFormats + TerrainSerializer + ShortTypeHints(Action.classes)
  val ecsSerializer = new JsonSerializer[IdTypes](
    knownSubtypes = KnownSubTypes.fromShortClassName(types = Action.classes:_*),
    jsonFormats = jsonFormats
  )

}
