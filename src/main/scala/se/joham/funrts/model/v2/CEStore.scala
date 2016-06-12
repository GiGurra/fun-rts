package se.joham.funrts.model.v2

import se.joham.funrts.model._
import scala.collection.mutable
import scala.language.implicitConversions

/**
  * Created by johan on 2016-06-11.
  */
case class CEStore(systems: mutable.Map[Id, CESystem[Component]] = new mutable.HashMap[Id, CESystem[Component]]) {

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

  private[v2] def systemOf(typeIdentifier: ComponentTypeIdentifier[Component]): CESystem[Component] = {
    systems.getOrElseUpdate(typeIdentifier.typeId, CESystem[Component]())
  }
}

object CEStore {
  implicit def store2map(store: CEStore): mutable.Map[Id, CESystem[Component]] = new mutable.Map[Id, CESystem[Component]] {
    val systems = store.systems
    override def get(key: Id): Option[CESystem[Component]] = systems.get(key)
    override def iterator: Iterator[(Id, CESystem[Component])] = systems.iterator
    override def put(k: Id, v: CESystem[Component]): Option[CESystem[Component]] = systems.put(k, v)
    override def +=(kv: (Id, CESystem[Component])): this.type = { systems += kv; this }
    override def -=(key: Id): this.type = { systems -= key; this }
  }
}
