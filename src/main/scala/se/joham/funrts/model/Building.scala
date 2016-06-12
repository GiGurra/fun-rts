package se.joham.funrts.model
import se.joham.funrts.math.Vec2FixPt

/**
  * Created by johan on 2016-06-11.
  */
case class Building(id: Id,
                    name: String,
                    team: Team,
                    action: Action = Idle(),
                    pos: Pos = Vec2FixPt(0,0),
                    size: Size = Vec2FixPt(2,2)) extends Entity {
}
