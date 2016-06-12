package se.joham.funrts.model

import scala.collection.mutable
import scala.language.implicitConversions

/**
  * Created by johan on 2016-06-12.
  */
case class CESystem[T <: Component](entries: mutable.Map[Entity, T] = new mutable.HashMap[Entity, T]) {
}

object CESystem {

  def apply(entries: Map[EntityId, Component]): CESystem[Component] = {
    new CESystem[Component](new mutable.HashMap[Entity, Component] ++ entries.map(p => Entity(p._1) -> p._2))
  }

  implicit def system2map[T <: Component](system: CESystem[T]): mutable.Map[Entity, T] = system.entries
}
