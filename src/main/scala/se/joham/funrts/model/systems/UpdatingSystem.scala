package se.joham.funrts.model.systems

import se.gigurra.scalego.core.System
import se.joham.funrts.model.FunRtsECS.IdTypes
import se.joham.funrts.model.Level

/**
  * Created by johan on 2016-09-18.
  */
trait UpdatingSystem[ComponentType] { _: System[ComponentType, IdTypes] =>
  def update(dt: Long, level: Level): Unit
}
