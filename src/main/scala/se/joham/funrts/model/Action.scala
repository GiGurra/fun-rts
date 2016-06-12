package se.joham.funrts.model

import se.joham.funrts.math.Vec2FixPt

/**
  * Created by johan on 2016-06-11.
  */
sealed trait Action
case class Idle() extends Action
case class MovingTo(pos: Pos) extends Action
case class AttackingArea(pos: Pos) extends Action
case class AttackingEntity(target: Id) extends Action
case class MiningGold(goldMineId: Id) extends Action
case class ChoppingLumber(pos: Pos) extends Action
case class BuildingUnit() extends Action
case class Researching() extends Action
case class BuildingBuilding() extends Action

object MovingTo {
  def apply(x: Long, y: Long): MovingTo = MovingTo(Vec2FixPt(x, y))
}

object Action {
  val classes = List(
    classOf[Idle],
    classOf[MovingTo],
    classOf[AttackingArea],
    classOf[AttackingEntity],
    classOf[MiningGold],
    classOf[ChoppingLumber],
    classOf[BuildingUnit],
    classOf[Researching],
    classOf[BuildingBuilding]
  )
}