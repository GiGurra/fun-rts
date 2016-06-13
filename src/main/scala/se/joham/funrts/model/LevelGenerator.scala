package se.joham.funrts.model

/**
  * Created by johan on 2016-06-11.
  */
trait LevelGenerator {
  def apply(nx: Int, ny: Int, seed: String): Terrain
}

object GroundLevelGenerator extends LevelGenerator {
  override def apply(nx: Int, ny: Int, seed: String): Terrain = {
    Terrain(nx, ny, (0 until nx * ny).map(_ => Tile.TYPE_GROUND).toArray)
  }
}