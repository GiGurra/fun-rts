package se.joham.funrts.model

import se.joham.funrts.model.components.{Acting, BaseInfo, MovementLimits, Positionable}

import scala.reflect.ClassTag

/**
  * Created by johan on 2016-06-12.
  */
trait Component { def typeIdentifier: ComponentType[_ <: Component] }
trait ComponentType[T <: Component] {
  def typeId: CESystemId
  def newSystem: CESystem[T]
}
object ComponentType {
  def apply[T <: Component : ClassTag](systemCtor: => CESystem[T]) = {
    new ComponentType[T] {
      def typeId: CESystemId = implicitly[ClassTag[T]].runtimeClass.getSimpleName
      def newSystem: CESystem[T] = systemCtor
    }
  }
}

object Component {
  val classes = List(
    classOf[Positionable],
    classOf[BaseInfo],
    classOf[Acting],
    classOf[MovementLimits]
  )
}
