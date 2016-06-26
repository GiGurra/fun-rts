package se.joham.funrts.scalego

import se.joham.funrts.model.components.{Acting, BaseInfo, MovementLimits, Positionable}

/**
  * Created by johan on 2016-06-12.
  */
trait Component { def typ: ComponentType[_ <: Component] }

object Component {
  val classes = List(
    classOf[Positionable],
    classOf[BaseInfo],
    classOf[Acting],
    classOf[MovementLimits]
  )
}
