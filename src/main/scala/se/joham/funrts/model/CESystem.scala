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
    override def apply(): CESystem[T] = DefaultCESystem[T]()
  }
}

trait CESystem[T <: Component] {
  protected def entries: mutable.Map[Entity, T]
  def apply(entity: Entity): T = entries.apply(entity)
  def get(entity: Entity): Option[T] = entries.get(entity)
  def -=(entity: Entity): Unit = entries -= entity
  def values: Iterable[T] = entries.values
  def keys: Iterable[Entity] = entries.keys
  def put(entity: Entity, component: T)(implicit store: CEStore, mesh: Mesh): Unit = entries.put(entity, component)
  def size: Int = entries.size
  def isEmpty: Boolean = size == 0
  def nonEmpty: Boolean = !isEmpty
  def update(dt: Long)(implicit store: CEStore, mesh: Mesh): Unit // Executed every sim iteration
}

object CESystem {
  implicit def sys2Map[T <: Component](sys: CESystem[T]): scala.collection.Map[Entity, T] = {
    sys.entries
  }
}

/**
  * Created by johan on 2016-06-12.
  */
case class DefaultCESystem[T <: Component](entries: mutable.Map[Entity, T]) extends CESystem[T] {
  def update(dt: Long)(implicit store: CEStore, mesh: Mesh): Unit = {}
}

object DefaultCESystem {
  def apply[T <: Component](entries: Map[EntityId, T]): CESystem[T] = new DefaultCESystem[T](new mutable.HashMap[Entity, T] ++ entries.map(p => Entity(p._1) -> p._2))
  def apply[T <: Component](): CESystem[T] = apply[T](Map.empty[EntityId, T])
}
