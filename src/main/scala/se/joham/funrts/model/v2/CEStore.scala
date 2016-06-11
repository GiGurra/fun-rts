package se.joham.funrts.model.v2

import se.joham.funrts.math.Vec2FixPt
import se.joham.funrts.model._

import scala.collection.{Map, mutable}
import scala.language.implicitConversions

case class Entity(id: Id = Id.gen()) extends AnyVal {
  def +=[T <: Component: ComponentTypeIdentifier](component: T)(implicit store: CEStore): Entity = {
    store.system[T].entries.put(this, component)
    this
  }
  def get[T <: Component: ComponentTypeIdentifier](implicit system: CESystem[T]): Option[T] = system.get(this)
  def apply[T <: Component: ComponentTypeIdentifier](implicit system: CESystem[T]): T = system.apply(this)
  def component[T <: Component: ComponentTypeIdentifier](implicit system: CESystem[T]): T = apply[T]
  def getComponent[T <: Component: ComponentTypeIdentifier](implicit system: CESystem[T]): Option[T] = get[T]
  def components(implicit store: CEStore): Iterable[Component] = store.componentsOf(this)
}
object Entity {
  def Builder(implicit store: CEStore): EntityBuilder = new EntityBuilder(Entity())
}

case class EntityBuilder(entity: Entity)(implicit store: CEStore) {
  def +[T <: Component: ComponentTypeIdentifier](component: T): EntityBuilder = {
    entity += component
    this
  }
}
object EntityBuilder {
  implicit def builder2entity(b: EntityBuilder): Entity = b.entity
}

sealed trait Component { def typeIdentifier: ComponentTypeIdentifier[_ <: Component] }
sealed trait ComponentTypeIdentifier[+T <: Component] {
  val typeId: Id = getClass.getSimpleName
}

case class BaseInfo(name: String, team: Team) extends Component {
  def typeIdentifier: ComponentTypeIdentifier[BaseInfo] = BaseInfo.typ
}
object BaseInfo {
  implicit val typ = new ComponentTypeIdentifier[BaseInfo] {}
}

case class Positionable(pos: Pos, size: Size = Vec2FixPt(1,1)) extends Component {
  def typeIdentifier: ComponentTypeIdentifier[Positionable] = Positionable.typ
}
object Positionable {
  implicit val typ = new ComponentTypeIdentifier[Positionable] {}
  def apply(x: Long, y: Long): Positionable = Positionable(Vec2FixPt(x,y), Vec2FixPt(1,1))
  def apply(x: Long, y: Long, size: Size): Positionable = Positionable(Vec2FixPt(x,y), size)
}

case class MovementLimits(topSpeed: Long) extends Component {
  def typeIdentifier: ComponentTypeIdentifier[MovementLimits] = MovementLimits.typ
}
object MovementLimits {
  implicit val typ = new ComponentTypeIdentifier[MovementLimits] {}
}

case class Acting(action: Action) extends Component {
  def typeIdentifier: ComponentTypeIdentifier[Acting] = Acting.typ
}
object Acting {
  implicit val typ = new ComponentTypeIdentifier[Acting] {}
}

case class CESystem[T <: Component](entries: mutable.Map[Entity, T] = new mutable.HashMap[Entity, T]) extends mutable.Map[Entity, T] {

  override def apply(key: Entity): T = entries.apply(key)
  override def get(key: Entity): Option[T] = entries.get(key)
  override def iterator: Iterator[(Entity, T)] = entries.iterator

  // These two are unfortunately necessary for the map api to work :/
  override def put(k: Entity, v: T): Option[T] = entries.put(k,v)
  override def +=(kv: (Entity, T)): this.type = { entries += kv; this }
  override def -=(key: Entity): this.type = { entries -= key; this }
}

/**
  * Created by johan on 2016-06-11.
  */
case class CEStore(systems: mutable.Map[Id, CESystem[_ <: Component]] = new mutable.HashMap[Id, CESystem[_ <: Component]]) extends mutable.Map[Id, CESystem[_ <: Component]] {

  def system[T <: Component : ComponentTypeIdentifier]: CESystem[T] = {
    systems.getOrElseUpdate(implicitly[ComponentTypeIdentifier[T]].typeId, CESystem[T]()).asInstanceOf[CESystem[T]]
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

  def add(entity: Entity, component: Component): Unit = {
    systemOf(component.typeIdentifier).put(entity, component)
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
