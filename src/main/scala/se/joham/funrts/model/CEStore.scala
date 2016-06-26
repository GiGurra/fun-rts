package se.joham.funrts.model

import scala.language.implicitConversions

/**
  * Created by johan on 2016-06-11.
  */
case class CEStore(systems: Map[ComponentType.Id, CESystem[Component]] = Map.empty)  {

  def system[T <: Component : ComponentType]: CESystem[T] = {
    val typ = implicitly[ComponentType[T]]
    systems.getOrElse(typ.id, throw new RuntimeException(s"No system of type $typ in $this")).asInstanceOf[CESystem[T]]
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

  def ++[T <: Component : ComponentType](system: CESystem[T]): CEStore = {
    CEStore(systems + (implicitly[ComponentType[T]].id -> system.asInstanceOf[CESystem[Component]]))
  }
  
  @deprecated("Use sparingly - VERY expensive. For testing/debugging", "2016-06-12")
  def allEntities: Set[Entity.Id] = {
    systems.values.flatMap(_.keys).toSet
  }

  @deprecated("Use sparingly - VERY expensive. For testing/debugging", "2016-06-12")
  def duplicate: CEStore = {
    copy(systems.map(p => p._1 -> p._2.duplicate))
  }

  @deprecated("Use sparingly - VERY expensive. For testing/debugging", "2016-06-12")
  def -(entity: Entity.Id): CEStore = {
    val out = duplicate
    out -= entity
    out
  }
}

object CEStore {
  implicit def store2map(store: CEStore): scala.collection.Map[ComponentType.Id, CESystem[Component]] = store.systems
}
