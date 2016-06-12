package se.joham.funrts.model.v2.components

import se.joham.funrts.model.v2.{Component, ComponentTypeIdentifier}

/**
  * Created by johan on 2016-06-12.
  */
case class MovementLimits(topSpeed: Long) extends Component {
  def typeIdentifier: ComponentTypeIdentifier[MovementLimits] = MovementLimits.typ
}

object MovementLimits {
  implicit val typ = ComponentTypeIdentifier[MovementLimits]
}