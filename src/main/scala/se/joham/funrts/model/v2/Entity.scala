package se.joham.funrts.model.v2

import scala.language.implicitConversions
import se.joham.funrts.model.{Id, _}

/**
  * Created by johan on 2016-06-12.
  */
case class Entity(id: Id = Id.gen()) extends AnyVal {
  def +=[T <: Component: ComponentTypeIdentifier](component: T)(implicit store: CEStore): Entity = {
    store.system[T].entries.put(this, component)
    this
  }
  def get[T <: Component: ComponentTypeIdentifier](implicit system: CESystem[T]): Option[T] = system.get(this)
  def apply[T <: Component: ComponentTypeIdentifier](implicit system: CESystem[T]): T = system.apply(this)
  def component[T <: Component: ComponentTypeIdentifier](implicit system: CESystem[T]): T = apply[T]
  def getComponent[T <: Component: ComponentTypeIdentifier](implicit system: CESystem[T]): Option[T] = get[T]
  def components(implicit store: CEStore): Iterable[Component] = store.componentsOf(this)
}
object Entity {
  def builder(implicit store: CEStore): Builder = new Builder(Entity())

  case class Builder(entity: Entity)(implicit store: CEStore) {
    def +[T <: Component: ComponentTypeIdentifier](component: T): Builder = {
      entity += component
      this
    }
  }
  object Builder {
    implicit def builder2entity(b: Builder): Entity = b.entity
  }
}
