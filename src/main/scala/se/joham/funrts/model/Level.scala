package se.joham.funrts.model

import se.joham.funrts.math.Vec2FixPt

import scala.collection.mutable
import scala.reflect.ClassTag

/**
  * Created by johan on 2016-06-11.
  */
case class Level(mesh: Mesh, entityLkup: mutable.HashMap[Id, StateFul[Entity]]) {

  def entities = entityLkup.values

  def size: Size = mesh.size

  def entitiesOfType[T <: Entity : ClassTag]: Iterable[StateFul[T]] = entityLkup.values.collect { case s@StateFul(e: T) => s.asInstanceOf[StateFul[T]] }
  def buildings: Iterable[StateFul[Building]] = entitiesOfType[Building]
  def characters: Iterable[StateFul[Character]] = entitiesOfType[Character]
  def entity(id: Id): Option[StateFul[Entity]] = entityLkup.get(id)

  def +=(entity: Entity): Unit = {
    require(canPlace(entity), s"Cannot place entity ${entity.name + entity.id} at ${entity.positions} - the spot is occupied!")
    entityLkup.put(entity.id, StateFul(entity))
  }

  def canPlace(entity: Entity): Boolean = {
    entity match {
      case c: Character => c.positions.forall(canPlaceCharacterAt)
      case b: Building => b.positions.forall(canPlaceBuildingAt)
    }
  }

  def canPlaceCharacterAt(pos: Pos): Boolean = {
    mesh(pos).isWalkable && isVacant(pos)
  }

  def canPlaceBuildingAt(pos: Pos): Boolean = {
    mesh(pos).isGround && isVacant(pos)
  }

  def isOccupied(pos: Pos): Boolean = {
    !isVacant(pos)
  }

  def isVacant(pos: Pos): Boolean = {
    // TODO: Optimize with cache once we know where in
    // TODO: game logic all movements will take place
    entities.flatMap(_.positions).forall(_ != pos)
  }

}

case class Mesh(nx: Int, ny: Int, cells: Array[Cell]) {

  val size: Size = Vec2FixPt(nx, ny)

  def update(pos: Pos, cell: Cell): Unit = {
    require(pos.x < size.x, s"Position $pos out of level bounds $size")
    require(pos.x >= 0, s"Position $pos out of level bounds $size")
    require(pos.y < size.y, s"Position $pos out of level bounds $size")
    require(pos.y >= 0, s"Position $pos out of level bounds $size")
    cells(pos2Index(pos)) = cell
  }


  def apply(pos: Pos): Cell = {
    require(pos.x < size.x, s"Position $pos out of level bounds $size")
    require(pos.x >= 0, s"Position $pos out of level bounds $size")
    require(pos.y < size.y, s"Position $pos out of level bounds $size")
    require(pos.y >= 0, s"Position $pos out of level bounds $size")
    cells(pos2Index(pos))
  }

  def pos2Index(pos: Pos): Int = {
    (pos.y * nx + pos.x).toInt
  }
}

object Level {
  def apply(nx: Int,
            ny: Int,
            seed: String,
            generator: LevelGenerator): Level = {
    val mesh = generator.apply(nx, ny, seed)
    val entities = new mutable.HashMap[Id, StateFul[Entity]]
    new Level(mesh, entities)
  }
}
