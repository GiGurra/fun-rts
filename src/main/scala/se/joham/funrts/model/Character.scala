package se.joham.funrts.model

import se.joham.funrts.math.Vec2FixPt

/**
  * Created by johan on 2016-06-11.
  */
case class Character(name: String,
                     team: Team,
                     var action: Action = Idle(),
                     var pos: Pos = Vec2FixPt(0,0),
                     size: Size = Vec2FixPt(1,1),
                     id: String = Id.gen()) extends Entity {

  override def setAction(action: Action): Unit = {
    this.action = action
  }
}
