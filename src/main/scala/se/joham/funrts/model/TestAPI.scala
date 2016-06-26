package se.joham.funrts.model

import se.joham.funrts.scalego.Entity
import se.joham.funrts.scalego.TestAPI._

/**
  * Created by johan on 2016-06-26.
  */
object TestAPI {
  implicit class TestableLevel(level: Level) {
    def -(entity: Entity): Level = {
      level.copy(level.terrain.duplicate, level.entityStore - entity)
    }
    def duplicate: Level = {
      level.copy(level.terrain.duplicate, level.entityStore.duplicate)
    }
  }
}
