package se.joham.funrts.model

import se.joham.funrts.math.Vec2FixPt

/**
  * Created by johan on 2016-06-11.
  */
case class Character(name: String,
                     pos: Pos,
                     size: Size = Vec2FixPt(1,1),
                     id: String = Id.gen()) extends Entity {
}

