package se.joham.funrts.model

/**
  * Created by johan on 2016-06-12.
  */
trait CESystemLogic[T <: Component] {
  def onAdd(store: CEStore, system: CESystem[T], e: Entity, c: T): Unit = {}
  def onRemove(store: CEStore, system: CESystem[T], e: Entity, c: T): Unit = {}
  def update(store: CEStore, system: CESystem[T]): Unit = {}
}

case class NoSystemLogic[T <: Component]() extends CESystemLogic[T]
