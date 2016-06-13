package se.joham.funrts.model.systems

import se.joham.funrts.model.components.Positionable
import se.joham.funrts.model._

import scala.collection.mutable

/**
  * Created by johan on 2016-06-12.
  */
case class PositionCESystem(entries: mutable.Map[EntityId, Positionable] = mutable.HashMap.empty) extends CESystem[Positionable] {

  override def put(entity: EntityId, component: Positionable)(implicit store: CEStore, mesh: Mesh): Unit = {
    val positions = component.positions
    val tiles = positions.map(mesh.apply)

    require(tiles.forall(_.isEitherType(component.acceptedTiles)), s"Cannot place entity $entity at $positions since tiles at that position are not compatible!")
    require(positions.forall(isVacant(_, entity)), s"Cannot place entity $entity at $positions since that position is already occupied!")

    super.put(entity, component)
    println(s"Added component Positionable. ${Entity(entity).info}")
  }

  def update(dt: Long)(implicit store: CEStore, mesh: Mesh): Unit = {

  }

  def isConflict(component: Positionable, self: Entity): Boolean = !component.positions.forall(isVacant(_, self))
  def isOccupied(pos: Pos, self: EntityId = null.asInstanceOf[EntityId]): Boolean = entries.filterKeys(_ != self).values.exists(_.contains(pos))
  def isVacant(pos: Pos, self: EntityId = null.asInstanceOf[EntityId]): Boolean = !isOccupied(pos, self)

  def duplicate: PositionCESystem = {
    copy(entries = new mutable.HashMap[EntityId, Positionable] ++ entries)
  }
}
