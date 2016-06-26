package se.joham.funrts.model

import se.joham.funrts.model.components.{Acting, BaseInfo, MovementLimits, Positionable}

import scala.reflect.ClassTag

/**
  * Created by johan on 2016-06-12.
  */
trait Component { def typ: ComponentType[_ <: Component] }
trait ComponentType[T <: Component] { def id: ComponentTypeId }
object ComponentType {
  def apply[T <: Component : ClassTag] = {
    new ComponentType[T] {
      def id: ComponentTypeId = implicitly[ClassTag[T]].runtimeClass.getSimpleName
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
