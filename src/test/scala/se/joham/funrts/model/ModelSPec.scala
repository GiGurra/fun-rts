package se.joham.funrts.model

import java.util.UUID

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success, Try}
import org.scalatest._
import org.scalatest.mock._
import se.gigurra.franklin.Collection._
import se.joham.funrts.math.Vec2FixPt
import se.joham.funrts.model.components.{Acting, BaseInfo, MovementLimits, Positionable}
import se.joham.funrts.util.JSON

class ModelSpec
  extends WordSpec
  with MockitoSugar
  with Matchers
  with OneInstancePerTest
  with BeforeAndAfterAll
  with BeforeAndAfterEach {

  val levelGen = GroundLevelGenerator
  val level = Level(10, 10, seed = "test", levelGen)
  val store = level.entityStore

  def makeBuilding(id: EntityId, name: String, pos: Pos, team: Team, size: Size = Vec2FixPt(2,2))(implicit store: CEStore): Entity = {
    Entity.builder(id) + Positionable(pos, size) + BaseInfo(name, team)
  }

  def makeCharacter(id: EntityId, name: String, pos: Pos, team: Team)(implicit store: CEStore): Entity = {
    Entity.builder(id) + Positionable(pos) + MovementLimits(2L) + BaseInfo(name, team)
  }

  "Model" should {

    "Level" should {
      "be able to add and get entities of different types from a Level" in {

        import level._

        store.allEntities.size shouldBe 0

        val b = makeBuilding(id = "b", name = "farm", pos = Vec2FixPt(1,1), team = Team.blue)
        val c = makeCharacter(id = "c", name = "footman", pos = Vec2FixPt(0,0), team = Team.blue)

        store.allEntities.size shouldBe 2

        b[Positionable] shouldBe Positionable(1,1, size = Vec2FixPt(2,2))
        c[MovementLimits] shouldBe MovementLimits(2)
      }

      "Not overlap positions between buildings and characters" in {
        import level._

        val building = makeBuilding(id = "building", name = "farm", pos = Vec2FixPt(1,1), size = Vec2FixPt(2,2), team = Team.blue)

        // throw if conflicting position
        // a[RuntimeException] should be thrownBy makeCharacter(id = "character", name = "footman", pos = Vec2FixPt(2,2), team = Team.blue)

        level.isOccupied(Vec2FixPt(1,1)) shouldBe true
        level.isOccupied(Vec2FixPt(1,2)) shouldBe true
        level.isOccupied(Vec2FixPt(2,1)) shouldBe true
        level.isOccupied(Vec2FixPt(2,2)) shouldBe true
        level.isOccupied(Vec2FixPt(3,2)) shouldBe false

        level.isVacant(Vec2FixPt(1,1)) shouldBe false
        level.isVacant(Vec2FixPt(1,2)) shouldBe false
        level.isVacant(Vec2FixPt(2,1)) shouldBe false
        level.isVacant(Vec2FixPt(2,2)) shouldBe false
        level.isVacant(Vec2FixPt(3,2)) shouldBe true
      }


      "Be written to JSON and read back" in {

        import level._

        val a: Entity = Entity.builder("id1")
        val b: Entity = Entity.builder("id2") + Positionable(1,2)
        val c: Entity = Entity.builder("id3") + Positionable(1,2) + Acting(MovingTo(2,2))
        val d: Entity = Entity.builder("id4") + Positionable(1,2) + Acting(MovingTo(2,2)) + MovementLimits(1L)
        val e: Entity = Entity.builder("id5") + Positionable(1,2) + Acting(MovingTo(2,2)) + MovementLimits(1L) + BaseInfo("lala", Team.blue)

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

      }
    }


  }


}
