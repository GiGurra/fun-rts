package se.joham.funrts.model

import se.joham.funrts.scalego.{CEStore, Entity}
import se.joham.funrts.util.JSON

/**
  * Created by johan on 2016-06-26.
  */
object EntityInfo {
  def apply(id: Entity.Id)(implicit store: CEStore[_]): String = {
    val components = Entity(id).components
    val buffer = new StringBuffer()
    buffer.append(s"Entity ($id) components:\n")
    for (component <- components) {
      buffer.append(s"  ${JSON.write(component, pretty = false)}")
    }
    buffer.toString
  }
}
