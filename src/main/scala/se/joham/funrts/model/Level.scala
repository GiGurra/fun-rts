package se.joham.funrts.model

import se.joham.funrts.model.FunRtsECS.{ECS, Entity}


/**
  * Created by johan on 2016-06-11.
  */
case class Level(terrain: Terrain, entityStore: ECS) {
  implicit val _terrain = terrain
  implicit val _stor = entityStore

  def -=(entity: Entity): Unit = {
    entityStore -= entity
  }

  def containsEntity(entity: Entity): Boolean = {
    entityStore.containsEntity(entity)
  }

}

object Level {

  def apply(nx: Int,
            ny: Int,
            seed: String,
            generator: LevelGenerator,
            entityStore: ECS): Level = {
    val terrain = generator.apply(nx, ny, seed)
    new Level(terrain, entityStore)
  }
}
