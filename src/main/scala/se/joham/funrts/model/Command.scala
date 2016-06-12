package se.joham.funrts.model

/**
  * Created by johan on 2016-06-11.
  */
case class AggregatedCommands(iStep: Int, playerCommands: Map[PlayerId, Seq[Command]]) {
  def players: Set[PlayerId] = playerCommands.keys.toSet
}
case class Command(action: Action, entityId: EntityId)
