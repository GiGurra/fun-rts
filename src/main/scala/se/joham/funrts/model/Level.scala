package se.joham.funrts.model

import se.joham.funrts.model.components._

/**
  * Created by johan on 2016-06-11.
  */
case class Level(terrain: Terrain, entityStore: CEStore) {
  implicit val _terrain = terrain
  implicit val _stor = entityStore
  implicit val _pSys = entityStore.system[Positionable]
  implicit val _aSys = entityStore.system[Acting]
  implicit val _mSys = entityStore.system[MovementLimits]
  implicit val _bSys = entityStore.system[BaseInfo]

  def -=(entity: Entity): Unit = {
    entityStore -= entity
  }

  def containsEntity(entity: Entity): Boolean = {
    entityStore.containsEntity(entity)
  }

  @deprecated("Use sparingly - VERY expensive. For testing/debugging", "2016-06-12")
  def -(entity: Entity): Level = {
    copy(terrain.duplicate, entityStore - entity)
  }

  @deprecated("Use sparingly - VERY expensive. For testing/debugging", "2016-06-12")
  def duplicate: Level = {
    copy(terrain.duplicate, entityStore.duplicate)
  }
}

object Level {
  def apply(nx: Int,
            ny: Int,
            seed: String,
            generator: LevelGenerator): Level = {
    val terrain = generator.apply(nx, ny, seed)
    new Level(terrain, CEStore.default)
  }
}
