package se.joham.funrts.model.v2

import scala.collection.mutable
import scala.language.implicitConversions

/**
  * Created by johan on 2016-06-12.
  */
case class CESystem[T <: Component](entries: mutable.Map[Entity, T] = new mutable.HashMap[Entity, T]) {
}

object CESystem {
  implicit def system2map[T <: Component](system: CESystem[T]): mutable.Map[Entity, T] = new mutable.Map[Entity, T] {
    val entries = system.entries
    override def apply(key: Entity): T = entries.apply(key)
    override def get(key: Entity): Option[T] = entries.get(key)
    override def iterator: Iterator[(Entity, T)] = entries.iterator

    // These two are unfortunately necessary for the map api to work :/
    override def put(k: Entity, v: T): Option[T] = entries.put(k,v)
    override def +=(kv: (Entity, T)): this.type = { entries += kv; this }
    override def -=(key: Entity): this.type = { entries -= key; this }
  }
}
