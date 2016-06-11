package se.joham.funrts.model
import se.joham.funrts.math.Vec2FixPt

/**
  * Created by johan on 2016-06-11.
  */
case class Building(name: String,
                    pos: Pos,
                    size: Size = Vec2FixPt(2,2),
                    id: String = Id.gen()) extends Entity {
}
