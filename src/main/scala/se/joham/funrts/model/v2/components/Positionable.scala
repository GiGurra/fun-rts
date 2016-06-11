package se.joham.funrts.model.v2.components

import se.joham.funrts.math.Vec2FixPt
import se.joham.funrts.model._
import se.joham.funrts.model.v2.{Component, ComponentTypeIdentifier}

/**
  * Created by johan on 2016-06-12.
  */
case class Positionable(pos: Pos, size: Size = Vec2FixPt(1,1)) extends Component {
  def typeIdentifier: ComponentTypeIdentifier[Positionable] = Positionable.typ
}

object Positionable {
  implicit val typ = new ComponentTypeIdentifier[Positionable] {
    override val typeId: Id = "Positionable"
  }
  def apply(x: Long, y: Long): Positionable = Positionable(Vec2FixPt(x,y), Vec2FixPt(1,1))
  def apply(x: Long, y: Long, size: Size): Positionable = Positionable(Vec2FixPt(x,y), size)
}