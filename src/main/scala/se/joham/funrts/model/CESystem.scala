package se.joham.funrts.model

import scala.collection.mutable
import scala.language.implicitConversions

/**
  * Created by johan on 2016-06-12.
  */
case class CESystem[T <: Component](entries: mutable.Map[Entity, T]) {
  def apply(entity: Entity): T = entries.apply(entity)
  def get(entity: Entity): Option[T] = entries.get(entity)
  def -=(entity: Entity): Unit = entries -= entity
  def values: Iterable[T] = entries.values
  def keys: Iterable[Entity] = entries.keys
  def put(k: Entity, v: T): Unit = entries.put(k,v)
  def size: Int = entries.size
  def isEmpty: Boolean = size == 0
  def nonEmpty: Boolean = !isEmpty
}

object CESystem {
  def apply[T <: Component](entries: Map[EntityId, T]): CESystem[T] = new CESystem[T](new mutable.HashMap[Entity, T] ++ entries.map(p => Entity(p._1) -> p._2))
  def apply[T <: Component](): CESystem[T] = apply[T](Map.empty[EntityId, T])
}
