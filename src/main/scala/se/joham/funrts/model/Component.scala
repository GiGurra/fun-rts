package se.joham.funrts.model

import se.joham.funrts.model.components.{Acting, BaseInfo, MovementLimits, Positionable}

import scala.reflect.ClassTag

/**
  * Created by johan on 2016-06-12.
  */
trait Component { def typeIdentifier: ComponentType[_ <: Component] }
case class ComponentType[+T <: Component](typeId: CESystemId)
object ComponentType {
  def apply[T <: Component : ClassTag]: ComponentType[T] = {
    new ComponentType(implicitly[ClassTag[T]].runtimeClass.getSimpleName)
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
