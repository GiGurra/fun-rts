package se.joham.funrts.model

import java.util.UUID

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success, Try}
import org.scalatest._
import org.scalatest.mock._
import se.gigurra.franklin.Collection._
import se.joham.funrts.math.Vec2FixPt

class ModelSpec
  extends WordSpec
  with MockitoSugar
  with Matchers
  with OneInstancePerTest
  with BeforeAndAfterAll
  with BeforeAndAfterEach {

  val levelGen = GroundLevelGenerator
  val level = Level(10, 10, seed = "test", levelGen)

  "Model" should {

    "Level" should {
      "be able to add and get entities of different types from a Level" in {
        val b = Building(id = "b", name = "farm", pos = Vec2FixPt(1,1), team = Team.blue)
        val c = Character(id = "c", name = "footman", pos = Vec2FixPt(0,0), team = Team.blue)

        level += b
        level += c

        level.entitiesOfType[Building].map(_.state) shouldBe Seq(b)
        level.entitiesOfType[Character].map(_.state) shouldBe Seq(c)

      }

      "find entity by ID" in {
        val b = Building(id = "b", name = "farm", pos = Vec2FixPt(1,1), team = Team.blue)
        val c = Character(id = "c", name = "footman", pos = Vec2FixPt(0,0), team = Team.blue)

        level += b
        level += c

        level.entity(b.id) shouldBe Some(StateFul(b))
        level.entity(c.id) shouldBe Some(StateFul(c))
        level.entity("lalala") shouldBe None

      }

      "Not overlap positions between buildings and characters" in {
        val character = Character(id = "character", name = "footman", pos = Vec2FixPt(2,2), team = Team.blue)
        val building = Building(id = "building", name = "farm", pos = Vec2FixPt(1,1), size = Vec2FixPt(2,2), team = Team.blue)

        level += building

        level.canPlace(character) shouldBe false
        a[IllegalArgumentException] should be thrownBy (level += character)

      }
    }


  }


}
