package se.joham.funrts.model

import se.joham.funrts.model.components.{Acting, BaseInfo, MovementLimits, Positionable}

/**
  * Created by johan on 2016-06-13.
  */
object GameCESTore {
  def apply(): CEStore = CEStore(Positionable.typ, BaseInfo.typ, MovementLimits.typ, Acting.typ)
}
