package se.joham.funrts.model

import scala.collection.mutable
import scala.language.implicitConversions

trait CESystem[T <: Component] {
  protected def entries: mutable.Map[Entity.Id, T]
  def apply(entity: Entity.Id): T = entries.apply(entity)
  def get(entity: Entity.Id): Option[T] = entries.get(entity)
  def -=(entity: Entity.Id): Unit = entries -= entity
  def values: Iterable[T] = entries.values
  def keys: Iterable[Entity.Id] = entries.keys
  def put(entity: Entity.Id, component: T)(implicit store: CEStore, terrain: Terrain): Unit = entries.put(entity, component)
  def size: Int = entries.size
  def isEmpty: Boolean = size == 0
  def nonEmpty: Boolean = !isEmpty
  def update(dt: Long)(implicit store: CEStore, terrain: Terrain): Unit = {}// Executed every sim iteration
  def duplicate: CESystem[T]
}

object CESystem {
  implicit def sys2Map[T <: Component](sys: CESystem[T]): scala.collection.Map[Entity.Id, T] = {
    sys.entries
  }
}

case class DefaultCESystem[T <: Component](entries: mutable.Map[Entity.Id, T] = mutable.Map.empty[Entity.Id, T]) extends CESystem[T] {
  def duplicate: DefaultCESystem[T] = copy(entries.clone())
}
