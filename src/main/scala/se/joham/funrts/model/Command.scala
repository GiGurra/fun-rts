package se.joham.funrts.model
import scala.language.implicitConversions

/**
  * Created by johan on 2016-06-11.
  */
case class Command(action: Action, entityId: EntityId)
case class AggregatedCommands(iStep: Int, playerCommands: Map[PlayerId, Seq[Command]]) {
  def players: Set[PlayerId] = playerCommands.keys.toSet
}
object AggregatedCommands {
  implicit def acmds2Map(acmds: AggregatedCommands): Map[PlayerId, Seq[Command]] = {
    acmds.playerCommands
  }
}
