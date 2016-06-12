package se.joham.funrts.model.v2

import se.joham.funrts.model._
import se.joham.funrts.model.v2.components.{Acting, BaseInfo, MovementLimits, Positionable}

import scala.reflect.ClassTag

/**
  * Created by johan on 2016-06-12.
  */
trait Component { def typeIdentifier: ComponentTypeIdentifier[_ <: Component] }
case class ComponentTypeIdentifier[+T <: Component](typeId: ComponentSystemId)
object ComponentTypeIdentifier {
  def apply[T <: Component : ClassTag]: ComponentTypeIdentifier[T] = {
    new ComponentTypeIdentifier(implicitly[ClassTag[T]].runtimeClass.getSimpleName)
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
