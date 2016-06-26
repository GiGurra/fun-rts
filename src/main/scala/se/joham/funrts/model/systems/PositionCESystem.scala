package se.joham.funrts.model.systems

import se.joham.funrts.model.components.Positionable
import se.joham.funrts.model._
import se.joham.funrts.model.Tile._

import scala.collection.mutable

/**
  * Created by johan on 2016-06-12.
  */
case class PositionCESystem(entries: mutable.Map[Entity.Id, Positionable] = mutable.HashMap.empty) extends CESystem[Positionable] {

  override def put(entity: Entity.Id, component: Positionable)(implicit store: CEStore, terrain: Terrain): Unit = {
    val positions = component.positions
    val tiles = positions.map(terrain.apply)

    require(tiles.forall(_.isEitherType(component.acceptedTiles)), s"Cannot place entity $entity at $positions since tiles at that position are not compatible!")
    require(positions.forall(isVacant(_, entity)), s"Cannot place entity $entity at $positions since that position is already occupied!")

    super.put(entity, component)
    println(s"Added component Positionable. ${Entity(entity).info}")
  }

  override def update(dt: Long)(implicit store: CEStore, terrain: Terrain): Unit = {

  }

  def isConflict(component: Positionable, self: Entity): Boolean = !component.positions.forall(isVacant(_, self))
  def isOccupied(pos: Pos.Type, self: Entity.Id = null.asInstanceOf[Entity.Id]): Boolean = entries.filterKeys(_ != self).values.exists(_.contains(pos))
  def isVacant(pos: Pos.Type, self: Entity.Id = null.asInstanceOf[Entity.Id]): Boolean = !isOccupied(pos, self)

  def duplicate: PositionCESystem = {
    copy(entries.clone())
  }
}
