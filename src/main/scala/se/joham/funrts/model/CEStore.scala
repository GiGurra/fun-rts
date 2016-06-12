package se.joham.funrts.model

import scala.collection.mutable
import scala.language.implicitConversions

/**
  * Created by johan on 2016-06-11.
  */
case class CEStore(systems: mutable.Map[ComponentSystemId, CESystem[Component]] = new mutable.HashMap[ComponentSystemId, CESystem[Component]]) {

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
      system.put(to, component)
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

  private def systemOf(typeIdentifier: ComponentTypeIdentifier[Component]): CESystem[Component] = {
    systems.getOrElseUpdate(typeIdentifier.typeId, CESystem[Component]())
  }
}

object CEStore {
  def apply(systems: Map[ComponentSystemId, CESystem[Component]]): CEStore = {
    new CEStore(new mutable.HashMap[ComponentSystemId, CESystem[Component]] ++ systems)
  }

  implicit def store2map(store: CEStore): mutable.Map[ComponentSystemId, CESystem[Component]] = store.systems
}
