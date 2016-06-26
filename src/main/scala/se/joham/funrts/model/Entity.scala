package se.joham.funrts.model

import se.joham.funrts.util.JSON

import scala.language.implicitConversions

/**
  * Created by johan on 2016-06-12.
  */
case class Entity(id: Entity.Id) extends AnyVal {
  def +=[T <: Component: ComponentType](component: T)(implicit store: CEStore, terrain: Terrain): Entity = {
    store.system[T].put(this, component)
    this
  }
  def get[T <: Component: ComponentType](implicit system: CESystem[T]): Option[T] = system.get(this)
  def apply[T <: Component: ComponentType](implicit system: CESystem[T]): T = system.apply(this)
  def component[T <: Component: ComponentType](implicit system: CESystem[T]): T = apply[T]
  def getComponent[T <: Component: ComponentType](implicit system: CESystem[T]): Option[T] = get[T]
  def components(implicit store: CEStore): Iterable[Component] = store.componentsOf(this)

  def info(implicit store: CEStore): String = {
    val buffer = new StringBuffer()
    buffer.append(s"Entity ($id) components:\n")
    for (component <- components) {
      buffer.append(s"  ${JSON.write(component, pretty = false)}")
    }
    buffer.toString
  }
}
object Entity {
  type Id = String

  def builder(id: Entity.Id)(implicit store: CEStore, terrain: Terrain): Builder = new Builder(Entity(id))

  case class Builder(entity: Entity)(implicit store: CEStore, terrain: Terrain) {
    def +[T <: Component: ComponentType](component: T): Builder = {
      entity += component
      this
    }
  }
  object Builder {
    implicit def builder2entity(b: Builder): Entity = b.entity
  }

  implicit def e2id(e: Entity): Entity.Id = e.id
}
