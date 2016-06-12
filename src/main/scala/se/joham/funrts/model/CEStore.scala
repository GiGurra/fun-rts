package se.joham.funrts.model

import scala.collection.mutable
import scala.language.implicitConversions

trait CEStore {
  def containsEntity(entity: Entity): Boolean
  def systems: scala.collection.Map[CESystemId, CESystem[Component]]
  def system[T <: Component : ComponentType]: CESystem[T]
  def -=(entity: Entity): Unit
  def componentsOf(entity: Entity): Iterable[Component]

  @deprecated("Use sparingly - VERY expensive. For testing/debugging", "2016-06-12")
  def duplicate: CEStore

  @deprecated("Use sparingly - VERY expensive. For testing/debugging", "2016-06-12")
  def allEntities: Set[Entity] // This function is slooooooow

  @deprecated("Use sparingly - VERY expensive. For testing/debugging", "2016-06-12")
  def -(entity: Entity): CEStore = {
    val out = duplicate
    out -= entity
    out
  }
}

/**
  * Created by johan on 2016-06-11.
  */
case class DefaultCEStore(systems: mutable.Map[CESystemId, CESystem[Component]]) extends CEStore {

  def system[T <: Component : ComponentType]: CESystem[T] = {
    val typ = implicitly[ComponentType[T]]
    systems.getOrElseUpdate(typ.typeId, typ.systemFactory.apply().asInstanceOf[CESystem[Component]]).asInstanceOf[CESystem[T]]
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

  def containsEntity(entity: Entity): Boolean = {
    systems.values.exists(_.contains(entity))
  }
  
  @deprecated("Use sparingly - VERY expensive. For testing/debugging", "2016-06-12")
  def allEntities: Set[Entity] = {
    systems.values.flatMap(_.keys).toSet
  }

  @deprecated("Use sparingly - VERY expensive. For testing/debugging", "2016-06-12")
  def duplicate: DefaultCEStore = {
    copy(systems = new mutable.HashMap[CESystemId, CESystem[Component]] ++ systems.mapValues(_.duplicate))
  }

}

object CEStore {
  def apply(systems: Map[CESystemId, CESystem[Component]] = Map.empty): DefaultCEStore = {
    new DefaultCEStore(new mutable.HashMap[CESystemId, CESystem[Component]] ++ systems)
  }

  def classes = List(classOf[DefaultCEStore])

  implicit def store2map(store: CEStore): scala.collection.Map[CESystemId, CESystem[Component]] = store.systems
}
