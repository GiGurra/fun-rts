package se.joham.funrts.model

/**
  * Created by johan on 2016-06-11.
  */
case class Team(id: Team.Id)

object Team {
  type Id = String
  val blue = Team("blue")
  val red = Team("red")
}