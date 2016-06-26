package se.joham.funrts.model.components

import se.joham.funrts.model._
import se.gigurra.scalego.{Component, ComponentType}


/**
  * Created by johan on 2016-06-12.
  */
case class BaseInfo(name: String, team: Team) extends Component {
  def typ: ComponentType[BaseInfo] = BaseInfo.typ
}

object BaseInfo {
  implicit val typ = ComponentType[BaseInfo]
}
