package se.joham.funrts.model.v2

import se.joham.funrts.model._

/**
  * Created by johan on 2016-06-12.
  */
trait Component { def typeIdentifier: ComponentTypeIdentifier[_ <: Component] }
trait ComponentTypeIdentifier[+T <: Component] {
  val typeId: Id
}

