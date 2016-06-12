package se.joham.funrts.model.v2

import se.joham.funrts.model._
import se.joham.funrts.model.v2.components.{Acting, BaseInfo, MovementLimits, Positionable}

/**
  * Created by johan on 2016-06-12.
  */
trait Component { def typeIdentifier: ComponentTypeIdentifier[_ <: Component] }
trait ComponentTypeIdentifier[+T <: Component] {
  val typeId: Id
}

object Component {
  val classes = List(
    classOf[Positionable],
    classOf[BaseInfo],
    classOf[Acting],
    classOf[MovementLimits]
  )
}
