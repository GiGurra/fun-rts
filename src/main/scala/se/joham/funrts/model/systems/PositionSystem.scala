package se.joham.funrts.model.systems

import se.gigurra.scalego.core.System
import se.joham.funrts.model.FunRtsECS._
import se.joham.funrts.model._
import se.joham.funrts.model.components.Positionable

import scala.collection.mutable

/**
  * Created by johan on 2016-06-12.
  */
class PositionSystem
  extends System(FunRtsECS.positionComponentInfo)(mutable.HashMap())
  with UpdatingSystem[Positionable] {

  def isConflict(component: Positionable, self: EntityId): Boolean = !component.positions.forall(isVacant(_, self))
  def isOccupied(pos: Pos.Type, self: EntityId = null.asInstanceOf[EntityId]): Boolean = this.filterKeys(_ != self).values.exists(_.contains(pos))
  def isVacant(pos: Pos.Type, self: EntityId = null.asInstanceOf[EntityId]): Boolean = !isOccupied(pos, self)

  override def update(dt: Long, level: Level): Unit = {

  }

}
