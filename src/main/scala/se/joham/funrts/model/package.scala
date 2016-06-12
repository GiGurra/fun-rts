package se.joham.funrts

import se.joham.funrts.math.Vec2FixPt

/**
  * Created by johan on 2016-06-11.
  */
package object model {
  type Pos = Vec2FixPt
  type EntityId = String
  type CombatantId = String
  type CESystemId = String
  type PlayerId = String
  type Size = Vec2FixPt
  type Tile = Byte

  implicit class RichTile(val tile: Tile) extends AnyVal {
    def isType(t: Byte)       : Boolean = (tile & Tile.TYPE_BITS) == t
    def isEitherType(t: Byte*): Boolean = t.exists(isType)
    def isEitherType(t: Iterable[Byte]): Boolean = t.exists(isType)
    def isWalkable            : Boolean = isGround || isBridge
    def isGround              : Boolean = isType(Tile.TYPE_GROUND)
    def isBridge              : Boolean = isType(Tile.TYPE_BRIDGE)
    def isChoppable           : Boolean = isType(Tile.TYPE_TREE)
    def isChopped             : Boolean = isType(Tile.TYPE_X_TREE)
    def isWater               : Boolean = isType(Tile.TYPE_WATER)
    def isCoast               : Boolean = isType(Tile.TYPE_COAST)
  }
}
