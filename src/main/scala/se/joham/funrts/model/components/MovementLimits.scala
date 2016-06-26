package se.joham.funrts.model.components

import se.gigurra.scalego.{Component, ComponentType}

/**
  * Created by johan on 2016-06-12.
  */
case class MovementLimits(topSpeed: Long) extends Component {
  def typ: ComponentType[MovementLimits] = MovementLimits.typ
}

object MovementLimits {
  implicit val typ = ComponentType[MovementLimits]
}