package se.joham.funrts.model

import se.joham.funrts.math.Vec2FixPt

/**
  * Created by johan on 2016-06-11.
  */
trait Entity {
  val id: Id
  val name: String
  val pos: Pos
  val size: Size
  val team: Team
  val action: Action

  lazy val positions: Seq[Pos] = for {
    x <- 0 until size.x.toInt
    y <- 0 until size.y.toInt
  } yield {
    Vec2FixPt(x,y) + pos
  }
}
