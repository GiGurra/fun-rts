package se.joham.funrts.model

import se.joham.funrts.model.components.{Acting, BaseInfo, MovementLimits}
import se.joham.funrts.model.systems.PositionCESystem
import se.gigurra.scalego.CEStore

/**
  * Created by johan on 2016-06-26.
  */
object DefaultCEStore {
  type Type = CEStore[Context]
  def apply(): Type = (CEStore.newBuilder[Context] ++
    PositionCESystem() ++
    DefaultCESystem[BaseInfo]() ++
    DefaultCESystem[MovementLimits]() ++
    DefaultCESystem[Acting]()).build
}
