package se.joham.funrts.model.components

import se.joham.funrts.model._


/**
  * Created by johan on 2016-06-12.
  */
case class BaseInfo(name: String, team: Team) extends Component {
  def typeIdentifier: ComponentType[BaseInfo] = BaseInfo.typ
}

object BaseInfo {
  implicit val typ = ComponentType[BaseInfo]
  implicit val sysFactory = CESystemFactory.default[BaseInfo]
}