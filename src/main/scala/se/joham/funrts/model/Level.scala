package se.joham.funrts.model

import java.util

import se.joham.funrts.math.Vec2FixPt
import se.joham.funrts.model.components.{Acting, BaseInfo, MovementLimits, Positionable}

/**
  * Created by johan on 2016-06-11.
  */
case class Level(mesh: Mesh, entityStore: CEStore) {
  implicit val _mesh = mesh
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
    copy(mesh.duplicate, entityStore - entity)
  }

  @deprecated("Use sparingly - VERY expensive. For testing/debugging", "2016-06-12")
  def duplicate: Level = {
    copy(mesh.duplicate, entityStore.duplicate)
  }
}

case class Mesh(nx: Int, ny: Int, tiles: Array[Tile]) {

  override def equals(other: Any): Boolean = {
    other match {
      case m: Mesh => (nx == m.nx) && (ny == m.ny) && util.Arrays.equals(tiles, m.tiles)
      case _ => false
    }
  }

  val size: Size = Vec2FixPt(nx, ny)

  def update(pos: Pos, tile: Tile): Unit = {
    require(pos.x < size.x, s"Position $pos out of level bounds $size")
    require(pos.x >= 0, s"Position $pos out of level bounds $size")
    require(pos.y < size.y, s"Position $pos out of level bounds $size")
    require(pos.y >= 0, s"Position $pos out of level bounds $size")
    tiles(pos2Index(pos)) = tile
  }


  def apply(pos: Pos): Tile = {
    require(pos.x < size.x, s"Position $pos out of level bounds $size")
    require(pos.x >= 0, s"Position $pos out of level bounds $size")
    require(pos.y < size.y, s"Position $pos out of level bounds $size")
    require(pos.y >= 0, s"Position $pos out of level bounds $size")
    tiles(pos2Index(pos))
  }

  def pos2Index(pos: Pos): Int = {
    (pos.y * nx + pos.x).toInt
  }

  @deprecated("Use sparingly - VERY expensive. For testing/debugging", "2016-06-12")
  def duplicate: Mesh = {
    copy(tiles = util.Arrays.copyOf(tiles, tiles.length))
  }

}

object Level {
  def apply(nx: Int,
            ny: Int,
            seed: String,
            generator: LevelGenerator): Level = {
    val mesh = generator.apply(nx, ny, seed)
    new Level(mesh, GameCESTore())
  }
}
