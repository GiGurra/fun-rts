package se.joham.funrts.model.components

import se.joham.funrts.math.Vec2FixPt
import se.joham.funrts.model._

import scala.language.implicitConversions

/**
  * Created by johan on 2016-06-12.
  */
case class Positionable(pos: Pos.Type,
                        size: Size.Type = Vec2FixPt(1,1),
                        acceptedTiles: Set[Byte] = Set(Tile.TYPE_GROUND, Tile.TYPE_BRIDGE)) extends Component {

  val positions: Seq[Pos.Type] = for {
    x <- 0 until size.x.toInt
    y <- 0 until size.y.toInt
  } yield {
    Vec2FixPt(x,y) + pos
  }

  def contains(pos: Pos.Type): Boolean = positions.contains(pos)

  def typ: ComponentType[Positionable] = Positionable.typ
}

object Positionable {
  implicit val typ = ComponentType[Positionable]
  def apply(x: Long, y: Long): Positionable = Positionable(Vec2FixPt(x,y), Vec2FixPt(1,1))
  def apply(x: Long, y: Long, size: Size.Type): Positionable = Positionable(Vec2FixPt(x,y), size)
  def apply(x: Long, y: Long, size: Size.Type, acceptedTiles: Set[Tile.Type]): Positionable = Positionable(Vec2FixPt(x,y), size, acceptedTiles)
}
