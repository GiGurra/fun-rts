package se.joham.funrts.model

import scala.collection.mutable
import scala.language.implicitConversions

trait CESystemFactory[T <: Component] {
  def apply(): CESystem[T]
}

object CESystemFactory {
  def apply[T <: Component](f: () => CESystem[T]): CESystemFactory[T] = new CESystemFactory[T] {
    override def apply(): CESystem[T] = f()
  }
  def default[T <: Component]: CESystemFactory[T] = new CESystemFactory[T] {
    override def apply(): CESystem[T] = new DefaultCESystem[T](mutable.Map.empty)
  }
}

trait CESystem[T <: Component] {
  protected def entries: mutable.Map[EntityId, T]
  def apply(entity: EntityId): T = entries.apply(entity)
  def get(entity: EntityId): Option[T] = entries.get(entity)
  def -=(entity: EntityId): Unit = entries -= entity
  def values: Iterable[T] = entries.values
  def keys: Iterable[EntityId] = entries.keys
  def put(entity: EntityId, component: T)(implicit store: CEStore, mesh: Mesh): Unit = entries.put(entity, component)
  def size: Int = entries.size
  def isEmpty: Boolean = size == 0
  def nonEmpty: Boolean = !isEmpty
  def update(dt: Long)(implicit store: CEStore, mesh: Mesh): Unit = {}// Executed every sim iteration
  def duplicate: CESystem[T]
}

object CESystem {
  implicit def sys2Map[T <: Component](sys: CESystem[T]): scala.collection.Map[EntityId, T] = {
    sys.entries
  }
}

/**
  * Created by johan on 2016-06-12.
  */
case class DefaultCESystem[T <: Component](entries: mutable.Map[EntityId, T]) extends CESystem[T] {
  def duplicate: DefaultCESystem[T] = copy(entries.clone())
}
