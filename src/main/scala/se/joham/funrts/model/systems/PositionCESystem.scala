package se.joham.funrts.model.systems

import se.joham.funrts.model.components.Positionable
import se.joham.funrts.model._

import scala.collection.mutable

/**
  * Created by johan on 2016-06-12.
  */
case class PositionCESystem(entries: mutable.Map[Entity, Positionable] = mutable.HashMap.empty) extends CESystem[Positionable] {

  override def put(entity: Entity, component: Positionable)(implicit store: CEStore, mesh: Mesh): Unit = {
    val positions = component.positions
    val tiles = positions.map(mesh.apply)

    require(tiles.forall(_.isEitherType(component.acceptedTiles)), s"Cannot place entity $entity at $positions since tiles at that position are not compatible!")
    require(positions.forall(isVacant(_, entity)), s"Cannot place entity $entity at $positions since that position is already occupied!")

    super.put(entity, component)
    println(s"Added component Positionable. ${entity.info}")
  }


  def isConflict(component: Positionable, self: Entity): Boolean = !component.positions.forall(isVacant(_, self))
  def isOccupied(pos: Pos, self: Entity = null.asInstanceOf[Entity]): Boolean = entries.filterKeys(_ != self).values.exists(_.contains(pos))
  def isVacant(pos: Pos, self: Entity = null.asInstanceOf[Entity]): Boolean = !isOccupied(pos, self)
}
