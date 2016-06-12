package se.joham.funrts.model.v2.components

import se.joham.funrts.model._
import se.joham.funrts.model.v2.{Component, ComponentTypeIdentifier}

/**
  * Created by johan on 2016-06-12.
  */
case class Acting(action: Action) extends Component {
  def typeIdentifier: ComponentTypeIdentifier[Acting] = Acting.typ
}

object Acting {
  implicit val typ = new ComponentTypeIdentifier[Acting] {
    override val typeId: ComponentSystemId = "Acting"
  }
}