package se.joham.funrts.scalego

import scala.language.implicitConversions

/**
  * Created by johan on 2016-06-11.
  */
case class CEStore[ContextType](systems: Map[ComponentType.Id, CESystem[Component, ContextType]] = Map.empty[ComponentType.Id, CESystem[Component, ContextType]])  {

  def system[T <: Component : ComponentType]: CESystem[T, ContextType] = {
    val typ = implicitly[ComponentType[T]]
    systems.getOrElse(typ.id, throw new RuntimeException(s"No system of type $typ in $this")).asInstanceOf[CESystem[T, ContextType]]
  }

  def -=(entity: Entity.Id): Unit = {
    systems.values.foreach(_ -= entity)
  }

  def componentsOf(entity: Entity.Id): Iterable[Component] = {
    for {
      system <- systems.values
      component <- system.get(entity)
    } yield {
      component
    }
  }

  def containsEntity(entity: Entity.Id): Boolean = {
    systems.values.exists(_.contains(entity))
  }

  def ++[T <: Component : ComponentType](system: CESystem[T, ContextType]): CEStore[ContextType] = {
    CEStore(systems + (implicitly[ComponentType[T]].id -> system.asInstanceOf[CESystem[Component, ContextType]]))
  }
  
  @deprecated("Use sparingly - VERY expensive. For testing/debugging", "2016-06-12")
  def allEntities: Set[Entity.Id] = {
    systems.values.flatMap(_.keys).toSet
  }

  @deprecated("Use sparingly - VERY expensive. For testing/debugging", "2016-06-12")
  def duplicate: CEStore[ContextType] = {
    copy(systems.map(p => p._1 -> p._2.duplicate))
  }

  @deprecated("Use sparingly - VERY expensive. For testing/debugging", "2016-06-12")
  def -(entity: Entity.Id): CEStore[ContextType] = {
    val out = duplicate
    out -= entity
    out
  }
}

object CEStore {
  implicit def store2map(store: CEStore[_]): scala.collection.Map[ComponentType.Id, CESystem[Component, _]] = store.systems
}
