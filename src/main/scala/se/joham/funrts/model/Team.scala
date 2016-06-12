package se.joham.funrts.model

/**
  * Created by johan on 2016-06-11.
  */
sealed trait Team
case class Combatant(id: Id) extends Team
case object Neutral extends Team

object Team {
  val blue = Combatant("blue")
  val red = Combatant("red")
}