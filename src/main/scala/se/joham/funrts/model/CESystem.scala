package se.joham.funrts.model

import scala.collection.mutable
import scala.language.implicitConversions

/**
  * Created by johan on 2016-06-12.
  */
case class CESystem[T <: Component](entries: mutable.Map[Entity, T]) {
}

object CESystem {
  def apply[T <: Component](entries: Map[EntityId, T]): CESystem[T] = new CESystem[T](new mutable.HashMap[Entity, T] ++ entries.map(p => Entity(p._1) -> p._2))
  def apply[T <: Component](): CESystem[T] = apply[T](Map.empty[EntityId, T])
  implicit def system2map[T <: Component](system: CESystem[T]): mutable.Map[Entity, T] = system.entries
}
