package se.joham.funrts

import se.joham.funrts.math.Vec2FixPt

/**
  * Created by johan on 2016-06-11.
  */
package object model {
  type Pos = Vec2FixPt
  type EntityId = String
  type CombatantId = String
  type ComponentSystemId = String
  type PlayerId = String
  type Size = Vec2FixPt
  type Cell = Byte

  implicit class RichCell(val cell: Cell) extends AnyVal {
    def isType(t: Byte)    : Boolean = (cell & Cell.TYPE_BITS) == t
    def isWalkable         : Boolean = isGround || isBridge
    def isGround           : Boolean = isType(Cell.TYPE_GROUND)
    def isBridge           : Boolean = isType(Cell.TYPE_BRIDGE)
    def isChoppable        : Boolean = isType(Cell.TYPE_TREE)
    def isChopped          : Boolean = isType(Cell.TYPE_X_TREE)
    def isWater            : Boolean = isType(Cell.TYPE_WATER)
    def isCoast            : Boolean = isType(Cell.TYPE_COAST)
  }
}
