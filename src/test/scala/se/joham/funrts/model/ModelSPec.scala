package se.joham.funrts.model

import com.twitter.io.Charsets
import org.scalatest._
import org.scalatest.mock._
import se.joham.funrts.math.Vec2FixPt
import se.joham.funrts.model.components.{Acting, BaseInfo, MovementLimits, Positionable}
import se.gigurra.scalego.Entity
import se.joham.funrts.util.{JSON, Zip}
import se.gigurra.scalego.TestAPI._
import se.joham.funrts.model.TestAPI._

class ModelSpec
  extends WordSpec
  with MockitoSugar
  with Matchers
  with OneInstancePerTest
  with BeforeAndAfterAll
  with BeforeAndAfterEach {

  val levelGen = GroundLevelGenerator
  val store = DefaultCEStore()
  val level = Level(10, 10, seed = "test", levelGen, store)
  import level._
  implicit val _aSys = store.system[Acting]
  implicit val _pSys = store.system[Positionable]
  implicit val _bSys = store.system[BaseInfo]
  implicit val _mSys = store.system[MovementLimits]
  implicit val _ctx = Context(store, terrain)

  def makeBuilding(id: Entity.Id, name: String, pos: Pos.Type, team: Team, size: Size.Type = Vec2FixPt(2,2)): Entity = {
    Entity.builder(id) + Positionable(pos, size) + BaseInfo(name, team)
  }

  def makeCharacter(id: Entity.Id, name: String, pos: Pos.Type, team: Team): Entity = {
    Entity.builder(id) + Positionable(pos) + MovementLimits(2L) + BaseInfo(name, team)
  }

  "Model" should {

    "Level" should {
      "be able to add and get entities of different types from a Level" in {

        store.allEntities.size shouldBe 0

        val b = makeBuilding(id = "b", name = "farm", pos = Vec2FixPt(1,1), team = Team.blue)
        val c = makeCharacter(id = "c", name = "footman", pos = Vec2FixPt(0,0), team = Team.blue)

        store.allEntities.size shouldBe 2

        b[Positionable] shouldBe Positionable(1,1, size = Vec2FixPt(2,2))
        c[MovementLimits] shouldBe MovementLimits(2)
      }

      "Be written to JSON and read back" in {

        val a: Entity = Entity.builder("id1")
        val b: Entity = Entity.builder("id2") + Positionable(1,2)
        val c: Entity = Entity.builder("id3") + Positionable(2,2) + Acting(MovingTo(2,2))
        val d: Entity = Entity.builder("id4") + Positionable(3,2) + Acting(MovingTo(2,2)) + MovementLimits(1L)
        val e: Entity = Entity.builder("id5") + Positionable(4,2) + Acting(MovingTo(2,2)) + MovementLimits(1L) + BaseInfo("lala", Team.blue)

        val json = JSON.write(level, pretty = false)
        println(json)

        val levelBack1 = JSON.read[Level](json)
        val levelBack2 = JSON.read[Level](json)
        val levelBack3 = JSON.read[Level](json)
        val levelBack4 = JSON.read[Level](json)

        println(JSON.write(levelBack1, pretty = false))

        levelBack1 shouldBe level
        levelBack2 shouldBe level
        levelBack3 shouldBe level
        levelBack4 shouldBe level

        val levelBytesUncompressed = json.getBytes(Charsets.Utf8)
        val levelBytesCompressed = Zip.compress(levelBytesUncompressed)

        println(s"level-json size(uncompressed): ${levelBytesUncompressed.length}")
        println(s"level-json size(compressed): ${levelBytesCompressed.length}")

        new String(Zip.decompress(levelBytesCompressed), Charsets.Utf8) shouldBe json
      }

      "Be copyable" in {

        val l1 = level.duplicate

        val a: Entity = Entity.builder("id1") + Positionable(1,2)
        val b: Entity = Entity.builder("id2") + Positionable(2,2) + Acting(MovingTo(2,2))

        val l2 = level.duplicate

        val c: Entity = Entity.builder("id3") + Positionable(3,2) + Acting(MovingTo(2,2)) + MovementLimits(1L)
        val d: Entity = Entity.builder("id4") + Positionable(4,2) + Acting(MovingTo(2,2)) + MovementLimits(1L) + BaseInfo("lala", Team.blue)

        val l3 = level.duplicate

        level.entityStore.allEntities.size shouldBe 4
        l1.entityStore.allEntities.size shouldBe 0
        l2.entityStore.allEntities.size shouldBe 2
        l3.entityStore.allEntities.size shouldBe 4

      }

      "Have some unit arithmetics for testing" in {

        val l1 = level.duplicate

        val a: Entity = Entity.builder("id1") + Positionable(1,2)
        val b: Entity = Entity.builder("id2") + Positionable(2,2) + Acting(MovingTo(2,2))

        val l2 = level.duplicate

        val c: Entity = Entity.builder("id3") + Positionable(3,2) + Acting(MovingTo(2,2)) + MovementLimits(1L)
        val d: Entity = Entity.builder("id4") + Positionable(4,2) + Acting(MovingTo(2,2)) + MovementLimits(1L) + BaseInfo("lala", Team.blue)

        val l3 = level.duplicate

        (level.duplicate - c - d) shouldBe l2
        (level.duplicate - c - d - a - b) shouldBe l1
        level shouldBe l3


      }
    }


  }

}
