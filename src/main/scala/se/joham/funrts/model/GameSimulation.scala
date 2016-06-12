package se.joham.funrts.model

import se.gigurra.serviceutils.twitter.logging.Logging

/**
  * Created by johan on 2016-06-11.
  */
case class GameSimulation(level: Level,
                          players: Set[Player],
                          dt: Long,
                          var iStep: Long) extends Logging {

  def update(aggregatedCommands: AggregatedCommands): Unit = {
    validateInputs(aggregatedCommands)
    applyCommands(aggregatedCommands.playerCommands.values.flatten)
    runSimulationStep()
    iStep += 1
  }

  private def runSimulationStep(): Unit = {
    ???
  }

  private def applyCommands(commands: Iterable[Command]): Unit = {
    /*
    for {
      command <- commands
      entity  <- level.entity(command.entityId)
    } {
      entity match {
        case state@StateFul(c: Character) => state.update(c.copy(action = command.action))
        case state@StateFul(b: Building) => state.update(b.copy(action = command.action))
      }
    }*/
  }

  private def findPlayer(playerId: PlayerId): Option[Player] = {
    players.find(_.id == playerId)
  }

  private def validateInputs(aggregatedCommands: AggregatedCommands) = {
    /*
    def commandOnlyOwnedEntities: Boolean = {
      def verifyOwnership(playerId: PlayerId, command: Command): Boolean = {
        findPlayer(playerId) match {
          case None =>
            logger.error(s"Command pretended to come from player $playerId but no such player exists")
            false
          case Some(player) =>
            level.entity(command.entityId) match {
              case Some(entity) if entity.team != player.team =>
                logger.error(s"Illegal command received from player $player. Tried to command units of team ${entity.team} which isn't his")
                false
              case _ =>
                true
            }
        }
      }
      aggregatedCommands.playerCommands.forall{ case (playerId, commands) => commands.forall(verifyOwnership(playerId, _))}
    }
    require(aggregatedCommands.iStep == iStep, "Tried to update game simulation with commands from wrong time")
    require(aggregatedCommands.players subsetOf players.map(_.id), "Commands originating from non simulation players")
    require(commandOnlyOwnedEntities, "One or more players tried to command opponent entities")*/
  }
}
