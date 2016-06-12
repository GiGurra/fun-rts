package se.joham.funrts.model.components

import se.joham.funrts.model.{CESystemFactory, Component, ComponentType}

/**
  * Created by johan on 2016-06-12.
  */
case class MovementLimits(topSpeed: Long) extends Component {
  def typeIdentifier: ComponentType[MovementLimits] = MovementLimits.typ
}

object MovementLimits {
  implicit val typ = ComponentType[MovementLimits]
  implicit val sysFactory = CESystemFactory.default[MovementLimits]
}