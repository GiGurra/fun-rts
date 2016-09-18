package se.joham.funrts.model

import com.twitter.io.Charsets
import org.scalatest._
import org.scalatest.mock._
import se.joham.funrts.math.Vec2FixPt
import se.joham.funrts.model.FunRtsECS._
import se.joham.funrts.model.components.{Acting, BaseInfo, MovementLimits, Positionable}
import se.joham.funrts.util.{JSON, Zip}

class ModelSpec
  extends WordSpec
  with MockitoSugar
  with Matchers
  with OneInstancePerTest
  with BeforeAndAfterAll
  with BeforeAndAfterEach {

  val levelGen = GroundLevelGenerator
  val ecs = FunRtsECS()
  val level = Level(10, 10, seed = "test", levelGen, ecs)
  implicit val _aSys = ecs.system[Acting]
  implicit val _pSys = ecs.system[Positionable]
  implicit val _bSys = ecs.system[BaseInfo]
  implicit val _mSys = ecs.system[MovementLimits]

  def allEntities: Set[Entity] = {
    ecs.systems.values.flatMap(_.keys).map(Entity.apply).toSet
  }

  def makeBuilding(id: EntityId, name: String, pos: Pos.Type, team: Team, size: Size.Type = Vec2FixPt(2,2)): Entity = {
    Entity.Builder + Positionable(pos, size) + BaseInfo(name, team) build(entityId = id)
  }

  def makeCharacter(id: EntityId, name: String, pos: Pos.Type, team: Team): Entity = {
    Entity.Builder + Positionable(pos) + MovementLimits(2L) + BaseInfo(name, team) build(entityId = id)
  }

  "Model" should {

    "Level" should {
      "be able to add and get entities of different types from a Level" in {

        allEntities.size shouldBe 0

        val b = makeBuilding(id = "b", name = "farm", pos = Vec2FixPt(1,1), team = Team.blue)
        val c = makeCharacter(id = "c", name = "footman", pos = Vec2FixPt(0,0), team = Team.blue)

        allEntities.size shouldBe 2

        b[Positionable] shouldBe Positionable(1,1, size = Vec2FixPt(2,2))
        c[MovementLimits] shouldBe MovementLimits(2)
      }

      "Be written to JSON and read back" in {

        val a: Entity = Entity.Builder + Positionable(1,2) build "id1"
        val b: Entity = Entity.Builder + Positionable(2,2) + Acting(MovingTo(2,2)) build "id2"
        val c: Entity = Entity.Builder + Positionable(3,2) + Acting(MovingTo(2,2)) + MovementLimits(1L) build "id3"
        val d: Entity = Entity.Builder + Positionable(4,2) + Acting(MovingTo(2,2)) + MovementLimits(1L) + BaseInfo("lala", Team.blue) build "id4"

        val terrainJson = JSON.write(level.terrain, pretty = false)
        val terrainBack = JSON.readTerrain(terrainJson)
        terrainBack shouldBe level.terrain

        val ecsJson = JSON.write(ecs, pretty = false)

        val ecsBack = FunRtsECS()
        JSON.readEcs(ecsBack, ecsJson)

        ecs shouldBe ecsBack

        val levelBytesUncompressed = (terrainJson + ecsJson).getBytes(Charsets.Utf8)
        val levelBytesCompressed = Zip.compress(levelBytesUncompressed)

        println(s"level-json size(uncompressed): ${levelBytesUncompressed.length}")
        println(s"level-json size(compressed): ${levelBytesCompressed.length}")

        new String(Zip.decompress(levelBytesCompressed), Charsets.Utf8) shouldBe (terrainJson + ecsJson)
      }
    }
  }
}
