package se.joham.funrts.renderer

import se.joham.funrts.model.{Building, Cell, Pos}
import se.joham.funrts.model.Character

/**
  * Created by johan on 2016-06-11.
  */
class GameRenderer(var camPos: Pos) {

  def draw(terrain: Seq[Cell],
           buildings: Seq[Building],
           characters: Seq[Character]): Unit = {

  }

}
