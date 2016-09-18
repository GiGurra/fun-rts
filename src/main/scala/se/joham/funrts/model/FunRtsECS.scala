package se.joham.funrts.model

import se.gigurra.scalego.core.{ComponentTypeInfo, ECS}
import se.joham.funrts.model.components.{Acting, BaseInfo, MovementLimits, Positionable}
import se.joham.funrts.model.systems.PositionSystem

/**
  * Created by johan on 2016-06-26.
  */
object FunRtsECS {

  type IdTypes = se.gigurra.scalego.core.IdTypes {
    type EntityId = String
    type SystemId = String
  }

  type ECS = se.gigurra.scalego.core.ECS[IdTypes]
  type SystemId = IdTypes#SystemId
  type EntityId = IdTypes#EntityId
  type Entity = se.gigurra.scalego.core.Entity[IdTypes]

  val Entity = se.gigurra.scalego.core.Entity

  implicit val positionComponentInfo        = ComponentTypeInfo[Positionable, IdTypes]("position")
  implicit val baseInfoComponentInfo        = ComponentTypeInfo[BaseInfo, IdTypes]("base-info")
  implicit val movementLimitsComponentInfo  = ComponentTypeInfo[MovementLimits, IdTypes]("movement-limits")
  implicit val actingComponentInfo          = ComponentTypeInfo[Acting, IdTypes]("acting")

  def apply(): ECS = ECS(
    new PositionSystem,
    new DefaultSystem[BaseInfo],
    new DefaultSystem[MovementLimits],
    new DefaultSystem[Acting]
  )
}
