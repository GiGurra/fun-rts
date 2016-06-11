package se.joham.funrts.model.v2

import se.joham.funrts.model._
import scala.collection.mutable
import scala.language.implicitConversions

/**
  * Created by johan on 2016-06-11.
  */
case class CEStore(systems: mutable.Map[Id, CESystem[_ <: Component]] = new mutable.HashMap[Id, CESystem[_ <: Component]]) extends mutable.Map[Id, CESystem[_ <: Component]] {

  def system[T <: Component : ComponentTypeIdentifier]: CESystem[T] = {
    systemOf(implicitly[ComponentTypeIdentifier[T]]).asInstanceOf[CESystem[T]]
  }

  def allEntities: Set[Entity] = {
    systems.values.flatMap(_.keys).toSet
  }

  def -=(entity: Entity): Unit = {
    systems.values.foreach(_ -= entity)
  }

  def copy(from: Entity, to: Entity): Unit = {
    for {
      system <- systems.values
      component <- system.get(from)
    } {
      system.asInstanceOf[CESystem[Component]].put(to, component)
    }
  }

  def componentsOf(entity: Entity): Iterable[Component] = {
    for {
      system <- systems.values
      component <- system.get(entity)
    } yield {
      component
    }
  }

  override def get(key: Id): Option[CESystem[_ <: Component]] = systems.get(key)
  override def iterator: Iterator[(Id, CESystem[_ <: Component])] = systems.iterator
  override def put(k: Id, v: CESystem[_ <: Component]): Option[CESystem[_ <: Component]] = systems.put(k, v)
  override def +=(kv: (Id, CESystem[_ <: Component])): this.type = { systems += kv; this }
  override def -=(key: Id): this.type = { systems -= key; this }

  private[v2] def systemOf(typeIdentifier: ComponentTypeIdentifier[_ <: Component]): CESystem[Component] = {
    systems.getOrElseUpdate(typeIdentifier.typeId, CESystem[Component]()).asInstanceOf[CESystem[Component]]
  }
}
