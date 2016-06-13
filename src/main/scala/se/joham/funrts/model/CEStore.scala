package se.joham.funrts.model

import scala.language.implicitConversions

/**
  * Created by johan on 2016-06-11.
  */
case class CEStore(systems: Map[CESystemId, CESystem[Component]])  {

  def system[T <: Component : ComponentType]: CESystem[T] = {
    val typ = implicitly[ComponentType[T]]
    systems.getOrElse(typ.typeId, throw new RuntimeException(s"No system of type $typ in $this")).asInstanceOf[CESystem[T]]
  }

  def -=(entity: EntityId): Unit = {
    systems.values.foreach(_ -= entity)
  }

  def componentsOf(entity: EntityId): Iterable[Component] = {
    for {
      system <- systems.values
      component <- system.get(entity)
    } yield {
      component
    }
  }

  def containsEntity(entity: EntityId): Boolean = {
    systems.values.exists(_.contains(entity))
  }
  
  @deprecated("Use sparingly - VERY expensive. For testing/debugging", "2016-06-12")
  def allEntities: Set[EntityId] = {
    systems.values.flatMap(_.keys).toSet
  }

  @deprecated("Use sparingly - VERY expensive. For testing/debugging", "2016-06-12")
  def duplicate: CEStore = {
    copy(systems.map(p => p._1 -> p._2.duplicate))
  }

  @deprecated("Use sparingly - VERY expensive. For testing/debugging", "2016-06-12")
  def -(entity: EntityId): CEStore = {
    val out = duplicate
    out -= entity
    out
  }
}

object CEStore {

  def apply(types: ComponentType[_]*): CEStore = {
    CEStore(types.map(typ => typ.typeId -> typ.newSystem.asInstanceOf[CESystem[Component]]).toMap)
  }

  implicit def store2map(store: CEStore): scala.collection.Map[CESystemId, CESystem[Component]] = store.systems
}
