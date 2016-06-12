package se.joham.funrts.model

import scala.collection.mutable
import scala.language.implicitConversions

trait CEStore {
  def systems: scala.collection.Map[CESystemId, CESystem[Component]]
  def system[T <: Component : ComponentType]: CESystem[T]
  def allEntities: Set[Entity] // This function is slooooooow
  def -=(entity: Entity): Unit
  def componentsOf(entity: Entity): Iterable[Component]
}

/**
  * Created by johan on 2016-06-11.
  */
case class DefaultCEStore(systems: mutable.Map[CESystemId, CESystem[Component]]) extends CEStore {

  def system[T <: Component : ComponentType]: CESystem[T] = {
    val typ = implicitly[ComponentType[T]]
    systems.getOrElseUpdate(typ.typeId, typ.systemFactory.apply().asInstanceOf[CESystem[Component]]).asInstanceOf[CESystem[T]]
  }

  def allEntities: Set[Entity] = {
    systems.values.flatMap(_.keys).toSet
  }

  def -=(entity: Entity): Unit = {
    systems.values.foreach(_ -= entity)
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
  def apply(systems: Map[CESystemId, CESystem[Component]] = Map.empty): DefaultCEStore = {
    new DefaultCEStore(new mutable.HashMap[CESystemId, CESystem[Component]] ++ systems)
  }

  def classes = List(classOf[DefaultCEStore])

  implicit def store2map(store: CEStore): scala.collection.Map[CESystemId, CESystem[Component]] = store.systems
}
