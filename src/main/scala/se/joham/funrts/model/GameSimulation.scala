package se.joham.funrts.model

import se.gigurra.serviceutils.twitter.logging.Logging
import se.joham.funrts.model.components.{Acting, BaseInfo}

/**
  * Created by johan on 2016-06-11.
  */
case class GameSimulation(level: Level,
                          players: Set[Player],
                          dt: Long,
                          var iStep: Long) extends Logging {
  import level._

  def update(aggregatedCommands: AggregatedCommands): Unit = {
    validateInputs(aggregatedCommands)
    applyCommands(aggregatedCommands)
    runSimulationStep()
    iStep += 1
  }

  private def runSimulationStep(): Unit = {
    for ((_, system) <- entityStore.systems) {
      system.update(dt)
    }
  }

  private def applyCommands(aggregatedCommands: AggregatedCommands): Unit = {
    for {
      (playerId, commands) <- aggregatedCommands
      command              <- commands
      entity                = Entity(command.entityId)
      actor                <- entity.get[Acting]
    } {
      entityStore.system[Acting].put(entity, actor.copy(action = command.action))
    }
  }

  private def findPlayer(playerId: PlayerId): Option[Player] = {
    players.find(_.id == playerId)
  }

  private def validateInputs(aggregatedCommands: AggregatedCommands) = {
    def commandOnlyOwnedEntities: Boolean = {
      def verifyOwnership(playerId: PlayerId, command: Command): Boolean = {
        findPlayer(playerId) match {
          case None =>
            logger.error(s"Command pretended to come from player $playerId but no such player exists")
            false
          case Some(player) =>
            Entity(command.entityId).get[BaseInfo] match {
              case Some(info) if info.team != player.team =>
                logger.error(s"Illegal command received from player $player. Tried to command units of team ${info.team} which isn't his")
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
    require(commandOnlyOwnedEntities, "One or more players tried to command opponent entities")
  }
}
