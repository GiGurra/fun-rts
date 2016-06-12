package se.joham.funrts.model

import scala.collection.mutable
import scala.language.implicitConversions

/**
  * Created by johan on 2016-06-11.
  */
case class CEStore(systems: mutable.Map[CESystemId, CESystem[Component]] = new mutable.HashMap[CESystemId, CESystem[Component]]) {

  def system[T <: Component : ComponentType : CESystemFactory]: CESystem[T] = {
    val id = implicitly[ComponentType[T]].typeId
    systems.getOrElseUpdate(id, implicitly[CESystemFactory[T]].apply().asInstanceOf[CESystem[Component]]).asInstanceOf[CESystem[T]]
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
}

object CEStore {
  def apply(systems: Map[CESystemId, CESystem[Component]]): CEStore = {
    new CEStore(new mutable.HashMap[CESystemId, CESystem[Component]] ++ systems)
  }

  implicit def store2map(store: CEStore): mutable.Map[CESystemId, CESystem[Component]] = store.systems
}
