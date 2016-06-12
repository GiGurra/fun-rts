package se.joham.funrts.model

import java.util.UUID

/**
  * Created by johan on 2016-06-11.
  */
case class Player(name: String, team: Team, id: PlayerId = UUID.randomUUID().toString) {
}
