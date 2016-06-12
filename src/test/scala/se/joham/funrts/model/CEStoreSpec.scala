package se.joham.funrts.model

import org.scalatest._
import org.scalatest.mock._
import se.joham.funrts.math.Vec2FixPt
import se.joham.funrts.model.components._
import se.joham.funrts.util.JSON

class CEStoreSpec
  extends WordSpec
  with MockitoSugar
  with Matchers
  with OneInstancePerTest
  with BeforeAndAfterAll
  with BeforeAndAfterEach {

  val levelGen = GroundLevelGenerator
  val level = Level(10, 10, seed = "test", levelGen)

  "CEStore" should {

    "automatically add systems on demand" in {

      val store = CEStore()
      store.isEmpty shouldBe true

      val posSystem = store.system[Positionable]
      store.isEmpty shouldBe false
      posSystem.isEmpty shouldBe true

      store.system[Positionable] shouldBe posSystem
      store.size shouldBe 1

      val moveLimitsSystem = store.system[MovementLimits]
      store.size shouldBe 2

    }

    "create an entity with components" in {
      implicit val store = CEStore()
      val entity = Entity("id")
      val p = Positionable(1,2)
      val a = Acting(MovingTo(2,2))
      entity += p
      entity += a

      implicit val pSystem = store.system[Positionable]
      implicit val aSystem = store.system[Acting]

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
      implicit val store = CEStore()
      implicit val pSystem = store.system[Positionable]
      implicit val aSystem = store.system[Acting]

      val a: Entity = Entity.builder("id1")
      val b: Entity = Entity.builder("id2") + Positionable(1,2)
      val c: Entity = Entity.builder("id3") + Positionable(1,2) + Acting(MovingTo(2,2))

      a.components.toSet shouldBe Set()
      b.components.toSet shouldBe Set(Positionable(1,2))
      c.components.toSet shouldBe Set(Positionable(1,2), Acting(MovingTo(2,2)))

      b[Positionable].pos shouldBe Vec2FixPt(1,2)
      c[Acting].action shouldBe MovingTo(2,2)
    }

    "Write a CEStore as JSOn and read it back" in {

      implicit val store = CEStore()

      implicit val pSystem = store.system[Positionable]
      implicit val aSystem = store.system[Acting]
      implicit val mSystem = store.system[MovementLimits]
      implicit val bSystem = store.system[BaseInfo]

      val a: Entity = Entity.builder("id1")
      val b: Entity = Entity.builder("id2") + Positionable(1,2)
      val c: Entity = Entity.builder("id3") + Positionable(1,2) + Acting(MovingTo(2,2))
      val d: Entity = Entity.builder("id4") + Positionable(1,2) + Acting(MovingTo(2,2)) + MovementLimits(1L)
      val e: Entity = Entity.builder("id5") + Positionable(1,2) + Acting(MovingTo(2,2)) + MovementLimits(1L) + BaseInfo("lala", Team.blue)

      val json = JSON.write(store, pretty = false)
      println(json)

      val storeBack = JSON.read[CEStore](json)
      println(JSON.write(storeBack, pretty = false))

      storeBack shouldBe store

    }

  }
}