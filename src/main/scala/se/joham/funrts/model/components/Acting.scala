package se.joham.funrts.model.components

import se.joham.funrts.model._
import se.joham.funrts.scalego.{Component, ComponentType}


/**
  * Created by johan on 2016-06-12.
  */
case class Acting(action: Action) extends Component {
  def typ: ComponentType[Acting] = Acting.typ
}

object Acting {
  implicit val typ = ComponentType[Acting]
}