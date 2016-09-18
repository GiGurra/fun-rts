package se.joham.funrts.model

import se.gigurra.scalego.core.{ComponentTypeInfo, System}
import se.joham.funrts.model.FunRtsECS.IdTypes

import scala.collection.mutable
import scala.reflect.ClassTag


/**
  * Created by johan on 2016-06-26.
  */
class DefaultSystem[ComponentType : ClassTag](implicit componentTypeInfo: ComponentTypeInfo[ComponentType, IdTypes])
  extends System(componentTypeInfo)(mutable.HashMap()) {
}
