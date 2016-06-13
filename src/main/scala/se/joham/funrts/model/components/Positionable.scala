package se.joham.funrts.model.components

import se.joham.funrts.math.Vec2FixPt
import se.joham.funrts.model.systems.PositionCESystem
import se.joham.funrts.model._

import scala.language.implicitConversions

/**
  * Created by johan on 2016-06-12.
  */
case class Positionable(pos: Pos,
                        size: Size = Vec2FixPt(1,1),
                        acceptedTiles: Set[Byte] = Set(Tile.TYPE_GROUND, Tile.TYPE_BRIDGE)) extends Component {

  val positions: Seq[Pos] = for {
    x <- 0 until size.x.toInt
    y <- 0 until size.y.toInt
  } yield {
    Vec2FixPt(x,y) + pos
  }

  def typeIdentifier: ComponentType[Positionable] = Positionable.typ
}

object Positionable {
  implicit def p2positions(p: Positionable): Seq[Pos] = p.positions
  implicit val typ = ComponentType(PositionCESystem())
  def apply(x: Long, y: Long): Positionable = Positionable(Vec2FixPt(x,y), Vec2FixPt(1,1))
  def apply(x: Long, y: Long, size: Size): Positionable = Positionable(Vec2FixPt(x,y), size)
  def apply(x: Long, y: Long, size: Size, acceptedTiles: Set[Tile.Type]): Positionable = Positionable(Vec2FixPt(x,y), size, acceptedTiles)
}
