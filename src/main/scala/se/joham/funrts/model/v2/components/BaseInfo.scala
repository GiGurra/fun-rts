package se.joham.funrts.model.v2.components

import se.joham.funrts.model._
import se.joham.funrts.model.v2.{Component, ComponentTypeIdentifier}

/**
  * Created by johan on 2016-06-12.
  */
case class BaseInfo(name: String, team: Team) extends Component {
  def typeIdentifier: ComponentTypeIdentifier[BaseInfo] = BaseInfo.typ
}

object BaseInfo {
  implicit val typ = ComponentTypeIdentifier[BaseInfo]
}