package se.joham.funrts.model

import org.scalatest._
import org.scalatest.mock._
import se.joham.funrts.math.Vec2FixPt
import se.joham.funrts.model.components._
import se.gigurra.scalego.Entity
import se.joham.funrts.util.JSON

class CEStoreSpec
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
  implicit val _ctx = Context(store, terrain)

  "CEStore" should {

    "create an entity with components" in {
      val entity = Entity("my-entity-id")
      val p = Positionable(1,2)
      val a = Acting(MovingTo(2,2))
      entity += p
      entity += a

      store.system[Positionable].size shouldBe 1
      store.system[Positionable].apply(entity) shouldBe p

      entity.apply[Positionable] shouldBe p
      entity.component[Positionable] shouldBe p
      entity[Positionable] shouldBe p
      entity.get[Positionable] shouldBe Some(p)
      entity.getComponent[Positionable] shouldBe Some(p)

      store.system[Acting].size shouldBe 1
      store.system[Acting].apply(entity) shouldBe a

      entity.apply[Acting] shouldBe a
      entity.component[Acting] shouldBe a
      entity[Acting] shouldBe a
      entity.get[Acting] shouldBe Some(a)
      entity.getComponent[Acting] shouldBe Some(a)
    }

    "create an entity using a Builder" in {

      val a: Entity = Entity.builder("id1")
      val b: Entity = Entity.builder("id2") + Positionable(1,2)
      val c: Entity = Entity.builder("id3") + Positionable(2,2) + Acting(MovingTo(2,2))

      a.components.toSet shouldBe Set()
      b.components.toSet shouldBe Set(Positionable(1,2))
      c.components.toSet shouldBe Set(Positionable(2,2), Acting(MovingTo(2,2)))

      b[Positionable].pos shouldBe Vec2FixPt(1,2)
      c[Acting].action shouldBe MovingTo(2,2)
    }

    "fail to place two entities in the same position" in {
      Entity.builder("id2") + Positionable(1,2)
      a[IllegalArgumentException] should be thrownBy (Entity.builder("id3") + Positionable(1,2))
    }

    "Write a CEStore as JSOn and read it back" in {

      val a: Entity = Entity.builder("id1")
      val b: Entity = Entity.builder("id2") + Positionable(1,2)
      val c: Entity = Entity.builder("id3") + Positionable(2,2) + Acting(MovingTo(2,2))
      val d: Entity = Entity.builder("id4") + Positionable(3,2) + Acting(MovingTo(2,2)) + MovementLimits(1L)
      val e: Entity = Entity.builder("id5") + Positionable(4,2) + Acting(MovingTo(2,2)) + MovementLimits(1L) + BaseInfo("lala", Team.blue)

      val json = JSON.write(store, pretty = false)
      println(json)

      val storeBack = JSON.read[DefaultCEStore.Type](json)
      println(JSON.write(storeBack, pretty = false))

      storeBack shouldBe store

    }

  }
}