package se.joham.funrts.model

import se.joham.funrts.math.Vec2FixPt

/**
  * Created by johan on 2016-06-11.
  */
case class Character(name: String,
                     team: Team,
                     action: Action = Idle(),
                     pos: Pos = Vec2FixPt(0,0),
                     size: Size = Vec2FixPt(1,1),
                     id: String = Id.gen()) extends Entity {
  override lazy val positions: Seq[Pos] = Seq(pos)
}
