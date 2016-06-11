package se.joham.funrts.model

/**
  * Created by johan on 2016-06-11.
  */
trait LevelGenerator {
  def apply(nx: Int, ny: Int, seed: String): Array[Cell]
}

object GroundLevelGenerator extends LevelGenerator {
  override def apply(nx: Int, ny: Int, seed: String): Array[Cell] = {
    (0 until nx * ny).map(_ => Cell.TYPE_GROUND).toArray
  }
}