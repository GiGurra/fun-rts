package se.joham.funrts.model

import se.joham.funrts.model.components.{Acting, BaseInfo, MovementLimits}
import se.joham.funrts.model.systems.PositionCESystem
import se.joham.funrts.scalego.CEStore

/**
  * Created by johan on 2016-06-26.
  */
object DefaultCEStore {
  type Type = CEStore[Context]
  def apply(): Type = CEStore[Context]() ++ PositionCESystem() ++ DefaultCESystem[BaseInfo]() ++ DefaultCESystem[MovementLimits]() ++ DefaultCESystem[Acting]()
}
