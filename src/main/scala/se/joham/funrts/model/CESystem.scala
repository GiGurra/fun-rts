package se.joham.funrts.model

import scala.collection.mutable
import scala.language.implicitConversions

trait CESystem[T <: Component] {
  protected def entries: mutable.Map[EntityId, T]
  def apply(entity: EntityId): T = entries.apply(entity)
  def get(entity: EntityId): Option[T] = entries.get(entity)
  def -=(entity: EntityId): Unit = entries -= entity
  def values: Iterable[T] = entries.values
  def keys: Iterable[EntityId] = entries.keys
  def put(entity: EntityId, component: T)(implicit store: CEStore, terrain: Terrain): Unit = entries.put(entity, component)
  def size: Int = entries.size
  def isEmpty: Boolean = size == 0
  def nonEmpty: Boolean = !isEmpty
  def update(dt: Long)(implicit store: CEStore, terrain: Terrain): Unit = {}// Executed every sim iteration
  def duplicate: CESystem[T]
}

object CESystem {
  implicit def sys2Map[T <: Component](sys: CESystem[T]): scala.collection.Map[EntityId, T] = {
    sys.entries
  }
}

case class DefaultCESystem[T <: Component](entries: mutable.Map[EntityId, T] = mutable.Map.empty[EntityId, T]) extends CESystem[T] {
  def duplicate: DefaultCESystem[T] = copy(entries.clone())
}
