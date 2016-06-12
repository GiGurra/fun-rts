package se.joham.funrts.model.systems

import se.joham.funrts.model.components.Positionable
import se.joham.funrts.model.{CEStore, CESystem, Entity, Mesh}

import scala.collection.mutable

/**
  * Created by johan on 2016-06-12.
  */
case class PositionCESystem(entries: mutable.Map[Entity, Positionable] = new mutable.HashMap[Entity, Positionable])
  extends CESystem[Positionable] {
  override def put(entity: Entity, component: Positionable)(implicit store: CEStore, mesh: Mesh): Unit = {
    super.put(entity, component)
    println(s"Added component Positionable. ${entity.info}")
  }
}
