package se.joham.funrts.model

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
