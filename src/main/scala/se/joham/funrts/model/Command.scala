package se.joham.funrts.model

import scala.language.implicitConversions

/**
  * Created by johan on 2016-06-11.
  */
case class Command(action: Action, entityId: String)
case class AggregatedCommands(iStep: Int, playerCommands: Map[Player.Id, Seq[Command]]) {
  def players: Set[Player.Id] = playerCommands.keys.toSet
}
object AggregatedCommands {
  implicit def acmds2Map(acmds: AggregatedCommands): Map[Player.Id, Seq[Command]] = {
    acmds.playerCommands
  }
}
