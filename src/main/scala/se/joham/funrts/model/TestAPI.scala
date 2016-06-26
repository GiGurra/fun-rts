package se.joham.funrts.model

import se.gigurra.scalego.Entity
import se.gigurra.scalego.TestAPI._

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
  implicit class TestableTerrain(terrain: Terrain) {
    def duplicate: Terrain = {
      terrain.copy(tiles = java.util.Arrays.copyOf(terrain.tiles, terrain.tiles.length))
    }
  }
}
