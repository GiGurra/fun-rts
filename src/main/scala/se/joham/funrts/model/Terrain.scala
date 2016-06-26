package se.joham.funrts.model

import java.util

import se.joham.funrts.math.Vec2FixPt

/**
  * Created by johan on 2016-06-13.
  */

case class Terrain(nx: Int, ny: Int, tiles: Array[Tile.Type]) {

  override def equals(other: Any): Boolean = {
    other match {
      case m: Terrain => (nx == m.nx) && (ny == m.ny) && util.Arrays.equals(tiles, m.tiles)
      case _ => false
    }
  }

  val size: Size.Type = Vec2FixPt(nx, ny)

  def update(pos: Pos.Type, tile: Tile.Type): Unit = {
    require(pos.x < size.x, s"Position $pos out of level bounds $size")
    require(pos.x >= 0, s"Position $pos out of level bounds $size")
    require(pos.y < size.y, s"Position $pos out of level bounds $size")
    require(pos.y >= 0, s"Position $pos out of level bounds $size")
    tiles(pos2Index(pos)) = tile
  }


  def apply(pos: Pos.Type): Tile.Type = {
    require(pos.x < size.x, s"Position $pos out of level bounds $size")
    require(pos.x >= 0, s"Position $pos out of level bounds $size")
    require(pos.y < size.y, s"Position $pos out of level bounds $size")
    require(pos.y >= 0, s"Position $pos out of level bounds $size")
    tiles(pos2Index(pos))
  }

  def pos2Index(pos: Pos.Type): Int = {
    (pos.y * nx + pos.x).toInt
  }

}
