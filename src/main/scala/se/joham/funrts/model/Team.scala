package se.joham.funrts.model

/**
  * Created by johan on 2016-06-11.
  */
sealed trait Team
case class Combatant(id: Id = Id.gen()) extends Team
case object Neutral extends Team
